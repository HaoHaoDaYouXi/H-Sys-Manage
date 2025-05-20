export interface SMenuListReq {
  menuParentId: number
  menuType?: string
  menuName: string
  disabled?: number
}

export interface SMenuList {
  menuId: number
  menuParentId: number
  menuType: string
  menuTypeStr: string
  menuName: string
  menuIcon: string
  menuKey: string
  menuComponent: string
  showOrder: number
  disabled: number
  updateTime: string
  hasChildren: boolean
}

export type SMenuListRes = ApiRes<SMenuList[]>

export interface SMenuAddOrUpdReq {
  menuId: number
  menuParentId: number
  menuType: string
  menuName: string
  menuIcon: string
  menuKey: string
  activeMenu: string
  redirect: string
  menuComponent: string
  outUrl: number
  showOrder: number
  disabled: number
  hidden: number
  cachedView: number
  breadcrumb: number
  affix: number
  alwaysShow: number
  menuDescribe: string
}

export interface SMenuDeTail {
  menuId: number
  menuParentId: number
  menuType: string
  menuName: string
  menuIcon: string
  menuKey: string
  activeMenu: string
  redirect: string
  menuComponent: string
  outUrl: number
  showOrder: number
  disabled: number
  hidden: number
  cachedView: number
  breadcrumb: number
  affix: number
  alwaysShow: number
  menuDescribe: string
  createUid: number
  updateUid: number
  createTime: string
  updateTime: string
  version: number
  delStatus: number
}
