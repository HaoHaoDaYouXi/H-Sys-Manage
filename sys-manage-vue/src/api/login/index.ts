import { request } from "@/utils/service"
import type * as Login from "./types/login"

/** 登录并返回 Token */
export function loginApi(data: Login.LoginReq) {
  return request<Login.LoginRes>({
    url: "login/account",
    method: "post",
    data
  })
}
/** 获取登录缓存 */
export function loginCacheApi() {
  return request<Login.LoginRes>({
    url: "login/getLoginCache",
    method: "post"
  })
}
