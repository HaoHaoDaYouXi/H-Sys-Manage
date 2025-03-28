import { type RouteRecordRaw, createRouter } from "vue-router"
import { history } from "./util"
import { Home, Sys } from "@/router/modules"

const Layout = () => import("@/layouts/index.vue")

/** 常驻路由 */
export const constantRoutes: RouteRecordRaw[] = [
  {
    path: "/",
    component: () => import("@/views/homeMiddleware/index.vue"),
    meta: {
      hidden: true
    }
  },
  {
    path: "/redirect",
    component: Layout,
    meta: {
      hidden: true
    },
    children: [
      {
        path: ":path(.*)",
        component: () => import("@/views/redirect/index.vue")
      }
    ]
  },
  {
    path: "/login",
    component: () => import("@/views/login/index.vue"),
    meta: {
      hidden: true
    }
  },
  {
    path: "/404",
    name: "404",
    component: () => import("@/views/404/index.vue"),
    meta: { hidden: true, title: "找不到此页面" }
  },
  {
    path: "/:pathMatch(.*)",
    name: "notFound",
    redirect: "/404",
    meta: {
      hidden: true
    }
  }
]

/** 路由组件 */
export const componentsMap: { [key: string]: () => Promise<any> } = {
  Layout,
  ...Home,
  ...Sys,
}

const router = createRouter({
  history,
  routes: constantRoutes
})

/** 重置路由 */
export function resetRouter() {
  // 路由必须带有 Name 属性，否则会不能重置干净
  try {
    router.getRoutes().forEach((route) => {
      const { name } = route
      if (name) {
        router.hasRoute(name) && router.removeRoute(name)
      }
    })
  } catch {
    // 强制刷新浏览器也行，只是交互体验不是很好
    window.location.reload()
  }
}

export default router
