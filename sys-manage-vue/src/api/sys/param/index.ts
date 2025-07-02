import { request } from "@/utils/service"
import { commonApi } from "@/api/commonApi"

const API_PREFIX = "s_param"
const baseApi = commonApi(API_PREFIX)
export const addApi = baseApi.addApi
export const detailApi = baseApi.detailApi
export const updApi = baseApi.updApi
export const batchDelApi = baseApi.batchDelApi

/** 获取所有参数 */
export function getAllParamApi() {
  return request<ApiRes<any>>({
    url: `${API_PREFIX}/getAllParam`,
    method: "get"
  })
}
/** 根据父级Code获取 */
export function getSParamByParentCodeApi(params: any) {
  return request<ApiRes<any>>({
    url: `${API_PREFIX}/getSParamByParentCode`,
    method: "get",
    params
  })
}
