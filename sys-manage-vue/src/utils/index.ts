/** 获取图标 */
export function getIconfont() {
  const iconFonts = document.querySelectorAll(".project-icon-list .block-icon-list .icon-code .icon-code-show")
  const res: string[] = []
  iconFonts.forEach((item) => {
    const val = (item as HTMLElement).innerText.trim()
    if (/^icon-/.test(val)) {
      res.push(val)
    }
  })
  return JSON.stringify(res)
}
