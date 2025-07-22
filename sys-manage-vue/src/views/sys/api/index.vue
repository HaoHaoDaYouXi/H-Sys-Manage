<template>
  <div class="app-container">
    <el-card v-loading="loading" shadow="never">
      <el-row :gutter="20">
        <div class="toolbar-wrapper">
          <div>
            <el-button type="primary" :icon="CirclePlus" @click="openItem(undefined)">新增</el-button>
            <el-button type="danger" :icon="Delete" @click="batchDel">批量删除</el-button>
          </div>
        </div>
      </el-row>
      <el-row :gutter="20" class="marginB-20">
        <el-col :span="4">
          <el-tree
            ref="treeRef"
            node-key="label"
            style="width: 100%; height: 100%; border: 1px solid #ebeef5"
            :props="props"
            :data="treeData"
            @node-click="treeClick"
            :expand-on-click-node="false"
            lazy
          />
        </el-col>
        <el-col :span="20">
          <el-table ref="tableDataRef" :data="tableData" style="width: 100%" row-key="apiId" border stripe>
            <el-table-column type="selection" width="50" align="center" />
            <el-table-column prop="moduleName" width="130" label="模块名称" align="center" />
            <el-table-column prop="apiName" width="140" label="接口名称" align="left" />
            <el-table-column prop="apiKey" label="接口标识" align="left" />
            <el-table-column prop="apiType" width="120" label="接口类型" align="center">
              <template #default="scope">{{ getApiType(scope.row.apiType) }}</template>
            </el-table-column>
            <el-table-column prop="requestMethod" width="120" label="请求类型" align="center" />
            <el-table-column prop="updateTime" width="180" label="更新时间" align="center" />
            <el-table-column fixed="right" width="150" label="操作" align="center">
              <template #default="scope">
                <el-button type="primary" text bg size="small" @click="openItem(scope.row.apiId)">修改</el-button>
                <el-button type="danger" text bg size="small" @click="handleDelete(scope.row)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
          <div class="pager-wrapper marginT-20">
            <el-pagination
              background
              :layout="tablePagination.paginationData.layout"
              :page-sizes="tablePagination.paginationData.pageSizes"
              :total="tablePagination.paginationData.total"
              :page-size="tablePagination.paginationData.pageSize"
              :currentPage="tablePagination.paginationData.currentPage"
              @size-change="tablePagination.handleSizeChange"
              @current-change="tablePagination.handleCurrentChange"
            />
          </div>
        </el-col>
      </el-row>
    </el-card>
    <Item ref="itemRef" @success="itemSuccess" />
  </div>
</template>

<script lang="ts" setup>
import { ref, onMounted, computed, nextTick } from "vue"
import { usePagination } from "@/hooks/usePagination"
import { LoadFunction, ElMessage, ElMessageBox, ElTable } from "element-plus"
import { CirclePlus, Delete } from "@element-plus/icons-vue"
import { getModuleList, pageListApi, batchDelApi } from "@/api/sys/api"
import { ApiTypeEnum } from "@/utils/enums"
import { ListObjectBO } from "@/api/commonTypes"
import Item from "./item.vue"

defineOptions({
  // 命名当前组件
  name: "SApi"
})

const loading = ref<boolean>(false)

const props = {
  label: "label",
  isLeaf: () => true
}
const treeRef = ref()
const treeData = ref([])
const getTreeData = async () => {
  const req = {
    // pageNum: leftPagination.paginationData.currentPage,
    // pageSize: leftPagination.paginationData.pageSize
  }
  const { data } = await getModuleList(req)
  treeData.value = data.list.map((m: any) => {
    return {
      label: m
    }
  })
  await nextTick()
  treeRef.value.setCurrentNode(treeData.value[0], true)
  await getTableData()
}
const treeClick = async (node: any) => {
  await getTableData()
}

const tableDataRef = ref<InstanceType<typeof ElTable>>()
const tableData = ref<any[]>([])
const tablePagination = usePagination()
const getTableData = async () => {
  const req = {
    pageNum: tablePagination.paginationData.currentPage,
    pageSize: tablePagination.paginationData.pageSize,
    moduleName: ""
  }
  if (treeRef.value.getCurrentKey()) {
    req.moduleName = treeRef.value.getCurrentKey()
  }
  const { data } = await pageListApi(req)
  tablePagination.paginationData.total = data.total
  tableData.value = data.list
}
const getApiType = computed(() => (apiType: number) => {
  const target = ApiTypeEnum.find((f) => f.value === apiType)
  return target?.label || ""
})
const itemRef = ref()
const openItem = (id?: number) => {
  itemRef.value.open(id)
}

const itemSuccess = async () => {
  console.log("itemSuccess")
  await getTreeData()
}

const handleDelete = (row: any) => {
  ElMessageBox.confirm("确定要删除吗？", "提示", {
    type: "warning",
    confirmButtonText: "确定",
    cancelButtonText: "取消"
  }).then(async () => {
    batchDelApi({ list: [row.paramCode] } as ListObjectBO)
      .then((res) => {
        ElMessage.success("删除成功")
        itemSuccess()
      })
      .catch((error) => {
        ElMessage.error("删除失败")
      })
  })
}

const batchDel = async () => {
  ElMessageBox.confirm("确定要删除吗？", "提示", {
    type: "warning",
    confirmButtonText: "确定",
    cancelButtonText: "取消"
  }).then(async () => {
    batchDelApi({ list: tableDataRef.value?.getSelectionRows()?.map((m: any) => m.paramCode) } as ListObjectBO)
      .then((res) => {
        ElMessage.success("删除成功")
        itemSuccess()
      })
      .catch((error) => {
        ElMessage.error("删除失败")
      })
  })
}

const init = async () => {
  if (loading.value) return
  loading.value = true
  await getTreeData()
  loading.value = false
}

onMounted(async () => {
  await init()
})
</script>

<style lang="scss" scoped></style>
