<!-- icon 组件 -->
<template>
  <SvgIcon v-if="isSvgIcon" :name="iconName" :class="iconClass" />
  <i v-else-if="isElIcon || isIconfont" :class="iconClass" />
  <el-icon v-else>
    <component :is="props.icon" :class="iconClass" />
  </el-icon>
</template>

<script lang="ts" setup>
import { computed } from "vue"

interface Props {
  icon?: string
  defaultStyle?: boolean
}

const props = withDefaults(defineProps<Props>(), {
  icon: "",
  defaultStyle: false,
  color: ""
})

const isSvgIcon = computed(() => props.icon.includes("svg-icon"))
const isElIcon = computed(() => props.icon.includes("el-icon"))
const isIconfont = computed(() => props.icon.includes("iconfont"))

const iconName = computed(() => {
  if (isSvgIcon.value) {
    return props.icon.replace("svg-icon-", "")
  }
  return props.icon
})

const iconClass = computed(() => {
  if (isSvgIcon.value) {
    return props.defaultStyle ? [] : ["el-icon", "sub-el-icon"]
  }
  if (isElIcon.value) {
    return props.defaultStyle ? [props.icon, "icon"] : [props.icon, "el-icon", "sub-el-icon", "mr-8"]
  }
  if (isIconfont.value) {
    return props.defaultStyle ? [props.icon, "icon"] : [props.icon, "el-icon", "sub-el-icon", "mr-5", "text-18"]
  }
  return props.defaultStyle ? [props.icon, "icon"] : ["el-icon", "sub-el-icon"]
})
</script>

<style lang="scss" scoped>
.sub-el-icon {
  width: 24px;
  display: inline-block;
  text-align: center;
  color: currentColor;
}
.mr-8 {
  margin-right: 8px;
}
.mr-5 {
  margin-right: 5px;
}
.text-18 {
  font-size: 18px;
}
</style>
