<template>
  <div class="upload-wrapper">
    <el-upload
      ref="uploadRef"
      name="file"
      :class="[
        props.fileList.length >= props.limit ? 'hide' : props.fileType !== 3 ? 'flex flex-wrap' : '',
        !uploadParams.api ? 'is-disabled' : ''
      ]"
      :action="uploadParams.api || ''"
      :headers="headers"
      :data="props.formData"
      :accept="uploadParams.extension"
      :limit="props.limit"
      :file-list="props.fileList"
      :list-type="props.listType"
      :multiple="props.limit > 1"
      :on-preview="handlePreview"
      :on-remove="handleRemove"
      :on-success="handleAvatarSuccess"
      :on-error="handleAvatarError"
      :on-exceed="handleExceed"
      :before-upload="beforeAvatarUpload"
    >
      <i class="el-icon-plus" v-if="props.listType === 'picture-card'" />
      <el-button v-else icon="el-icon-paperclip" round>{{ props.placeholder }}</el-button>
      <template #tip v-if="!!props.tip">
        <div class="el-upload__tip">{{ computedTip }}</div>
      </template>
    </el-upload>
    <el-image-viewer v-if="showPreview" :url-list="previewImageUrl" show-progress @close="showPreview = false" />
  </div>
</template>

<script lang="ts" setup>
import { computed, getCurrentInstance, h, ref, withDefaults } from "vue"
import { ElMessage, ElUpload, UploadFile, UploadFiles, UploadProps, UploadRawFile, UploadUserFile } from "element-plus"
import { uploadTypeEnum } from "@/utils/enums"
import { getToken } from "@/utils/cache/cookies"
import { getBaseURL } from "@/utils/service"
import { API_PREFIX } from "@/api/sys/file"

interface Props extends Partial<UploadProps> {
  fileType?: number
  fileCount?: number
  limit?: number
  action?: string
  placeholder?: string
  maxSize?: number
  tip?: string
  width?: number
  height?: number
  formKey?: string
  formData?: Record<string, any>
  fileList?: UploadUserFile[]
  listType?: "text" | "picture" | "picture-card"
}

const props = withDefaults(defineProps<Props>(), {
  fileType: 1, // 文件保存的路径类型 0-其他 1-图片 2-附件 3-图片和附件 4-音频 5-视频 6-音频和视频
  fileCount: 0,
  limit: 1,
  action: "",
  placeholder: "选择文件",
  maxSize: 5 * 1024 * 1024, // 文件大小最大默认5M
  tip: "",
  width: 0,
  height: 0,
  formKey: "",
  formData: () => {
    return {}
  },
  fileList: () => {
    return []
  },
  listType: "picture-card" // text/picture/picture-card
})

// 动态计算tip属性
const computedTip = computed(() => {
  const m = props.maxSize / 1024 / 1024
  return props.tip || `只能上传${uploadParams.value.extension}文件，且不超过 ${m > 1024 ? m / 1024 + " G" : m + " M"}`
})

const uploadRef = ref()
const uploadParams = computed(() => {
  switch (props.fileType) {
    case 2:
      return { ...uploadTypeEnum.DOC, api: getBaseURL(API_PREFIX) + uploadTypeEnum.DOC.api }
    case 3:
      return { ...uploadTypeEnum.IMG_DOC, api: getBaseURL(API_PREFIX) + uploadTypeEnum.IMG_DOC.api }
    case 4:
      return { ...uploadTypeEnum.AUDIO, api: getBaseURL(API_PREFIX) + uploadTypeEnum.AUDIO.api }
    case 5:
      return { ...uploadTypeEnum.VIDEO, api: getBaseURL(API_PREFIX) + uploadTypeEnum.VIDEO.api }
    default:
      return { ...uploadTypeEnum.IMG, api: getBaseURL(API_PREFIX) + uploadTypeEnum.IMG.api }
  }
})

const headers = ref({
  "H-Token": getToken()
})

const emit = defineEmits(["changeFile"])

const previewImageUrl = ref<any[]>([])
const showPreview = ref(false)
const handlePreview = (uploadFile: UploadFile) => {
  // console.log("handlePreview", uploadFile)
  previewImageUrl.value = uploadFile.url ? [uploadFile.url] : []
  showPreview.value = true
}
const handleRemove = (uploadFile: any) => {
  // console.log("handleRemove", uploadFile)
  emit("changeFile", {
    type: "remove",
    formKey: props.formKey,
    fileCode: uploadFile.response ? uploadFile.response.data.fileCode : uploadFile.code
  })
}
const handleAvatarSuccess = (res: any, uploadFile: any) => {
  // console.log("handleAvatarSuccess", res, uploadFile)
  if (res.code === 20000) {
    const temp = res.data || {}
    emit("changeFile", {
      type: "add",
      formKey: props.formKey,
      fileName: uploadFile.name,
      ...temp
    })
    if (temp.zoomFile) {
      uploadFile.url = `${temp.prefixUrl}${temp.zoomFile}`
    }
  } else {
    ElMessage.error(res.message)
  }
}
const handleAvatarError = (error: Error, uploadFile: UploadFile, uploadFiles: UploadFiles) => {
  console.log("handleAvatarError", error)
}
const handleExceed = (files: File[], uploadFiles: UploadUserFile[]) => {
  ElMessage.error(`最大上传文件数量为 ${props.limit}`)
}
const beforeAvatarUpload = (rawFile: UploadRawFile) => {
  const format = rawFile.name.substring(rawFile.name.lastIndexOf("."))
  if (!uploadParams.value.extension.split(",").concat(format)) {
    ElMessage.error(`只能上传 ${uploadParams.value.extension || ""} 文件`)
    return false
  }
  if (rawFile.size > props.maxSize) {
    const m = props.maxSize / 1024 / 1024
    ElMessage.error(`文件大小不能超过 ${m > 1024 ? m / 1024 + " G" : m + " M"}`)
    return false
  }
  if (props.width && props.height) {
    return new Promise<void>(function (resolve, reject) {
      const _URL = window.URL || window.webkitURL
      const img = new Image()
      const objectUrl = _URL.createObjectURL(rawFile)
      img.onload = function () {
        const valid = img.width <= props.width && img.height <= props.height
        _URL.revokeObjectURL(objectUrl)
        valid ? resolve() : reject()
      }
      img.onerror = function () {
        _URL.revokeObjectURL(objectUrl)
        reject(new Error("图片加载失败"))
      }
      img.src = objectUrl
    })
      .then(() => {
        return true
      })
      .catch(() => {
        ElMessage.error(`上传图片像素最大是${props.width}*${props.height}!`)
        return false
      })
  }
  return true
}

const vm = getCurrentInstance()
function changeRef(instance: any) {
  if (vm) {
    vm.exposeProxy = vm.exposed = instance || {}
  }
}
</script>

<style lang="scss" scoped>
.upload-wrapper {
  .is-disabled {
    :deep(.el-upload) {
      cursor: not-allowed;
    }
  }

  :deep(.el-upload--picture-card) {
    height: 80px;
    width: 80px;
    line-height: 80px;
  }
}

.upload-wrapper :deep(.el-upload-list--picture-card) {
  display: flex;
  flex-flow: row wrap;

  .el-upload-list__item {
    height: 80px;
    width: 80px;
  }
}

.hide {
  :deep(.el-upload) {
    &.el-upload--picture-card,
    &.el-upload--text {
      display: none;
    }
  }
}

.object-fit {
  object-fit: fill;
}
</style>
