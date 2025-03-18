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
export function listByParentApi(data: Menu.SMenuListReq) {
  return request<Menu.SMenuListRes>({
    url: `${API_PREFIX}/listByParent`,
    method: "post",
    data
  })
}

export function addApi(data: any) {
  return request<ApiRes<string>>({
    url: `${API_PREFIX}/add`,
    method: "post",
    data
  })
}

export function detailApi(id: any) {
  return request<ApiRes<any>>({
    url: `${API_PREFIX}/detail/${id}`,
    method: "get"
  })
}

export function updApi(data: any) {
  return request<ApiRes<string>>({
    url: `${API_PREFIX}/upd`,
    method: "post",
    data
  })
}
