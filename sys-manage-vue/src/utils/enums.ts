export const TopId = 0

export const TrueFalseEnum = {
  FALSE: 0,
  TRUE: 1
}

export const DisabledList = [
  { value: TrueFalseEnum.FALSE, label: "启用" },
  { value: TrueFalseEnum.TRUE, label: "禁用" }
]
// 1-增 2-删 3-改 4-查
export const ApiTypeEnum = [
  { value: 1, label: "新增" },
  { value: 2, label: "删除" },
  { value: 3, label: "修改" },
  { value: 4, label: "查询" },
  { value: 5, label: "新增或修改" }
]

export const uploadTypeEnum = {
  IMG: {
    extension: ".png,.jpg,.jpeg,.gif,.dng",
    api: "/upload/img"
  },
  DOC: {
    extension: ".doc,.docx,.xls,.xlsx,.ppt,.pptx,.pdf,.txt,.zip",
    api: "/upload/doc"
  },
  get IMG_DOC() {
    return {
      extension: this.IMG.extension + "," + this.DOC.extension,
      api: "/upload/imgOrDoc"
    }
  },
  AUDIO: {
    extension: ".mp3,.wav,.wma,.aac",
    api: "/upload/audio"
  },
  VIDEO: {
    extension: ".mp4,.avi,.mpeg,.mov,.mkv",
    api: "/upload/video"
  }
}
