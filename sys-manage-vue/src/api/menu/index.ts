import { request } from "@/utils/service"
import type * as Menu from "./types/menu"

const API_PREFIX = "s_menu"

/** 查 */
export function getMenuTypeApi() {
  return request<ApiRes<[]>>({
    url: `${API_PREFIX}/getMenuType`,
    method: "get"
  })
}
/** 查 */
export function listByParentApi(params: Menu.SMenuListReq) {
  return request<Menu.SMenuListRes>({
    url: `${API_PREFIX}/listByParent`,
    method: "post",
    params
  })
}
