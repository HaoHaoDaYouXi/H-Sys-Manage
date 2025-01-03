export interface LoginReq {
  /** 账号 */
  account: string
  /** 密码 */
  pwd: string
}

export interface UserLoginCacheBO {
  userId: number
  account: string
  userCode: string
  userName: string
  userAvatar: string
  userContact: string
  remarks: string
  multipleStatus: number
}
export interface UserLinkLoginCacheBO {
  userRoles: UserRoles[]
}
export interface UserRoles {
  id: number
  roleId: number
  roleName: string
  roleCode: string
  useStatus: number
}
export type LoginRes = ApiRes<{
  hToken: string
  userLoginCacheBO: UserLoginCacheBO
  userLinkLoginCacheBO: UserLinkLoginCacheBO
}>
