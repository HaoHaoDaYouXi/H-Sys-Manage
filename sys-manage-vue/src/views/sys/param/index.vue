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
        <el-col :span="6">
          <el-tree
            style="width: 100%; height: 100%; border: 1px solid #ebeef5"
            :props="props"
            :load="loadNode"
            @node-click="treeClick"
            :expand-on-click-node="false"
            lazy
          />
        </el-col>
        <el-col :span="18">
          <el-table
            ref="tableDataRef"
            :data="tableData"
            style="width: 100%"
            row-key="paramCode"
            border
            stripe
            :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
          >
            <el-table-column type="selection" width="50" align="center" />
            <el-table-column prop="paramCode" label="参数编码" align="left" />
            <el-table-column prop="paramParentCode" label="参数上级编码" align="center" />
            <el-table-column prop="paramName" label="参数名称" align="center" />
            <el-table-column prop="paramValue" label="参数值" align="center" />
            <el-table-column prop="paramSortCode" label="顺序" align="center" />
            <el-table-column prop="updateTime" label="更新时间" align="center" />
            <el-table-column fixed="right" label="操作" width="150" align="center">
              <template #default="scope">
                <el-button type="primary" text bg size="small" @click="openItem(scope.row.paramCode)">修改</el-button>
                <el-button type="danger" text bg size="small" @click="handleDelete(scope.row)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-col>
      </el-row>
    </el-card>
    <Item ref="itemRef" @success="itemSuccess" />
  </div>
</template>

<script lang="ts" setup>
import { ref, onMounted } from "vue"
import { LoadFunction, ElMessage, ElMessageBox, ElTable } from "element-plus"
import { CirclePlus, Delete } from "@element-plus/icons-vue"
import { batchDelApi, getSParamByParentCodeApi } from "@/api/sys/param"
import Item from "./item.vue"
import { ListObjectBO } from "@/api/commonTypes"

defineOptions({
  // 命名当前组件
  name: "SParam"
})

const loading = ref<boolean>(false)

const props = {
  label: "paramName",
  isLeaf: "leaf"
}

const loadNode: LoadFunction = async (node, resolve) => {
  const params: {
    paramCode?: number
    paramParentCode?: number
  } = {}
  if (node?.data?.paramCode) {
    params.paramParentCode = node?.data?.paramCode
  } else {
    params.paramCode = 1
  }
  const { data } = await getSParamByParentCodeApi(params)
  return resolve(data)
}
const treeClick = async (node: any) => {
  treeClickNode.value = node ? node : ""
  await getTableData()
}
const treeClickNode = ref()

const tableDataRef = ref<InstanceType<typeof ElTable>>()
const tableData = ref<any[]>([])
const getTableData = async () => {
  const params = {
    paramParentCode: treeClickNode.value ? treeClickNode.value.paramCode : 1
  }
  const { data } = await getSParamByParentCodeApi(params)
  tableData.value = data
}

const getChild = (data: any[], parentId: any): any[] => {
  const child = data
    .filter((item) => item.paramParentCode === parentId)
    .sort((a, b) => {
      if (a.paramSortCode < b.paramSortCode) return -1
      if (a.paramSortCode > b.paramSortCode) return 1
      if (a.paramCode < b.paramCode) {
        return 1
      } else {
        return -1
      }
    })
  if (child.length > 0) {
    return child.map((item) => {
      return {
        ...item,
        children: getChild(data, item.paramCode)
      }
    })
  } else {
    return []
  }
}

const itemRef = ref()
const openItem = (id?: number) => {
  itemRef.value.open(tableData.value, id)
}

const itemSuccess = async () => {
  await getTableData()
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
  await getTableData()
  loading.value = false
}

onMounted(async () => {
  await init()
})
</script>

<style lang="scss" scoped></style>
