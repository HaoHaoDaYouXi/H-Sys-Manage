<template>
  <div>
    <el-drawer class="base-el-drawer" :title="dialogTitle" v-model="dialogVisible" size="36%" append-to-body>
      <el-scrollbar class="base-scrollbar">
        <el-form ref="formRef" :model="form" :rules="detailFormRules" style="width: 90%" v-loading="formLoading">
          <el-form-item label="父级菜单：" label-width="120px" prop="menuParentId">
            <el-cascader
              v-model="form.menuParentId"
              clearable
              :props="menuParentProps"
              placeholder="请选择父级菜单名称"
              :append-to-body="false"
              class="area-choose"
            />
          </el-form-item>
          <el-form-item label="菜单名称：" label-width="120px" prop="menuName" :rules="detailFormRules.menuName">
            <el-input v-model="form.menuName" maxlength="20" show-word-limit clearable placeholder="请输入菜单名称" />
          </el-form-item>
          <el-form-item label="菜单类型：" label-width="120px" prop="menuType">
            <el-select v-model="form.menuType" placeholder="请选择菜单类型" style="width: 100%">
              <el-option v-for="item in menuTypes" :key="item.value" :label="item.label" :value="item.value" />
            </el-select>
          </el-form-item>
          <el-form-item v-if="form.menuType !== 3" label="图标：" label-width="120px" prop="menuIcon">
            <IconSelect :exData="{ icon: form.menuIcon }" @changeIcon="changeIcon" />
          </el-form-item>
          <el-form-item label-width="120px" prop="menuKey" :rules="detailFormRules.menuKey">
            <template #label>
              <span>
                权限标识
                <el-tooltip
                  effect="dark"
                  content="提示：权限标识 /、/icon、/redirect、/login、/404 的是保留字，请不要再次配置。"
                  placement="top"
                >
                  <i class="el-icon-info" />
                </el-tooltip>
                ：
              </span>
            </template>
            <el-input v-model="form.menuKey" placeholder="请输入权限标识" clearable />
          </el-form-item>
          <el-form-item v-if="form.menuType === 2" label-width="120px" label="高亮菜单标识：">
            <el-input v-model="form.activeMenu" placeholder="请输入高亮菜单标识" clearable />
          </el-form-item>
          <el-form-item v-if="form.menuType === 1" label-width="120px" label="重定向：">
            <el-input v-model="form.redirect" placeholder="请输入重定向地址" clearable />
          </el-form-item>
          <el-form-item
            label="组件名："
            label-width="120px"
            prop="menuComponent"
            v-if="form.menuType !== 3"
            :rules="detailFormRules.menuComponent"
          >
            <el-input v-model="form.menuComponent" placeholder="请输入组件名" clearable />
          </el-form-item>
          <el-form-item v-if="form.menuType === 2" label="打开方式：" label-width="140px" prop="outUrl">
            <el-radio-group v-model="form.outUrl">
              <el-radio :label="TrueFalseEnum.FALSE">内链</el-radio>
              <el-radio :label="TrueFalseEnum.TRUE">外链</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="排序号：" label-width="120px" prop="showOrder" v-if="form.menuType !== 3">
            <el-input
              v-model="form.showOrder"
              placeholder="请输入排序号"
              clearable
              @input="
                form.showOrder = parseInt(
                  form.showOrder
                    .toString()
                    .replace(/[^\d]/g, '')
                    .replace(/(^0+)(\d+)/, '$2')
                )
              "
            />
          </el-form-item>
          <el-form-item label="是否禁用：" label-width="140px" prop="disabled">
            <el-switch
              v-model="form.disabled"
              :active-value="TrueFalseEnum.TRUE"
              :inactive-value="TrueFalseEnum.FALSE"
            />
          </el-form-item>
          <el-form-item v-if="form.menuType !== 3" label="是否隐藏：" label-width="140px" prop="hidden">
            <el-switch v-model="form.hidden" :active-value="TrueFalseEnum.TRUE" :inactive-value="TrueFalseEnum.FALSE" />
          </el-form-item>
          <el-form-item v-if="form.menuType !== 3" label="是否缓存：" label-width="140px" prop="cachedView">
            <el-switch
              v-model="form.cachedView"
              :active-value="TrueFalseEnum.TRUE"
              :inactive-value="TrueFalseEnum.FALSE"
            />
          </el-form-item>
          <el-form-item v-if="form.menuType !== 3" label="面包屑中显示：" label-width="140px" prop="breadcrumb">
            <el-switch
              v-model="form.breadcrumb"
              :active-value="TrueFalseEnum.TRUE"
              :inactive-value="TrueFalseEnum.FALSE"
            />
          </el-form-item>
          <el-form-item v-if="form.menuType === 2" label="固定在tags-view：" label-width="140px" prop="affix">
            <el-switch v-model="form.affix" :active-value="TrueFalseEnum.TRUE" :inactive-value="TrueFalseEnum.FALSE" />
          </el-form-item>
          <el-form-item v-if="form.menuType === 2" label="显示根目录：" label-width="140px" prop="alwaysShow">
            <el-switch
              v-model="form.alwaysShow"
              :active-value="TrueFalseEnum.TRUE"
              :inactive-value="TrueFalseEnum.FALSE"
            />
          </el-form-item>
          <el-form-item label="备注：" label-width="120px" prop="menuDescribe">
            <el-input
              v-model="form.menuDescribe"
              type="textarea"
              :rows="5"
              placeholder="请输入备注"
              maxlength="200"
              show-word-limit
              clearable
            />
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
import { ref } from "vue"
import commonFormRules from "@/utils/rules"
import IconSelect from "@/components/IconSelect/index.vue"
import { TopId, TrueFalseEnum } from "@/utils/enums"
import { addApi, detailApi, updApi, labelValueByParentApi } from "@/api/sys/menu"
import { ElMessage } from "element-plus"

