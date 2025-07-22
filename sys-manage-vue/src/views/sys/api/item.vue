<template>
  <div>
    <el-drawer class="base-el-drawer" :title="dialogTitle" v-model="dialogVisible" size="36%" append-to-body>
      <el-scrollbar class="base-scrollbar">
        <el-form ref="formRef" :model="form" :rules="detailFormRules" style="width: 90%" v-loading="formLoading">
          <el-form-item label="模块名称：" label-width="120px" prop="moduleName" :rules="detailFormRules.required">
            <el-input v-model="form.moduleName" maxlength="32" show-word-limit clearable placeholder="请输入模块名称" />
          </el-form-item>
          <el-form-item label="接口名称：" label-width="120px" prop="apiName" :rules="detailFormRules.required">
            <el-input v-model="form.apiName" maxlength="32" show-word-limit clearable placeholder="请输入接口名称" />
          </el-form-item>
          <el-form-item label="接口类型：" label-width="120px" prop="apiType" :rules="detailFormRules.select">
            <el-select v-model="form.apiType" placeholder="请选择接口类型" clearable>
              <el-option v-for="item in ApiTypeEnum" :key="item.value" :label="item.label" :value="item.value" />
            </el-select>
          </el-form-item>
          <el-form-item label="接口标识：" label-width="120px" prop="apiKey" :rules="detailFormRules.required">
            <el-input v-model="form.apiKey" maxlength="120" show-word-limit clearable placeholder="请输入接口标识" />
          </el-form-item>
          <el-form-item label="Url类型：" label-width="120px" prop="urlType" :rules="detailFormRules.select_0">
            <el-select v-model="form.urlType" placeholder="请选择Url类型" clearable>
              <el-option key="urlType_0" label="普通" :value="0" />
              <el-option key="urlType_1" label="路径中有参数" :value="1" />
            </el-select>
          </el-form-item>
          <el-form-item label="请求类型：" label-width="120px" prop="requestMethod" :rules="detailFormRules.select">
            <el-select v-model="form.requestMethod" placeholder="请选择请求类型" clearable>
              <el-option key="requestMethod_post" label="POST" value="POST" />
              <el-option key="requestMethod_get" label="GET" value="GET" />
            </el-select>
          </el-form-item>
          <el-form-item label="开放状态：" label-width="120px" prop="openStatus" :rules="detailFormRules.select_0">
            <el-select v-model="form.openStatus" placeholder="请选择开放状态" clearable>
              <el-option key="openStatus_0" label="需要权限" :value="0" />
              <el-option key="openStatus_1" label="开放" :value="1" />
            </el-select>
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
import { addApi, detailApi, updApi } from "@/api/sys/api"
import { ApiTypeEnum } from "@/utils/enums"
import { ElMessage } from "element-plus"

const dialogVisible = ref(false)
const dialogTitle = ref("新增")

/** 打开弹窗 */
const open = async (id?: number) => {
  resetForm()
  dialogVisible.value = true
  formLoading.value = true
  dialogTitle.value = id ? "修改" : "新增"
  if (id) {
    try {
      const detail = await detailApi(id)
      form.value = detail.data
    } catch (e) {
      ElMessage.error("获取详情失败")
    }
  }
  formLoading.value = false
}
/** 提供 open 方法，用于打开弹窗 */
defineExpose({ open })

const formLoading = ref(false)
const formRef = ref()
const form = ref({
  apiId: undefined,
  moduleName: "",
  apiName: "",
  apiType: 4,
  apiKey: "",
  urlType: 0,
  requestMethod: "POST",
  openStatus: 0
})
const detailFormRules = {
  ...commonFormRules
}

const emit = defineEmits(["success"])

const handleSubmit = async () => {
  formRef.value?.validate((valid: boolean) => {
    if (valid && !formLoading.value) {
      formLoading.value = true
      const api = form.value.apiId === undefined ? addApi : updApi
      api(form.value).then((res) => {
        ElMessage.success(res.message)
        emit("success")
        dialogVisible.value = false
      })
      formLoading.value = false
    }
  })
}

const resetForm = () => {
  form.value = {
    apiId: undefined,
    moduleName: "",
    apiName: "",
    apiType: 4,
    apiKey: "",
    urlType: 0,
    requestMethod: "POST",
    openStatus: 0
  }
  formRef.value?.resetFields()
}
</script>

<style lang="scss" scoped></style>
