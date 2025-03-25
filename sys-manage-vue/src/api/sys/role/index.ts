import { request } from "@/utils/service"
import { LabelValue, ListObjectBO } from "@/api/commonTypes"

const API_PREFIX = "s_role"

/** 查 */
export function getRoleTypeApi() {
  return request<ApiRes<LabelValue[]>>({
    url: `${API_PREFIX}/getRoleType`,
    method: "get"
  })
}
/** 查 */
export function pageListApi(data: any) {
  return request<ApiRes<any>>({
    url: `${API_PREFIX}/pageList`,
    method: "post",
    data
  })
}

export function addApi(data: any) {
  return request<ApiRes<boolean>>({
    url: `${API_PREFIX}/add`,
    method: "post",
    data
  })
}

export function detailApi(id: any) {
  return request<ApiRes<any>>({
    url: `${API_PREFIX}/detail/${id}`,
    method: "get"
  })
}

export function updApi(data: any) {
  return request<ApiRes<boolean>>({
    url: `${API_PREFIX}/upd`,
    method: "post",
    data
  })
}

export function batchDelApi(data: ListObjectBO) {
  return request<ApiRes<boolean>>({
    url: `${API_PREFIX}/batchDel`,
    method: "post",
    data
  })
}
