<template>
  <div>
    <el-drawer class="base-el-drawer" :title="dialogTitle" v-model="dialogVisible" size="40%" append-to-body>
      <el-scrollbar class="base-scrollbar">
        <el-form ref="formRef" :model="form" :rules="detailFormRules" style="width: 90%" v-loading="formLoading">
          <el-form-item label="用户名称：" label-width="120px" prop="userName" :rules="detailFormRules.userName">
            <el-input v-model="form.userName" maxlength="64" show-word-limit clearable placeholder="请输入用户名称" />
          </el-form-item>
          <el-form-item label="账号：" label-width="120px" prop="account" :rules="detailFormRules.account">
            <el-input v-model="form.account" maxlength="64" show-word-limit clearable placeholder="请输入账号" />
          </el-form-item>
          <el-form-item v-if="!form.userId" label="密码：" label-width="120px" prop="pwd" :rules="detailFormRules.pwd">
            <el-input
              type="password"
              v-model="form.pwd"
              maxlength="64"
              show-word-limit
              clearable
              show-password
              placeholder="请输入密码"
            />
          </el-form-item>
          <el-form-item
            v-if="!form.userId"
            label="确定密码："
            label-width="120px"
            prop="pwd2"
            :rules="detailFormRules.pwd2"
          >
            <el-input
              type="password"
              v-model="form.pwd2"
              maxlength="64"
              show-word-limit
              clearable
              show-password
              placeholder="请输入确定密码"
            />
          </el-form-item>
          <el-form-item label="头像：" label-width="120px" prop="avatar">
            <Upload
              formKey="userAvatar"
              :fileList="form.userAvatar"
              :fileCount="form.userAvatar.length"
              @changeFile="changeFile"
            />
          </el-form-item>
          <el-form-item label="联系方式：" label-width="120px" prop="userContact">
            <el-input
              v-model="form.userContact"
              maxlength="64"
              show-word-limit
              clearable
              placeholder="请输入联系方式"
            />
          </el-form-item>
          <el-form-item label="备注：" label-width="120px" prop="remarks">
            <el-input
              type="textarea"
              v-model="form.remarks"
              maxlength="64"
              show-word-limit
              clearable
              resize="none"
              placeholder="备注"
            />
          </el-form-item>
          <el-form-item label="是否多人使用：" label-width="120px" prop="multipleStatus">
            <el-switch
              v-model="form.multipleStatus"
              :active-value="TrueFalseEnum.TRUE"
              :inactive-value="TrueFalseEnum.FALSE"
            />
          </el-form-item>
          <el-form-item label="角色权限：" label-width="120px" prop="remarks">
            <el-select
              ref="userRoleRef"
              v-model="form.roleIds"
              multiple
              clearable
              collapse-tags
              placeholder="角色权限"
              popper-class="custom-header"
              :max-collapse-tags="5"
              filterable
              remote
              remote-show-suffix
              :remote-method="remoteMethod"
              :loading="roleLoading"
            >
              <el-option v-for="item in roleList" :key="item.value" :label="item.label" :value="item.value" />
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
import { ref, nextTick, watch, onMounted, onUnmounted } from "vue"
import commonFormRules from "@/utils/rules"
import { TopId, TrueFalseEnum } from "@/utils/enums"
import { pageListApi } from "@/api/sys/role"
import { LabelValue } from "@/api/commonTypes"
import { ElMessage, CheckboxValueType } from "element-plus"
import Upload from "@/components/upload/index.vue"
import { addApi, detailApi, updApi } from "@/api/sys/user"
import {getDetailApiUrl} from "@/api/sys/file";

const dialogVisible = ref(false)
const dialogTitle = ref("新增")
const formLoading = ref(false)
const formRef = ref()
const form = ref({
  userId: undefined,
  account: "",
  pwd: "",
  pwd2: "",
  userName: "",
  userAvatar: [],
  userContact: "",
  remarks: "",
  multipleStatus: undefined,
  roleIds: [] as any[]
})
const resetForm = () => {
  form.value = {
    userId: undefined,
    account: "",
    pwd: "",
    pwd2: "",
    userName: "",
    userAvatar: [],
    userContact: "",
    remarks: "",
    multipleStatus: undefined,
    roleIds: []
  }
  formRef.value?.resetFields()
}
const validatePwd2 = (rule: any, value: string, callback: any) => {
  if (!value) {
    callback(new Error("请输入确定密码"))
  } else if (value !== form.value.pwd) {
    callback(new Error("两次输入的密码不一致"))
  } else {
    callback()
  }
}
const detailFormRules = {
  account: [{ required: true, message: "请输入账号", trigger: "blur" }],
  pwd: [{ required: true, message: "请输入密码", trigger: "blur" }],
  pwd2: [
    { required: true, message: "请输入确定密码", trigger: "blur" },
    { validator: validatePwd2, trigger: "blur" }
  ],
  userName: [{ required: true, message: "请输入用户名称", trigger: "blur" }],
  ...commonFormRules
}

const userRoleRef = ref()
const roleList = ref<any[]>([])
const roleLoading = ref(false)
const remoteMethod = async (query: string) => {
  roleLoading.value = true
  roleList.value = []
  const { data } = await pageListApi({
    pageNum: 1,
    pageSize: 100,
    roleName: query || ""
  })
  roleList.value = data.list.map((item: any) => ({
    value: item.roleId,
    label: item.roleName
  }))
  roleLoading.value = false
}

/** 打开弹窗 */
const open = async (id?: number) => {
  resetForm()
  dialogVisible.value = true
  formLoading.value = true
  dialogTitle.value = id ? "修改" : "新增"
  await remoteMethod("")
  if (id) {
    const detail = await detailApi(id)
    form.value = {
      ...detail.data,
      userAvatar: [{ code: detail.data.userAvatar, url: await getDetailApiUrl(detail.data.userAvatar) }]
    }
  }
  formLoading.value = false
}
/** 提供 open 方法，用于打开弹窗 */
defineExpose({ open })

const changeFile = (res: any) => {
  console.log(res)
  // 确保 formKey 存在且对应的属性是数组
  if (res.formKey && Array.isArray(form.value[res.formKey as keyof typeof form.value])) {
    const key = res.formKey as keyof typeof form.value
    if (res.type === "add") {
      // 添加文件时，将 fileCode 添加到数组中
      if (res.fileCode) {
        form.value[key].push({ code: res.fileCode, url: res.fileCode })
      }
    } else if (res.type === "remove") {
      // 删除文件时，从数组中移除对应的文件
      if (res.fileCode) {
        form.value[key] = form.value[key].filter((item: any) => item.code !== res.fileCode)
      }
    }
  }
}

const emit = defineEmits(["success"])
const handleSubmit = async () => {
  formRef.value?.validate((valid: boolean) => {
    if (valid) {
      formLoading.value = true
      const api = form.value.userId ? updApi : addApi
      console.log(form.value)
      const tmpForm = {
        ...form.value,
        userAvatar: form.value.userAvatar ? form.value.userAvatar[0].code : ""
      }
      api(tmpForm)
        .then((res) => {
          ElMessage.success(res.message)
          emit("success")
          dialogVisible.value = false
        })
        .catch((error) => {
          ElMessage.error("提交失败，请稍后重试")
        })
      formLoading.value = false
    }
  })
}
</script>

<style lang="scss" scoped></style>
