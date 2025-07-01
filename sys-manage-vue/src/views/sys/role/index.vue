<template>
  <div class="app-container">
    <el-card v-loading="loading" shadow="never" class="search-wrapper">
      <el-form ref="searchFormRef" :inline="true" :model="searchData">
        <el-form-item prop="roleName" label="角色名">
          <el-input v-model="searchData.roleName" placeholder="请输入角色名" />
        </el-form-item>
        <el-form-item prop="roleType" label="角色类型">
          <el-select v-model="searchData.roleType" placeholder="请选择角色类型" clearable>
            <el-option v-for="item in roleTypeList" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
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
          <el-button type="primary" :icon="CirclePlus" @click="openItem(undefined)">新增角色</el-button>
          <el-button type="danger" :icon="Delete" @click="batchDel">批量删除</el-button>
        </div>
      </div>
      <div class="marginB-20">
        <el-table ref="tableDataRef" :data="tableData" row-key="roleId" border stripe>
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column prop="roleName" label="用户名" align="center" />
          <el-table-column prop="roleTypeStr" label="角色" align="center">
            <template #default="scope">
              <el-tag type="primary" effect="plain">
                {{ scope.row.roleTypeStr }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="updateTime" label="更新时间" align="center" />
          <el-table-column fixed="right" label="操作" width="150" align="center">
            <template #default="scope">
              <el-button type="primary" text bg size="small" @click="openItem(scope.row.roleId)">修改</el-button>
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
import { getRoleTypeApi, pageListApi, batchDelApi } from "@/api/sys/role"
import { LabelValue, ListObjectBO } from "@/api/commonTypes"
import Item from "./item.vue"

defineOptions({
  // 命名当前组件
  name: "Role"
})

const { paginationData, handleCurrentChange, handleSizeChange } = usePagination()

const loading = ref<boolean>(false)

const searchFormRef = ref<FormInstance | null>(null)
const searchData = reactive({
  roleName: "",
  roleType: undefined
})

const roleTypeList = ref<LabelValue[]>([])
const getRoleType = async () => {
  try {
    const { data } = await getRoleTypeApi()
    roleTypeList.value = data
  } catch (error) {
    ElMessage.error("获取角色类型失败")
  }
}

const tableDataRef = ref<InstanceType<typeof ElTable>>()
const tableData = ref<any[]>([])
const getTableData = async () => {
  loading.value = true
  pageListApi({
    pageNum: paginationData.currentPage,
    pageSize: paginationData.pageSize,
    roleName: searchData.roleName || "",
    roleType: searchData.roleType || ""
  })
    .then(({ data }) => {
      paginationData.total = data.total
      tableData.value = data.list
    })
    .catch(() => {
      tableData.value = []
    })
    .finally(() => {
      loading.value = false
    })
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
  ElMessageBox.confirm(`正在删除角色：${row.roleName}，确认删除？`, "提示", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning"
  }).then(() => {
    batchDelApi({ list: [row.roleId] } as ListObjectBO).then(() => {
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
    batchDelApi({ list: tableDataRef.value?.getSelectionRows()?.map((m: any) => m.roleId) } as ListObjectBO)
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
  await getRoleType()
  await getTableData()
}

onMounted(async () => {
  await init()
})
</script>

<style lang="scss" scoped></style>
