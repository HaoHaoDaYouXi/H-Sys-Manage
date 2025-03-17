// element-ui icon数据来源：https://github.com/ElemeFE/element/blob/dev/examples/icon.json
import elIcon from '@/icons/elIcon.json'
import iconfont from '@/icons/iconfont.json'

export const TopId = 0

export const TrueFalseEnum = {
  FALSE: 0,
  TRUE: 1
}

export const DisabledList = [
  { value: TrueFalseEnum.FALSE, label: '启用' },
  { value: TrueFalseEnum.TRUE, label: '禁用' }
]

export const select = {
  // 图标
  iconsList: elIcon.map((name: string) => `el-icon-${name}`).concat(iconfont.map((name: string) => `iconfont ${name}`)),
}
