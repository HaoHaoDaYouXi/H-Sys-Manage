import { request } from "@/utils/service"

const API_PREFIX = "s_user"

/** 改变使用角色 */
export function changeUseRole(params: any) {
  return request<ApiRes<any>>({
    url: `${API_PREFIX}/changeUseRole`,
    method: "get",
    params
  })
}
