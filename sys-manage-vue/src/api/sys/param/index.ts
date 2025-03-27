import { request } from "@/utils/service"

const API_PREFIX = "s_param"

/** 改变使用角色 */
export function getAllParamApi(params: any) {
  return request<ApiRes<any>>({
    url: `${API_PREFIX}/getAllParam`,
    method: "get",
    params
  })
}
