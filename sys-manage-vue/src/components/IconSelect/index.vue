<template>
  <div>
    <el-popover
      ref="popoverRef"
      placement="top-start"
      width="460"
      trigger="click">
      <template #reference>
        <div class="icon-select">
          <el-button
            v-if="!props.exData.icon"
            class="el-icon-plus icon-select-trigger"></el-button>
          <p
            v-else
            class="icon-select-result-box"
            @mouseenter.stop="booClear = true"
            @mouseleave.stop="booClear = false">
            <el-button
              size="small"
              class="icon-select-result"
              :style="{ color: props.exData.color }">
              <IconItem :icon="props.exData.icon" :defaultStyle="true" />
            </el-button>
            <el-button
              size="small"
              class="el-icon-circle-close icon-select-clear"
              @click.stop="handleClear"
              v-if="booClear"></el-button>
          </p>
        </div>
      </template>
      <template v-if="showColorConfig()">
        <p class="subtitle">颜色选择</p>
        <div class="color-box">
          <el-button
            v-for="(item, index) in colorList"
            :key="index"
            :class="props.exData.color === item.value ? 'gou' : ''"
            :style="{ backgroundColor: item.value, backgroundImage: item.img }"
            @click="setColor(item.value)"></el-button>
        </div>
      </template>

      <p class="subtitle">图标选择</p>
      <div class="marginB-15">
        <el-input
          placeholder="请输入内容"
          v-model="searchForm.icon"
          clearable>
          <template #append>
            <el-button
              icon="search"
              @click="searchSubmit"></el-button>
          </template>
        </el-input>
      </div>
      <el-tabs v-model="tabsModel">
        <el-tab-pane
          v-for="(pane, index) in IconJson"
          :key="index"
          :label="pane.label"
          :name="pane.name">
          <el-divider border-style="dashed" class="tab-divider" />
          <el-scrollbar height="220px">
            <ul class="ml-2 flex flex-wrap">
              <li
                v-for="(item, key) in pane.value"
                :title="item"
                :key="pane.prefix+key"
                :style="iconItemStyle(item)"
                class="icon-item mr-2 mt-1 w-1/10 flex cursor-pointer items-center justify-center border border-solid p-2"
                @click="setIcon(pane.prefix+item)">
                <IconItem :icon="pane.prefix+item" :defaultStyle="true" />
              </li>
            </ul>
          </el-scrollbar>
        </el-tab-pane>
      </el-tabs>
    </el-popover>
  </div>
</template>

<script lang="ts" setup>
import { ref, computed } from "vue"
import { ElPopover } from 'element-plus'
import IconItem from "../Icon/item.vue"
import { IconJson } from "@/icons"

interface ExData {
  icon: string
  color?: string
  [key: string]: any // 允许其他属性
}

const props = withDefaults(defineProps<{ exData: ExData }>(), {
  exData: () => ({
    icon: "",
    color: ""
  })
})

const popoverRef = ref<InstanceType<typeof ElPopover> | null>(null);

const colorList = [
  { label: '', value: '#2975F9' },
  { label: '', value: '#585BFF' },
  { label: '', value: '#F53F3F' },
  { label: '', value: '#FF943E' },
  { label: '', value: '#15BC83' },
  {
    label: '',
    value: '',
    img: 'linear-gradient(45deg, #00000040, #00000040),linear-gradient(45deg,#eee 25%,transparent 0,transparent 75%,#eee 0,#eee),linear-gradient(45deg, #eee 25%, #fff 0, #fff 75%, #eee 0, #eee)'
  }
]
const booClear = ref(false)

const searchForm = ref({
  icon: ''
})

const tabsModel = ref(IconJson[0].name)

const iconItemStyle = computed((): ParameterCSSProperties => {
  return (item) => {
    if (props.exData.icon === item) {
      return {
        borderColor: 'var(--el-color-primary)',
        color: 'var(--el-color-primary)'
      }
    }
  }
})

const showColorConfig = () => {
  return Object.prototype.hasOwnProperty.call(props.exData, 'color')
}

const emit = defineEmits<{ (e: 'changeIcon', value: {
    icon: string
    color?: string
    [key: string]: any
  }): void }>()

