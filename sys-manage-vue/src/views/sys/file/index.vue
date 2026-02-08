<template>
  <div class="app-container">
    <el-scrollbar height="100%" @end-reached="loadMore">
      <p v-for="item in num" :key="item" class="scrollbar-demo-item">
        {{ item }}
      </p>
    </el-scrollbar>
  </div>
</template>

<script lang="ts" setup>
import { onMounted, reactive, ref, watch } from "vue"

defineOptions({
  // 命名当前组件
  name: "SFile"
})

import type { ScrollbarDirection } from  "element-plus"

const num = ref(30)

const loadMore = (direction: ScrollbarDirection) => {
  if (direction === "bottom") {
    num.value += 5
  }
}

const loading = ref<boolean>(false)

const init = async () => {

}

onMounted(async () => {
  await init()
})
</script>

<style lang="scss" scoped>
.scrollbar-demo-item {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 50px;
  margin: 10px;
  text-align: center;
  border-radius: 4px;
  background: var(--el-color-primary-light-9);
  color: var(--el-color-primary);
}
.el-slider {
  margin-top: 20px;
}
</style>
