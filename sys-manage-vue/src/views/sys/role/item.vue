<template>
  <div>
    <el-drawer class="base-el-drawer" :title="dialogTitle" v-model="dialogVisible" size="36%" append-to-body>
      <el-scrollbar class="base-scrollbar">
        <el-form ref="formRef" :model="form" :rules="detailFormRules" style="width: 90%" v-loading="formLoading">
          <el-form-item label="角色名称：" label-width="120px" prop="roleName" :rules="detailFormRules.roleName">
            <el-input v-model="form.roleName" maxlength="20" show-word-limit clearable placeholder="请输入角色名称" />
          </el-form-item>
          <el-form-item label="角色类型：" label-width="120px" prop="roleType" :rules="detailFormRules.select">
            <el-select v-model="form.roleType" placeholder="请选择角色类型" style="width: 100%">
              <el-option v-for="item in roleTypeList" :key="item.value" :label="item.label" :value="item.value" />
            </el-select>
          </el-form-item>
          <el-form-item label="角色权限：" label-width="120px" prop="roleMenu">
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
                  v-model="menuExpand"
                  active-text="展开"
                  inactive-text="折叠"
                  inline-prompt
                  @change="handleCheckedTreeExpand"
                />
              </template>
              <el-tree
                class="w-full h-300px !overflow-y-scroll"
                ref="treeRef"
                :props="menuTreeProps"
                lazy
                :load="lazyLoadMenu"
                empty-text="加载中，请稍候"
                node-key="value"
                show-checkbox
                check-strictly
                @check-change="handleCheckedTreeNode"
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
import { TopId, TrueFalseEnum } from "@/utils/enums"
import { addApi, detailApi, updApi, getRoleTypeApi } from "@/api/sys/role"
import { LabelValue } from "@/api/commonTypes"
import { ElMessage } from "element-plus"
import { labelValueByParentApi } from "@/api/sys/menu"

const dialogVisible = ref(false)
const dialogTitle = ref("新增角色")

/** 打开弹窗 */
const open = async (id?: number) => {
  resetForm()
  await getRoleType()
  dialogVisible.value = true
  formLoading.value = true
  dialogTitle.value = id ? "修改角色" : "新增角色"
  form.value.roleType = roleTypeList.value[0].value
  if (id) {
    try {
      const detail = await detailApi(id)
      form.value = detail.data
      if (detail.data.menuIds) {
        treeRef.value.setCheckedKeys(detail.data.menuIds)
      }
    } catch (e) {
      ElMessage.error("获取详情失败")
    }
  }
  formLoading.value = false
}
/** 提供 open 方法，用于打开弹窗 */
defineExpose({ open })

const roleTypeList = ref<LabelValue[]>([])
const getRoleType = async () => {
  try {
    const { data } = await getRoleTypeApi()
    roleTypeList.value = data
  } catch (error) {
    ElMessage.error("获取角色类型失败")
  }
}

const formLoading = ref(false)
const formRef = ref()
const form = ref({
  roleId: undefined,
  roleName: "",
  roleType: "",
  menuIds: []
})
const detailFormRules = {
  roleName: [{ required: true, message: "请输入角色名称", trigger: "blur" }],
  ...commonFormRules
}

const treeRef = ref() // 菜单树组件 Ref
const treeNodeAll = ref(false) // 全选/全不选
const menuExpand = ref(false) // 展开/折叠

const menuTreeProps = {
  label: "label",
  value: "value",
  checkStrictly: true
}
const lazyLoadMenu = async (node: any, resolve: any) => {
  if (!node.loaded) {
    try {
      const { data } = await labelValueByParentApi(node.data?.value ? node.data.value : TopId)
      resolve(data)
    } catch (e) {
      ElMessage.error("获取下级菜单列表失败")
      resolve([])
    }
  } else {
    resolve([])
  }
}

/** 选/不选 */
const handleCheckedTreeNode = async (data: any, checked: boolean, hasCheckChild: boolean) => {
  if (checked && data.hasChildren && !data.children) {
    await getChild(data)
  }
  await nextTick(() => {
    treeRef.value.setChecked(data.value, checked, !hasCheckChild)
    const node = treeRef.value.getNode(data.value)
    if (checked) {
      if (node.parent.data.value) {
        treeRef.value.setChecked(node.parent.data.value, checked, !hasCheckChild)
      }
    } else {
      if (node.data.children) {
        node.data.children.forEach((child: any) => {
          treeRef.value.setChecked(child.value, checked, !hasCheckChild)
        })
      }
    }
  })
}

/** 全选/全不选 */
const handleCheckedTreeNodeAll = async () => {
  if (treeNodeAll.value && !menuExpand.value) {
    menuExpand.value = true
    await handleCheckedTreeExpand()
  }
  for (const node in treeRef.value?.store.nodesMap) {
    treeRef.value.store.nodesMap[node].checked = treeNodeAll.value
  }
}

/** 展开/折叠全部 */
const handleCheckedTreeExpand = async () => {
  const nodes = treeRef.value?.store.root.childNodes
  for (const node in nodes) {
    if (nodes[node].expanded === menuExpand.value) {
      continue
    }
    if (menuExpand.value) {
      await getChild(nodes[node].data)
    } else {
      nodes[node].expanded = menuExpand.value
    }
  }
}

const getChild = async (data: any) => {
  const node = treeRef.value.getNode(data.value)
  if (node.data.hasChildren) {
    const res = await labelValueByParentApi(data.value)
    treeRef.value.updateKeyChildren(data.value, res.data)
    for (const child of res.data) {
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
  formRef.value?.validate((valid: boolean) => {
    if (valid) {
      formLoading.value = true
      const api = form.value.roleId === undefined ? addApi : updApi
      const tmpForm = {
        ...form.value,
        menuIds: [...treeRef.value?.getCheckedKeys()]
      }
      api(tmpForm)
        .then((res) => {
          ElMessage.success(res.message)
          emit("success")
          dialogVisible.value = false
        })
        .catch((e) => {
          ElMessage.error("提交失败，请稍后重试")
        })
      formLoading.value = false
    }
  })
}

const resetForm = () => {
  treeNodeAll.value = false
  menuExpand.value = false
  treeRef.value?.setCheckedKeys([])

  form.value = {
    roleId: undefined,
    roleName: "",
    roleType: "",
    menuIds: []
  }
  formRef.value?.resetFields()
}
</script>

<style lang="scss" scoped></style>
