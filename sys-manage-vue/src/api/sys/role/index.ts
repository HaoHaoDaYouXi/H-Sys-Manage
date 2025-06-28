import { request } from "@/utils/service"
import { LabelValue } from "@/api/commonTypes"
import { commonApi } from "@/api/commonApi"

const API_PREFIX = "s_role"
const baseApi = commonApi(API_PREFIX)
export const addApi = baseApi.addApi
export const detailApi = baseApi.detailApi
export const updApi = baseApi.updApi
export const batchDelApi = baseApi.batchDelApi

/** 查 */
export function getRoleTypeApi() {
  return request<ApiRes<LabelValue[]>>({
    url: `${API_PREFIX}/getRoleType`,
    method: "get"
  })
}
/** 查 */
export function pageListApi(data: any) {
  return request<ApiRes<any>>({
    url: `${API_PREFIX}/pageList`,
    method: "post",
    data
  })
}
