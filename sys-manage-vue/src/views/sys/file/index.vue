<template>
  <div class="app-container">
    <el-scrollbar height="100%" @end-reached="loadMore">
      <!-- 每个日期分组 -->
      <div v-for="group in dataList" :key="group.fzrtq" class="date-group">
        <div class="date-header">{{ group.fzrtq }}</div>
        <div class="file-list">
          <div v-for="file in group.files" :key="file.name" class="file-item">
            <!-- 这里可以放置文件图标，简单用文字表示 -->
            <div class="file-icon">📄</div>
            <span class="file-name">{{ file.name }}</span>
          </div>
        </div>
      </div>
      <!-- 加载提示 -->
      <div v-if="loading" class="loading-tip">加载中...</div>
      <div v-if="!hasMore && dataList.length > 0" class="loading-tip">没有更多了</div>
    </el-scrollbar>
  </div>
</template>

<script lang="ts" setup>
import { ref, onMounted } from "vue"
import type { ScrollbarDirection } from "element-plus"

defineOptions({
  name: "SFile"
})

// ---------- 类型定义 ----------
interface FileItem {
  name: string
}

interface DateGroup {
  fzrtq: string
  files: FileItem[]
}

// ---------- 数据状态 ----------
const dataList = ref<DateGroup[]>([])
const loading = ref(false)
const page = ref(1)
const pageSize = ref(5) // 每页加载几个日期分组
const total = ref(0)
const hasMore = ref(true)

// ---------- 模拟分页数据 ----------
const fetchData = (pageNum: number): Promise<DateGroup[]> => {
  return new Promise((resolve) => {
    setTimeout(() => {
      const totalGroups = 15
      total.value = totalGroups
      const start = (pageNum - 1) * pageSize.value
      const end = Math.min(start + pageSize.value, totalGroups)
      const groups: DateGroup[] = []
      for (let i = start; i < end; i++) {
        const day = String(i + 1).padStart(2, "0")
        // 每个日期下生成 3~5 个文件
        const fileCount = 3 + (i % 3)
        const files: FileItem[] = []
        for (let j = 0; j < fileCount; j++) {
          files.push({ name: `文件${i * 10 + j + 1}` })
        }
        groups.push({
          fzrtq: `2026-01-${day}`,
          files
        })
      }
      resolve(groups)
    }, 400)
  })
}

// ---------- 加载数据 ----------
const loadData = async (isAppend: boolean = true) => {
  if (loading.value) return
  if (!hasMore.value && isAppend) return

  loading.value = true
  try {
    const newGroups = await fetchData(page.value)
    if (isAppend) {
      dataList.value = [...dataList.value, ...newGroups]
    } else {
      dataList.value = newGroups
    }
    hasMore.value = dataList.value.length < total.value
    if (hasMore.value) {
      page.value++
    }
  } catch (error) {
    console.error("加载失败", error)
  } finally {
    loading.value = false
  }
}

// ---------- 滚动触底加载更多 ----------
const loadMore = (direction: ScrollbarDirection) => {
  if (direction === "bottom") {
    loadData(true)
  }
}

// ---------- 初始化 ----------
const init = async () => {
  page.value = 1
  hasMore.value = true
  dataList.value = []
  await loadData(false)
}

onMounted(() => {
  init()
})
</script>

<style lang="scss" scoped>
.app-container {
  height: 400px; // 根据实际容器调整
}

.date-group {
  margin: 16px 10px;
  background: var(--el-color-primary-light-9);
  border-radius: 8px;
  padding: 12px 16px;

  .date-header {
    font-weight: 600;
    font-size: 16px;
    color: var(--el-color-primary);
    margin-bottom: 12px;
    padding-bottom: 8px;
    border-bottom: 1px solid var(--el-border-color-light);
  }

  .file-list {
    display: flex;
    flex-wrap: wrap;
    gap: 12px; // 文件之间的间距

    .file-item {
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      width: 320px; // 固定宽度，可根据需要调整
      height: 200px;
      padding: 10px 0;
      background: #fff;
      border-radius: 6px;
      box-shadow: 0 2px 6px rgba(0, 0, 0, 0.06);
      transition: box-shadow 0.2s;
      cursor: default;

      &:hover {
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.12);
      }

      .file-icon {
        font-size: 28px;
        line-height: 1.2;
        margin-bottom: 4px;
      }

      .file-name {
        font-size: 18px;
        color: var(--el-text-color-primary);
        text-align: center;
        word-break: break-all;
        max-width: 70px;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
      }
    }
  }
}

.loading-tip {
  text-align: center;
  padding: 12px;
  color: var(--el-text-color-secondary);
  font-size: 14px;
}
</style>
