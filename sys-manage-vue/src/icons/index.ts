import { type App } from "vue"
import SvgIcon from "@/components/SvgIcon/index.vue"
import "virtual:svg-icons-register"

import icon from "@/icons/icon.json"
// element-ui icon数据来源：https://github.com/ElemeFE/element/blob/dev/examples/icon.json
import elIcon from "@/icons/elIcon.json"
import iconfont from "@/icons/iconfont.json"

export const IconJson = [
  {
    label: "Element Plus",
    name: "ep",
    value: icon,
    prefix: "",
  },
  {
    label: "Element UI",
    name: "eu",
    value: elIcon,
    prefix: "el-icon-",
  },
  {
    label: "IconFont",
    name: "if",
    value: iconfont,
    prefix: "iconfont ",
  },
]

export function loadSvg(app: App) {
  app.component("SvgIcon", SvgIcon)
}
