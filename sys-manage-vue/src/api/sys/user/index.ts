import { request } from "@/utils/service"
import { commonApi } from "@/api/commonApi"

const API_PREFIX = "s_user"
const baseApi = commonApi(API_PREFIX)
export const addApi = baseApi.addApi
export const detailApi = baseApi.detailApi
export const updApi = baseApi.updApi
export const batchDelApi = baseApi.batchDelApi

/** 改变使用角色 */
export function changeUseRoleApi(params: any) {
  return request<ApiRes<any>>({
    url: `${API_PREFIX}/changeUseRole`,
    method: "get",
    params
  })
}