const dialogVisible = ref(false)
const dialogTitle = ref("新增菜单")
const menuTypes = ref<[]>([])

/** 打开弹窗 */
const open = async (menuTypeData: [], id?: number) => {
  resetForm()
  dialogVisible.value = true
  dialogTitle.value = id ? "修改菜单" : "新增菜单"
  if (id) {
    formLoading.value = true
    const detail = await detailApi(id)
    form.value = detail.data
    form.value.menuParentId = detail.data.menuParentPath.split("-").map(Number)
    formLoading.value = false
  }
  menuTypes.value = menuTypeData
}
/** 提供 open 方法，用于打开弹窗 */
defineExpose({ open })

const formLoading = ref(false)
const formRef = ref()
const form = ref({
  menuId: undefined,
  menuParentId: [TopId],
  menuType: 1,
  menuName: "",
  menuIcon: "",
  menuKey: "",
  activeMenu: "",
  redirect: "",
  menuComponent: "",
  outUrl: 0,
  showOrder: 1,
  disabled: 0,
  hidden: 0,
  cachedView: 0,
  breadcrumb: 1,
  affix: 0,
  alwaysShow: 1,
  menuDescribe: ""
})
const detailFormRules = {
  menuName: [{ required: true, message: "请输入菜单名称", trigger: "blur" }],
  menuComponent: [{ required: true, message: "请输入组件名", trigger: ["change", "blur"] }],
  menuKey: [{ required: true, message: "请输入", trigger: ["change", "blur"] }],
  ...commonFormRules
}

const lazyLoadMenu = async (node: any, resolve: any) => {
  const { data } = await labelValueByParentApi(node.value ? node.value : TopId)
  resolve(data || [])
}

const menuParentProps = {
  label: "label",
  value: "value",
  checkStrictly: true,
  lazy: true,
  lazyLoad: lazyLoadMenu
}

const changeIcon = (exData: { icon: string }) => {
  form.value.menuIcon = exData.icon
}

const emit = defineEmits<{ (e: "success", value: { menuParentId: number }): void }>()

const handleSubmit = async () => {
  formRef.value?.validate((valid: boolean) => {
    if (valid) {
      formLoading.value = true
      const api = form.value.menuId === undefined ? addApi : updApi
      const formData = {
        ...form.value,
        menuParentId:
          Array.isArray(form.value.menuParentId) && form.value.menuParentId.length > 0
            ? form.value.menuParentId[form.value.menuParentId.length - 1]
            : TopId
      }
      api(formData)
        .then((res) => {
          ElMessage.success(res.message)
          emit("success", { menuParentId: formData.menuParentId })
          dialogVisible.value = false
        })
        .catch(() => {
          ElMessage.error("提交失败，请稍后重试")
        })
      formLoading.value = false
    }
  })
}

const resetForm = () => {
  form.value = {
    menuId: undefined,
    menuParentId: [TopId],
    menuType: 1,
    menuName: "",
    menuIcon: "",
    menuKey: "",
    activeMenu: "",
    redirect: "",
    menuComponent: "",
    outUrl: 0,
    showOrder: 1,
    disabled: 0,
    hidden: 0,
    cachedView: 0,
    breadcrumb: 1,
    affix: 0,
    alwaysShow: 1,
    menuDescribe: ""
  }
  formRef.value?.resetFields()
}
</script>

<style lang="scss" scoped></style>
