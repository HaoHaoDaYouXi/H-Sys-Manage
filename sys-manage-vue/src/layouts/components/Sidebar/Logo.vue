<template>
  <div class="sidebar-logo-container" :class="{ collapse: props.collapse }">
    <transition name="sidebarLogoFade">
      <router-link v-if="props.collapse" key="collapse" class="sidebar-logo-link" to="/">
        <img v-if="logo" :src="logo" class="sidebar-logo" alt="" />
        <h1 v-else class="sidebar-title">{{ title }}</h1>
      </router-link>
      <router-link v-else key="expand" class="sidebar-logo-link" to="/">
        <img v-if="logo" :src="logo" class="sidebar-logo" alt="" />
        <h1 class="sidebar-title">{{ title }}</h1>
      </router-link>
    </transition>
  </div>
</template>

<script setup lang="ts">
const title = import.meta.env.VITE_APP_TITLE
import logo from "/vite.svg"

interface Props {
  collapse?: boolean
}

const props = withDefaults(defineProps<Props>(), {
  collapse: true
})
</script>

<style lang="scss" scoped>
@import "@/styles/variables.module.scss";

.sidebarLogoFade-enter-active {
  transition: opacity 1.5s;
}

.sidebarLogoFade-enter,
.sidebarLogoFade-leave-to {
  opacity: 0;
}

.sidebar-logo-container {
  position: relative;
  width: 100%;
  padding-top: 10px;
  background: $menuBg;
  text-align: center;
  overflow: hidden;

  & .sidebar-logo-link {
    height: 100%;
    width: 100%;

    & .sidebar-logo {
      width: 32px;
      height: 32px;
      vertical-align: middle;
      margin-right: 12px;
    }

    & .sidebar-title {
      display: inline-block;
      margin: 0;
      color: $menuText;
      font-weight: 600;
      line-height: 21px;
      font-size: 14px;
      font-family:
        Avenir,
        Helvetica Neue,
        Arial,
        Helvetica,
        sans-serif;
      vertical-align: middle;
    }
  }

  &.collapse {
    .sidebar-logo {
      margin-right: 0;
    }
  }
}
</style>
