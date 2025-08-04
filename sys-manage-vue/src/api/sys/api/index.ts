import { request } from "@/utils/service"
import { commonApi } from "@/api/commonApi"

const API_PREFIX = "s_api"
const baseApi = commonApi(API_PREFIX)
export const pageListApi = baseApi.pageListApi
export const addApi = baseApi.addApi
export const detailApi = baseApi.detailApi
export const updApi = baseApi.updApi
export const batchDelApi = baseApi.batchDelApi

/** 获取模块列表 */
export function getModuleListApi(data: any) {
  return request<ApiRes<any>>({
    url: `${API_PREFIX}/getModuleList`,
    method: "post",
    data
  })
}
