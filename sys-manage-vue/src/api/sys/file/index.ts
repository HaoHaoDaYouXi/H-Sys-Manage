import { request } from "@/utils/service"
import { commonApi } from "@/api/commonApi"

const API_PREFIX = "file"
const baseApi = commonApi(API_PREFIX)
export const pageListApi = baseApi.pageListApi
export const addApi = baseApi.addApi
export const detailApi = baseApi.detailApi
export const updApi = baseApi.updApi
export const batchDelApi = baseApi.batchDelApi

/** 根据编码获取文件预览地址 */
export function getPreviewUrlApi(fileCode: any) {
  return request<ApiRes<any>>({
    url: `${API_PREFIX}/getPreviewUrl/${fileCode}`,
    method: "get"
  })
}
/** 预览文件 */
export function previewApi(fileName: any) {
  return request<ApiRes<any>>({
    url: `${API_PREFIX}/preview/${fileName}`,
    method: "get"
  })
}
