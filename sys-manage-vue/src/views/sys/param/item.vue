<template>
  <div>
    <el-drawer class="base-el-drawer" :title="dialogTitle" v-model="dialogVisible" size="36%" append-to-body>
      <el-scrollbar class="base-scrollbar">
        <el-form ref="formRef" :model="form" :rules="detailFormRules" style="width: 90%" v-loading="formLoading">
          <el-form-item label="父级：" label-width="120px" prop="paramParentCode" :rules="detailFormRules.select">
            <el-cascader
              v-model="form.paramParentCode"
              clearable
              :props="paramParentCodeProps"
              placeholder="请选择父级"
              :append-to-body="false"
              class="area-choose"
            />
          </el-form-item>
          <el-form-item label="参数名称：" label-width="120px" prop="paramName" :rules="detailFormRules.required">
            <el-input v-model="form.paramName" maxlength="20" show-word-limit clearable placeholder="请输入参数名称" />
          </el-form-item>
          <el-form-item label="参数值：" label-width="120px" prop="paramValue" :rules="detailFormRules.required">
            <el-input v-model="form.paramValue" maxlength="20" show-word-limit clearable placeholder="请输入参数值" />
          </el-form-item>
          <el-form-item label="排序：" label-width="120px" prop="paramSortCode">
            <el-input-number
              v-model="form.paramSortCode"
              :min="1"
              :max="99"
              value-on-clear="max"
              placeholder="请输入排序"
            />
          </el-form-item>
          <el-form-item label="参数备注：" label-width="120px" prop="paramRemark">
            <el-input
              v-model="form.paramRemark"
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
import { ref, nextTick } from "vue"
import commonFormRules from "@/utils/rules"
import { addApi, detailApi, getSParamByParentCodeApi, updApi } from "@/api/sys/param"
import { ElMessage } from "element-plus"

const dialogVisible = ref(false)
const dialogTitle = ref("新增参数")

const paramParentCodeProps = {
  label: "paramName",
  value: "paramCode",
  checkStrictly: true,
  lazy: true,
  async lazyLoad(node: any, resolve: any) {
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
    resolve(data)
  }
}

/** 打开弹窗 */
const open = async (tableData: any, id?: number) => {
  resetForm()
  dialogVisible.value = true
  formLoading.value = true
  dialogTitle.value = id ? "修改参数" : "新增参数"
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
  paramCode: undefined,
  paramParentCode: 1,
  paramName: "",
  paramValue: "",
  paramSortCode: 99,
  paramRemark: ""
})
const detailFormRules = {
  ...commonFormRules
}

const emit = defineEmits(["success"])

const handleSubmit = async () => {
  formRef.value?.validate((valid: boolean) => {
    if (valid && !formLoading.value) {
      if (!Array.isArray(form.value.paramParentCode) || form.value.paramParentCode.length == 0) {
        ElMessage.error("请选择父级")
        return
      }
      formLoading.value = true
      const api = form.value.paramCode === undefined ? addApi : updApi
      const formData = {
        ...form.value,
        paramParentCode: form.value.paramParentCode[form.value.paramParentCode.length - 1]
      }
      api(formData).then((res) => {
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
    paramCode: undefined,
    paramParentCode: 1,
    paramName: "",
    paramValue: "",
    paramSortCode: 99,
    paramRemark: ""
  }
  formRef.value?.resetFields()
}
</script>

<style lang="scss" scoped></style>
