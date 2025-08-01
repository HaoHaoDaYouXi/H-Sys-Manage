﻿import { ref } from "vue"
import store from "@/store"
import { defineStore } from "pinia"
import { componentsMap, constantRoutes } from "@/router"
import { getRouterByTokenApi } from "@/api/sys/menu"
import { TopId, TrueFalseEnum } from "@/utils/enums"

export const usePermissionStore = defineStore("permission", () => {
  /** 可访问的路由 */
  const routes = ref<any[]>([])
  /** 是否已经添加路由 */
  const booAddRoutes = ref<boolean>(false)
  /** 添加路由 */
  const addRouter = ref<any[]>([])
  /** 当前使用路由 */
  const currentRoutes = ref(undefined)
  /** 默认打开的路由 */
  const defaultOpenRoute = ref(undefined)
  /** 面包屑列表 */
  const breadcrumbList = ref<any[]>([])
  /** 重置状态 */
  const resetState = () => {
    routes.value = []
    booAddRoutes.value = false
    addRouter.value = []
    currentRoutes.value = undefined
    defaultOpenRoute.value = undefined
    breadcrumbList.value = []
  }

  /** 获取路由 */
  const getRouterByUser = async () => {
    const { data } = await getRouterByTokenApi() // 调用接口获取路由信息
    // 格式化路由对象
    addRouter.value = generateRoutes(data)
    defaultOpenRoute.value = addRouter.value[0].children[0]
    routes.value = constantRoutes.concat(addRouter.value)
  }
  /** 格式化路由 */
  const generateRoutes = (routers: any[]) => {
    /**
     * 将路由对象格式化为标准格式
     */
    const formatRoute = (item: any, children: any[]) => {
      const child: any[] = []
      const btnKeys: string[] = []
      children.forEach((item) => {
        if (item.menuType === 3) {
          btnKeys.push(item.menuKey)
        } else {
          child.push(item)
        }
      })
      return {
        id: item.menuId,
        menuParentId: item.menuParentId,
        menuType: item.menuType,
        path: item.menuKey,
        name: item.menuComponent === "Layout" ? item.menuKey.replace(/^\//, "") : item.menuComponent,
        component: componentsMap[item.menuComponent],
        disabled: item.disabled,
        meta: {
          title: item.menuName,
          icon: item.menuIcon,
          hidden: item.hidden === TrueFalseEnum.TRUE,
          activeMenu: item.activeMenu,
          breadcrumb: item.breadcrumb === TrueFalseEnum.TRUE,
          affix: item.affix === TrueFalseEnum.TRUE,
          alwaysShow: item.alwaysShow === TrueFalseEnum.TRUE,
          cachedView: item.cachedView === TrueFalseEnum.TRUE,
          showOrder: item.showOrder,
          btnKeys: btnKeys
        },
        children: child
      }
    }
    const getChild = (data: any[], parentId: any): any[] => {
      const child = data
        .filter((item) => item.menuParentId === parentId)
        .sort((a, b) => {
          if (a.showOrder > b.showOrder) return -1
          if (a.showOrder < b.showOrder) return 1
          if (a.menuId < b.menuId) {
            return 1
          } else {
            return -1
          }
        })
      if (child.length > 0) {
        return child.map((item) => {
          return formatRoute(item, getChild(data, item.menuId))
        })
      } else {
        return []
      }
    }
    return getChild(routers, TopId)
  }

  return {
    resetState,
    routes,
    booAddRoutes,
    addRouter,
    currentRoutes,
    defaultOpenRoute,
    breadcrumbList,
    getRouterByUser
  }
})

/** 在 setup 外使用 */
export function usePermissionStoreHook() {
  return usePermissionStore(store)
}
