import { request } from "@/utils/service"

const API_PREFIX = "s_param"

/** 获取所有参数 */
export function getAllParamApi() {
  return request<ApiRes<any>>({
    url: `${API_PREFIX}/getAllParam`,
    method: "get"
  })
}
