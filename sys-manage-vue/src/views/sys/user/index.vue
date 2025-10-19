<template>
  <div class="app-container">
    <el-card v-loading="loading" shadow="never" class="search-wrapper">
      <el-form ref="searchFormRef" :inline="true" :model="searchData">
        <el-form-item prop="account" label="账号">
          <el-input v-model="searchData.account" placeholder="请输入账号" />
        </el-form-item>
        <el-form-item prop="userName" label="用户名称">
          <el-input v-model="searchData.userName" placeholder="请输入用户名称" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" :icon="Search" @click="handleSearch">查询</el-button>
          <el-button :icon="Refresh" @click="resetSearch">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>
    <el-card v-loading="loading" shadow="never">
      <div class="toolbar-wrapper">
        <div>
          <el-button type="primary" :icon="CirclePlus" @click="openItem(undefined)">新增</el-button>
          <el-button type="danger" :icon="Delete" @click="batchDel">批量删除</el-button>
        </div>
      </div>
      <div class="marginB-20">
        <el-table ref="tableDataRef" :data="tableData" row-key="userId" border stripe @row-dblclick="tableDblClick">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column prop="account" label="账号" align="center" />
          <el-table-column prop="userName" label="用户名称" align="center" />
          <el-table-column prop="userContact" label="用户联系方式" align="center" />
          <el-table-column prop="lastLoginTime" label="最近登录时间" align="center" />
          <el-table-column prop="userAvatar" label="用户头像" align="center">
            <template #default="scope">
              <el-image
                style="width: 100px; height: 100px"
                :zoom-rate="1.2"
                :max-scale="7"
                :min-scale="0.2"
                fit="cover"
                v-if="avatarUrls[scope.row.userId]"
                :src="avatarUrls[scope.row.userId]"
                preview-teleported
                :preview-src-list="[avatarUrls[scope.row.userId]]"
              />
            </template>
          </el-table-column>
          <el-table-column prop="updateTime" label="更新时间" align="center" />
          <el-table-column fixed="right" label="操作" width="150" align="center">
            <template #default="scope">
              <el-button type="primary" text bg size="small" @click="openItem(scope.row.userId)">修改</el-button>
              <el-button type="danger" text bg size="small" @click="handleDelete(scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div class="pager-wrapper">
        <el-pagination
          background
          :layout="paginationData.layout"
          :page-sizes="paginationData.pageSizes"
          :total="paginationData.total"
          :page-size="paginationData.pageSize"
          :currentPage="paginationData.currentPage"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
    </el-card>
    <Item ref="itemRef" @success="getTableData" />
  </div>
</template>

<script lang="ts" setup>
import { onMounted, reactive, ref, watch } from "vue"
import { type FormInstance, type FormRules, ElMessage, ElMessageBox, ElTable } from "element-plus"
import { Search, Refresh, CirclePlus, Delete } from "@element-plus/icons-vue"
import { usePagination } from "@/hooks/usePagination"
import { cloneDeep } from "lodash-es"
import { pageListApi, batchDelApi } from "@/api/sys/user"
import { LabelValue, ListObjectBO } from "@/api/commonTypes"
import Item from "./item.vue"
import { getDetailApiUrl } from "@/api/sys/file"
// 存储用户头像 URL 的对象
const avatarUrls = ref<Record<number, string>>({})

defineOptions({
  // 命名当前组件
  name: "SUser"
})

const { paginationData, handleCurrentChange, handleSizeChange } = usePagination()

const loading = ref<boolean>(false)

const searchFormRef = ref<FormInstance | null>(null)
const searchData = reactive({
  account: "",
  userName: ""
})

const tableDataRef = ref<InstanceType<typeof ElTable>>()
const tableData = ref<any[]>([])
const getTableData = async () => {
  loading.value = true
  try {
    const { data } = await pageListApi({
      pageNum: paginationData.currentPage,
      pageSize: paginationData.pageSize,
      account: searchData.account || "",
      userName: searchData.userName || ""
    })
    paginationData.total = data.total
    tableData.value = data.list
    // 为每个用户加载头像
    await loadAvatars(data.list)
  } catch (error) {
    tableData.value = []
    console.error("获取表格数据失败:", error)
  } finally {
    loading.value = false
  }
}

// 加载用户头像的函数
const loadAvatars = async (users: any[]) => {
  for (const user of users) {
    if (user.userAvatar && !user.userAvatar.startsWith("http")) {
      try {
        avatarUrls.value[user.userId] = await getDetailApiUrl(user.userAvatar)
      } catch (error) {
        console.error(`加载用户 ${user.userId} 头像失败:`, error)
        // 使用默认头像
        avatarUrls.value[user.userId] = new URL("vue.svg", import.meta.url).href
      }
    } else if (user.userAvatar) {
      // 绝对路径直接使用
      avatarUrls.value[user.userId] = user.userAvatar
    } else {
      // 没有头像使用默认
      avatarUrls.value[user.userId] = new URL("vue.svg", import.meta.url).href
    }
  }
}
const tableDblClick = (row: any) => {
  openItem(row.userId)
}

const handleSearch = () => {
  paginationData.currentPage === 1 ? getTableData() : (paginationData.currentPage = 1)
}
const resetSearch = () => {
  searchFormRef.value?.resetFields()
  handleSearch()
}

const itemRef = ref()
const openItem = (id?: number) => {
  itemRef.value.open(id)
}

const handleDelete = (row: any) => {
  ElMessageBox.confirm(`正在删除用户：${row.userName}，确认删除？`, "提示", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning"
  }).then(() => {
    batchDelApi({ list: [row.userId] } as ListObjectBO).then(() => {
      ElMessage.success("删除成功")
      getTableData()
    })
  })
}

const batchDel = async () => {
  ElMessageBox.confirm("确定要批量删除吗？", "提示", {
    type: "warning",
    confirmButtonText: "确定",
    cancelButtonText: "取消"
  }).then(async () => {
    batchDelApi({ list: tableDataRef.value?.getSelectionRows()?.map((m: any) => m.userId) } as ListObjectBO)
      .then((res) => {
        ElMessage.success("删除成功")
        // 刷新列表
        getTableData()
      })
      .catch((error) => {
        ElMessage.error("删除失败")
      })
  })
}

/** 监听分页参数的变化 */
watch([() => paginationData.currentPage, () => paginationData.pageSize], getTableData)

const init = async () => {
  await getTableData()
}

onMounted(async () => {
  await init()
})
</script>

<style lang="scss" scoped>
.el-image {
  width: 100%;
  height: 200px;
}
</style>
