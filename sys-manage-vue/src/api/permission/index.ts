import { request } from "@/utils/service"
import { PermissionRes } from "./types/permission"

/** 获取用户路由 */
export function getRouterByTokenApi() {
  return request<PermissionRes>({
    url: "s_menu/getRouterByToken",
    method: "get"
  })
}