const setIcon = (icon: string) => {
  emit('changeIcon', {
    ...props.exData,
    icon
  })
  if (popoverRef.value) {
    popoverRef.value.hide(); // 使用 hide 方法关闭弹出框
  }
}

const setColor = (color: string) => {
  emit('changeIcon', {
    ...props.exData,
    color
  })
}

const handleClear = () => {
  emit('changeIcon', {
    icon: '',
    color: ''
  })
}

const searchSubmit = () => {
  searchForm.value.icon = searchForm.value.icon.trim()
  if (searchForm.value.icon) {
    pageNum.value = 1
    setIconList(iconList.value.filter((item: string) => item.includes(searchForm.value.icon.trim().toLowerCase())))
  } else {
    setIconList()
  }
}
</script>

<style lang="scss" scoped>
.el-divider--horizontal {
  margin: 1px auto !important;
}

.tab-divider.el-divider--horizontal {
  margin: 0 !important;
}

.icon-item {
  &:hover {
    color: var(--el-color-primary);
    border-color: var(--el-color-primary);
    transform: scaleX(1.05);
    transition: all 0.4s;
  }
}

:deep(.el-tabs__nav-next) {
  font-size: 15px;
  line-height: 32px;
  box-shadow: -5px 0 5px -6px #ccc;
}

:deep(.el-tabs__nav-prev) {
  font-size: 15px;
  line-height: 32px;
  box-shadow: 5px 0 5px -6px #ccc;
}

:deep(.el-tabs__item) {
  height: 30px;
  font-size: 12px;
  font-weight: normal;
  line-height: 30px;
}

:deep(.el-tabs__header),
:deep(.el-tabs__nav-wrap) {
  position: static;
  margin: 0;
}


.icon-select {
  width: 32px;

  &-trigger {
    width: 32px;
    height: 32px;
    padding: 0;
    border-radius: 8px;
    vertical-align: top;
  }

  &-result-box {
    position: relative;
    left: 0;
    top: 0;
  }

  &-result {
    padding: 0;
    border: none;
    font-size: 32px;
    vertical-align: top;

    &:hover {
      color: inherit;
      background-color: transparent;
    }
  }

  &-clear {
    position: absolute;
    left: 20px;
    top: -5px;
    z-index: 1;
    padding: 0;
    margin: 0;
    font-size: 20px;
    color: #666666;
    background-color: #DEE1E5;
    border-radius: 50%;
    border: 1px solid #FFFFFF;
  }
}

.subtitle {
  margin-bottom: 12px;
  font-size: 14px;
  font-family: PingFang SC-Medium, PingFang SC,serif;
  color: #333333;
}

.color-box {
  display: flex;
  align-items: center;
  margin-bottom: 24px;

  ::v-deep .el-button {
    width: 18px;
    height: 18px;
    padding: 0;
    border: none;
    border-radius: 50%;
    background-size: 4px 4px;
    background-position: 0 0, 0 0, 2px 2px;

    & + & {
      margin-left: 16px;
    }

    &.gou {
      position: relative;
      left: 0;
      top: 0;

      &::before {
        position: absolute;
        left: 7px;
        top: 4px;
        z-index: 1;
        content: '';
        width: 4px;
        height: 7px;
        border-right: 1px solid #FFF;
        border-bottom: 1px solid #FFF;
        transform: rotate(40deg);
      }
    }
  }
}

.icon-box {
  position: relative;
  left: 0;
  top: 0;
  height: 180px;
}

.empty {
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
  color: #606266;
}

.icon-list {
  display: inline-block;
  height: 28px;
  margin-left: 16px;
  margin-bottom: 8px;
  color: #666666;
  cursor: pointer;
  font-size: 0;

  &:hover,
  &.selected {
    background: #FFFFFF;
    box-shadow: 0 0 10px 0 rgba(0, 0, 0, 0.302);
    border-radius: 2px;
  }

  &:nth-of-type(10n + 1) {
    margin-left: 0;
  }

  .icon {
    display: block;
    width: 28px;
    height: 100%;
    line-height: 28px;
    color: inherit;
    font-size: 20px;
    text-align: center;
  }
}
</style>
