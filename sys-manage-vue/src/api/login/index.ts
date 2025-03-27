import { request } from "@/utils/service"
import type * as Login from "./types/login"

const API_PREFIX = "login"

/** 登录并返回 Token */
export function loginApi(data: Login.LoginReq) {
  return request<Login.LoginRes>({
    url: `${API_PREFIX}/account`,
    method: "post",
    data
  })
}
/** 获取登录缓存 */
export function loginCacheApi() {
  return request<Login.LoginRes>({
    url: `${API_PREFIX}/getLoginCache`,
    method: "post"
  })
}
