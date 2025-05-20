<template>
  <div class="app-container">
    <el-card v-loading="loading" shadow="never" class="search-wrapper">
      <el-form ref="searchFormRef" :inline="true" :model="searchData">
        <el-form-item prop="menuName" label="菜单名称">
          <el-input v-model="searchData.menuName" placeholder="请输入" />
        </el-form-item>
        <el-form-item prop="menuType" label="菜单类型">
          <el-select v-model="searchData.menuType" placeholder="请选择菜单类型" clearable>
            <el-option v-for="item in menuTypeList" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
        </el-form-item>
        <el-form-item prop="disabled" label="禁用状态">
          <el-select v-model="searchData.disabled" placeholder="请选择禁用状态" clearable>
            <el-option v-for="item in DisabledList" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" :icon="Search" @click="getTableData">查询</el-button>
          <el-button :icon="Refresh" @click="resetSearch">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>
    <el-card v-loading="loading" shadow="never">
      <div class="toolbar-wrapper">
        <div>
          <el-button type="primary" :icon="CirclePlus" @click="openItem(undefined)">新增菜单</el-button>
          <el-button type="danger" :icon="Delete" @click="batchDel">批量删除</el-button>
        </div>
      </div>
      <div class="table-wrapper">
        <el-table
          ref="tableDataRef"
          :data="tableData"
          style="width: 100%"
          row-key="menuId"
          border
          stripe
          lazy
          :load="load"
          :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
        >
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column prop="menuName" label="菜单名称" align="left" />
          <el-table-column prop="menuIcon" label="图标" align="center">
            <template #default="scope">
              <IconItem v-if="scope.row.menuIcon" :icon="scope.row.menuIcon" />
            </template>
          </el-table-column>
          <el-table-column prop="menuTypeStr" label="菜单类型" align="center" />
          <el-table-column prop="menuKey" label="菜单标识" align="center" />
          <el-table-column prop="menuComponent" label="组件地址" align="center" />
          <el-table-column prop="showOrder" label="顺序" align="center" />
          <el-table-column prop="disabled" label="禁用" align="center">
            <template #default="scope">
              <el-switch
                v-model="scope.row.disabled"
                :active-value="TrueFalseEnum.TRUE"
                :inactive-value="TrueFalseEnum.FALSE"
                :loading="changeDisableLoading"
                @change="ChangeDisable(scope.row)"
              />
            </template>
          </el-table-column>
          <el-table-column prop="updateTime" label="更新时间" align="center" />
          <el-table-column fixed="right" label="操作" width="150" align="center">
            <template #default="scope">
              <el-button type="primary" text bg size="small" @click="openItem(scope.row.menuId)">修改</el-button>
              <el-button type="danger" text bg size="small" @click="handleDelete(scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </el-card>
    <Item ref="itemRef" @success="itemSuccess" />
  </div>
</template>

<script lang="ts" setup>
import { reactive, ref, onMounted } from "vue"
import { type FormInstance, ElMessage, ElMessageBox, ElTable } from "element-plus"
import { Search, Refresh, CirclePlus, Delete } from "@element-plus/icons-vue"
import IconItem from "@/components/Icon/item.vue"
import { batchDelApi, getMenuTypeApi, listByParentApi, changeDisableApi } from "@/api/sys/menu"
import { SMenuListReq, SMenuList } from "@/api/sys/menu/types/menu"
import { TopId, DisabledList, TrueFalseEnum } from "@/utils/enums"
import Item from "./item.vue"
import { LabelValue, ListObjectBO } from "@/api/commonTypes"

defineOptions({
  // 命名当前组件
  name: "Menu"
})

const loading = ref<boolean>(false)
const searchFormRef = ref<FormInstance | null>(null)
const searchData = reactive<SMenuListReq>({
  menuParentId: TopId,
  menuName: "",
  menuType: undefined,
  disabled: undefined
})

const menuTypeList = ref<LabelValue[]>([])
const getMenuType = async () => {
  try {
    const { data } = await getMenuTypeApi()
    menuTypeList.value = data
  } catch (error) {
    ElMessage.error("获取菜单类型失败")
  }
}

const tableDataRef = ref<InstanceType<typeof ElTable>>()
const tableData = ref<SMenuList[]>([])
const getTableData = async () => {
  try {
    const { data } = await listByParentApi(searchData)
    tableData.value = data
  } catch (error) {
    ElMessage.error("获取菜单列表失败")
  }
}

const changeDisableLoading = ref(false)

const ChangeDisable = async (row: SMenuList) => {
  changeDisableLoading.value = true
  changeDisableApi({ menuId: row.menuId, disabled: row.disabled })
    .then((res) => {
      ElMessage.success(res.message)
    })
    .catch((error) => {
      ElMessage.error("更新失败，请稍后重试")
      row.disabled = row.disabled === TrueFalseEnum.TRUE ? TrueFalseEnum.FALSE : TrueFalseEnum.TRUE
    })
  changeDisableLoading.value = false
}

const load = async (row: SMenuList, treeNode: unknown, resolve: (data: SMenuList[]) => void) => {
  const tmp = {
    ...searchData,
    menuParentId: row.menuId
  }
  const { data } = await listByParentApi(tmp)
  resolve(data)
}

const resetSearch = () => {
  searchData.menuParentId = TopId
  searchFormRef.value?.resetFields()
  getTableData()
}

const itemRef = ref()
const openItem = (id?: number) => {
  itemRef.value.open(menuTypeList.value, id)
}

const itemSuccess = async (value: { menuParentId: number }) => {
  if (!value.menuParentId || value.menuParentId === TopId) {
    await getTableData()
  } else {
    const { data } = await listByParentApi({ menuParentId: value.menuParentId } as SMenuListReq)
    tableDataRef.value?.updateKeyChildren(value.menuParentId.toString(), data)
  }
}

const handleDelete = (row: SMenuList) => {
  ElMessageBox.confirm("确定要删除吗？", "提示", {
    type: "warning",
    confirmButtonText: "确定",
    cancelButtonText: "取消"
  }).then(async () => {
    batchDelApi({ list: [row.menuId] } as ListObjectBO)
      .then((res) => {
        ElMessage.success("删除成功")
        itemSuccess({ menuParentId: row.menuParentId })
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
    batchDelApi({ list: tableDataRef.value?.getSelectionRows()?.map((m: SMenuList) => m.menuId) } as ListObjectBO)
      .then((res) => {
        ElMessage.success("删除成功")
        itemSuccess({ menuParentId: TopId })
      })
      .catch((error) => {
        ElMessage.error("删除失败")
      })
  })
}

const init = async () => {
  if (loading.value) return
  loading.value = true
  await getMenuType()
  await getTableData()
  loading.value = false
}

onMounted(async () => {
  await init()
})
</script>

<style lang="scss" scoped></style>
