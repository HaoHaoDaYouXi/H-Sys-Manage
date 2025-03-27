/** 系统管理 */
const Sys = {
  Table: () => import("@/views/sys/element-plus/index.vue"),
  Param: () => import("@/views/sys/param/index.vue"),
  Menu: () => import("@/views/sys/menu/index.vue"),
  Role: () => import("@/views/sys/role/index.vue"),
  User: () => import("@/views/sys/user/index.vue"),
}

export default Sys
