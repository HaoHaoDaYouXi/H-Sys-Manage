import { request } from "@/utils/service"
import {ListObjectBO} from "@/api/commonTypes";

const API_PREFIX = "s_param"

/** 获取所有参数 */
export function getAllParamApi() {
  return request<ApiRes<any>>({
    url: `${API_PREFIX}/getAllParam`,
    method: "get"
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
