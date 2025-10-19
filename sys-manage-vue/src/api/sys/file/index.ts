import { request } from "@/utils/service"
import { commonApi } from "@/api/commonApi"

export const API_PREFIX = "file"
const baseApi = commonApi(API_PREFIX)

/** 根据编码获取文件预览地址 */
export function getPreviewUrlApi(fileCode: any) {
  return request<ApiRes<any>>({
    url: `${API_PREFIX}/getPreviewUrl/${fileCode}`,
    method: "get"
  })
}
/** 预览文件 */
function detailApi(fileCode: any) {
  return request<any>({
    url: `${API_PREFIX}/detail/${fileCode}`,
    method: "get",
    responseType: "blob"
  })
}
/** 预览文件 */
export async function getDetailApiUrl(fileCode: any) {
  // 将 Blob 转换为 Object URL
  return URL.createObjectURL(await detailApi(fileCode))
}
/** 预览文件 */
export function previewApi(fileName: any) {
  return request<ApiRes<any>>({
    url: `${API_PREFIX}/preview/${fileName}`,
    method: "get"
  })
}
/** 上传接口 */
export function uploadApi(uploadUrl: string) {
  return request<ApiRes<any>>({
    url: `${API_PREFIX}/${uploadUrl}`,
    method: "get"
  })
}
