import { request } from "@/utils/service"
import { commonApi } from "@/api/commonApi"

const API_PREFIX = "file"
const baseApi = commonApi(API_PREFIX)
export const pageListApi = baseApi.pageListApi
export const addApi = baseApi.addApi
export const detailApi = baseApi.detailApi
export const updApi = baseApi.updApi
export const batchDelApi = baseApi.batchDelApi
