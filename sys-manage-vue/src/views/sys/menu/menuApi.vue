<template>
  <div>
    <el-drawer class="base-el-drawer" :title="dialogTitle" v-model="dialogVisible" size="36%" append-to-body>
      <el-scrollbar class="base-scrollbar">
        <el-form ref="formRef" :model="form" :rules="detailFormRules" style="width: 90%" v-loading="formLoading">
          <el-form-item label="菜单名称：" label-width="120px">{{ form.menuName }}</el-form-item>
          <el-form-item label="接口配置：" label-width="120px" prop="menuApi">
            <el-card style="width: 100%" shadow="never">
              <template #header>
                全选/全不选:
                <el-switch
                  v-model="treeNodeAll"
                  active-text="是"
                  inactive-text="否"
                  inline-prompt
                  @change="handleCheckedTreeNodeAll"
                />
                全部展开/折叠:
                <el-switch
                  v-model="treeExpand"
                  active-text="展开"
                  inactive-text="折叠"
                  inline-prompt
                  @change="handleCheckedTreeExpand"
                />
              </template>
              <el-tree
                class="w-full h-300px !overflow-y-scroll"
                ref="treeRef"
                :props="treeProps"
                lazy
                :load="lazyLoadMenu"
                empty-text="加载中，请稍候"
                node-key="value"
                show-checkbox
                check-strictly
              />
            </el-card>
          </el-form-item>
        </el-form>
      </el-scrollbar>
      <div class="base-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" :disabled="formLoading" @click="handleSubmit">确 定</el-button>
      </div>
    </el-drawer>
  </div>
</template>

<script lang="ts" setup>
import { ref, nextTick } from "vue"
import commonFormRules from "@/utils/rules"
import { getModuleListApi, pageListApi } from "@/api/sys/api"
import { setMenuApisApi } from "@/api/sys/menu"
import { LabelValue } from "@/api/commonTypes"
import { ElMessage } from "element-plus"

const dialogVisible = ref(false)
const dialogTitle = ref("菜单接口配置")

/** 打开弹窗 */
const open = async (menuId: number, menuName: string) => {
  formLoading.value = true
  resetForm()
  dialogVisible.value = true
  form.value.menuId = menuId
  form.value.menuName = menuName
  formLoading.value = false
}
/** 提供 open 方法，用于打开弹窗 */
defineExpose({ open })

const formLoading = ref(false)
const formRef = ref()
const form: any = ref({
  menuId: undefined,
  menuName: ""
})
const detailFormRules = {
  ...commonFormRules
}

const treeRef = ref() // 树组件 Ref
const treeNodeAll = ref(false) // 全选/全不选
const treeExpand = ref(false) // 展开/折叠

const treeProps = {
  label: "label",
  value: "value",
  isLeaf: "leaf"
}
const lazyLoadMenu = async (node: any, resolve: any) => {
  if (!node.loaded) {
    let res
    if (node.data?.label) {
      res = await pageList(node.data.label)
      resolve(res)
    } else {
      res = await getModuleList()
    }
    resolve(res)
  } else {
    resolve([])
  }
}

const getModuleList = async () => {
  const { data } = await getModuleListApi({})
  return data.list.map((m: any) => {
    return {
      label: m,
      value: m,
      disabled: true,
      hasChildren: true
    }
  })
}
const pageList = async (moduleName: any) => {
  const { data } = await pageListApi({ moduleName: moduleName })
  return data.list.map((m: any) => {
    return {
      label: m.apiName,
      value: m.apiId,
      leaf: true,
      hasChildren: false
    }
  })
}

/** 全选/全不选 */
const handleCheckedTreeNodeAll = async () => {
  if (treeNodeAll.value && !treeExpand.value) {
    treeExpand.value = true
    await handleCheckedTreeExpand()
  }
  for (const node in treeRef.value?.store.nodesMap) {
    if (!treeRef.value.store.nodesMap[node].disabled) {
      treeRef.value.store.nodesMap[node].checked = treeNodeAll.value
    }
  }
}

/** 展开/折叠全部 */
const handleCheckedTreeExpand = async () => {
  const nodes = treeRef.value?.store.root.childNodes
  for (const node in nodes) {
    if (nodes[node].expanded === treeExpand.value) {
      continue
    }
    if (treeExpand.value) {
      await getChild(nodes[node].data)
    } else {
      nodes[node].expanded = treeExpand.value
    }
  }
}

const getChild = async (data: any) => {
  const node = treeRef.value.getNode(data)
  if (data.hasChildren) {
    const res = await pageList(data.label)
    treeRef.value.updateKeyChildren(data.value, res)
    for (const child of res) {
      await getChild(child)
    }
  } else {
    node.isLeaf = true
  }
  node.loaded = true
  node.expanded = true
}

const emit = defineEmits(["success"])

const handleSubmit = async () => {
  formRef.value?.validate(async (valid: boolean) => {
    if (valid) {
      formLoading.value = true
      if (treeRef.value?.getCheckedKeys().length === 0) {
        ElMessage.error("请选择关联接口")
        return
      }
      setMenuApisApi(form.value.menuId, { list: [...treeRef.value?.getCheckedKeys()] }).then(({ code }) => {
        if (code === 20000) {
          ElMessage.success("操作成功")
          emit("success")
          dialogVisible.value = false
        }
      })
      formLoading.value = false
    }
  })
}

const resetForm = () => {
  treeNodeAll.value = false
  treeExpand.value = false
  treeRef.value?.setCheckedKeys([])

  form.value = {
    menuId: undefined,
    menuName: ""
  }
}
</script>

<style lang="scss" scoped></style>
