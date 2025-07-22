// 下拉框
const selectValidator = (rule: any, value: any, callback: any) => {
  if (!value || (Array.isArray(value) && value.length < 1)) {
    return callback("请选择")
  }
  callback()
}
const selectValidator_0 = (rule: any, value: any, callback: any) => {
  if ((!value && value !== 0) || (Array.isArray(value) && value.length < 1)) {
    return callback("请选择")
  }
  callback()
}

/** 公共表单校验规则 */
const commonFormRules = {
  required: [{ required: true, min: 1, message: "必填", trigger: "blur" }],
  select: [{ required: true, validator: selectValidator, trigger: "change" }],
  select_0: [{ required: true, validator: selectValidator_0, trigger: "change" }]
}

export default commonFormRules
