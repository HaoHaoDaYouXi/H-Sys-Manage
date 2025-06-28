/** 通用标准 API 接口 */
import { request } from "@/utils/service"
import { ListObjectBO } from "@/api/commonTypes"

export function commonApi<Prefix extends string>(API_PREFIX: Prefix) {
  return {
    addApi: <T = boolean>(data: any) =>
      request<ApiRes<T>>({
        url: `${API_PREFIX}/add`,
        method: "post",
        data
      }),

    detailApi: <T = any>(id: any) =>
      request<ApiRes<T>>({
        url: `${API_PREFIX}/detail/${id}`,
        method: "get"
      }),

    updApi: <T = boolean>(data: any) =>
      request<ApiRes<T>>({
        url: `${API_PREFIX}/upd`,
        method: "post",
        data
      }),

    batchDelApi: <T = boolean>(data: ListObjectBO) =>
      request<ApiRes<T>>({
        url: `${API_PREFIX}/batchDel`,
        method: "post",
        data
      })
  }
}
