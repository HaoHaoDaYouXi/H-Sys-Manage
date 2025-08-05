import { type Directive } from "vue"
import { usePermissionStoreHook } from "@/store/modules/permission"

/**
 * 权限判断
 */
export const permission: Directive = {
  mounted(el, binding) {
    const { value } = binding
    const permissionStore = usePermissionStoreHook()
    const hasPermission = permissionStore.currentRoutesData?.meta.btnKeys?.includes(value)
    if (!hasPermission) {
      if (el.parentNode) {
        el.parentNode.removeChild(el)
      } else {
        el.innerHTML = ""
      }
    } else {
      el && el.setAttribute("permission-code", value)
    }
  }
}
