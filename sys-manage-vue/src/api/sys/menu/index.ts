import { request } from "@/utils/service"
import type * as Menu from "./types/menu"
import { commonApi } from "@/api/commonApi"

const API_PREFIX = "s_menu"
const baseApi = commonApi(API_PREFIX)
export const addApi = baseApi.addApi
export const detailApi = baseApi.detailApi
export const updApi = baseApi.updApi
export const batchDelApi = baseApi.batchDelApi

/** 获取用户路由 */
export function getRouterByTokenApi() {
  return request<ApiRes<[]>>({
    url: `${API_PREFIX}/getRouterByToken`,
    method: "get"
  })
}

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

export function labelValueByParentApi(parentId: any) {
  return request<ApiRes<any>>({
    url: `${API_PREFIX}/labelValueByParent/${parentId}`,
    method: "get"
  })
}

export function changeDisableApi(data: any) {
  return request<ApiRes<boolean>>({
    url: `${API_PREFIX}/changeDisable`,
    method: "post",
    data
  })
}
