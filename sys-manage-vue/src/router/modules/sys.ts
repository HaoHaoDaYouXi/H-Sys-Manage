/** 系统管理 */
const Sys = {
  TestTable: () => import("@/views/sys/element-plus/index.vue"),
  SParam: () => import("@/views/sys/param/index.vue"),
  SApi: () => import("@/views/sys/api/index.vue"),
  SMenu: () => import("@/views/sys/menu/index.vue"),
  SRole: () => import("@/views/sys/role/index.vue"),
  SUser: () => import("@/views/sys/user/index.vue")
}

export default Sys
