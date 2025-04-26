import { computed, reactive, ref } from "vue"
import store from "@/store"
import { defineStore } from "pinia"
import { usePermissionStoreHook } from "./permission"
import { useTagsViewStore } from "./tags-view"
import { useSettingsStore } from "./settings"
import { getToken, removeToken, setToken } from "@/utils/cache/cookies"
import { resetRouter } from "@/router"
import { loginApi, loginCacheApi } from "@/api/login"
import { type LoginReq, UserRoles } from "@/api/login/types/login"
import { changeUseRoleApi } from "@/api/sys/user"

export interface UserInfoData {
  userName: string
  avatar: string
  userRoleId: number
  roleName: string
}

export const useUserStore = defineStore("user", () => {
  const token = ref<string>(getToken() || "")
  const userInfo: UserInfoData = reactive({
    userName: "",
    avatar: "",
    userRoleId: 0,
    roleName: ""
  })
  const roleList = ref<UserRoles[]>([])
  /** 设置用户信息 */
  const setUserInfo = (userInfo: UserInfoData, roles: UserRoles[]) => {
    userInfo.userName = userInfo.userName || ""
    userInfo.avatar = userInfo.avatar || ""
    userInfo.userRoleId = userInfo.userRoleId || 0
    userInfo.roleName = userInfo.roleName || ""
    roleList.value = roles || []
  }
  /** 重置状态 */
  const resetState = () => {
    token.value = ""
    setUserInfo({ userName: "", avatar: "", userRoleId: 0, roleName: "" }, [])
  }

  const permissionStore = usePermissionStoreHook()
  const tagsViewStore = useTagsViewStore()
  const settingsStore = useSettingsStore()

  /** 登录 */
  const login = async ({ account, pwd }: LoginReq) => {
    const { data } = await loginApi({ account, pwd })
    setToken(data.hToken)
    token.value = data.hToken
  }

  /** 获取用户详情 */
  const getUserInfo = async () => {
    const { data } = await loginCacheApi()
    setUserInfo({
      userName: data.userLoginCacheBO.userName,
      avatar: data.userLoginCacheBO.userAvatar,
      userRoleId: data.userLinkLoginCacheBO.userRoles[0].id,
      roleName: data.userLinkLoginCacheBO.userRoles[0].roleName
    }, data.userLinkLoginCacheBO.userRoles)
  }

  /** 登出 */
  const logout = () => {
    removeToken()
    resetState()
    permissionStore.resetState()
    resetRouter()
    if (settingsStore.cacheTagsView) {
      tagsViewStore.delAllVisitedViews()
      tagsViewStore.delAllCachedViews()
    }
  }

  /** 更改角色 */
  const changeRole = async (id: number) => {
    const { data } = await changeUseRoleApi({id})
    if (data) {
      resetState()
      permissionStore.resetState()
      resetRouter()
      if (settingsStore.cacheTagsView) {
        tagsViewStore.delAllVisitedViews()
        tagsViewStore.delAllCachedViews()
      }
      // 刷新页面
      location.reload()
    }
  }

  return { token, userInfo, roleList, resetState, login, logout, getUserInfo, changeRole }
})

/** 在 setup 外使用 */
export function useUserStoreHook() {
  return useUserStore(store)
}
