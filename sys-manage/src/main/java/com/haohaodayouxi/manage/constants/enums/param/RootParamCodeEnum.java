package com.haohaodayouxi.manage.constants.enums.param;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 配置类 根编码 枚举
 *
 * @author TONE
 * @date 2024/12/23
 */
@Getter
@AllArgsConstructor
public enum RootParamCodeEnum {

    MENU_TYPE(1L, "菜单类型"),
    MENU_BUTTON_TYPE(2L, "菜单按钮类型"),
    ROLE_CODE(11L, "角色编码"),
    LOGIN_LIMIT(21L, "登录限制控制"),
    ;
    private final Long code;

    private final String name;


}
