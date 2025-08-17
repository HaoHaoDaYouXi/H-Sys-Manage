<template>
  <div class="upload-wrapper">
    <component :is="h(ElUpload, { ...$attrs, ...props, ref: changeRef }, $slots)" />
  </div>
</template>

<script lang="ts" setup>
import { getCurrentInstance, h } from "vue"
import { ElUpload, type UploadProps } from "element-plus"

const props = defineProps<Partial<UploadProps>>()
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
    ::v-deep .el-upload {
      cursor: not-allowed;
    }
  }

  ::v-deep .el-upload--picture-card {
    height: 80px;
    width: 80px;
    line-height: 80px;
  }
}

.upload-wrapper ::v-deep .el-upload-list--picture-card {
  display: flex;
  flex-flow: row wrap;

  .el-upload-list__item {
    height: 80px;
    width: 80px;
  }
}

.hide {
  ::v-deep .el-upload {
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
