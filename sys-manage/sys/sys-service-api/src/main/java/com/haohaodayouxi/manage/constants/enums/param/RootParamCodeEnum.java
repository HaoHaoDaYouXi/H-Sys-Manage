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

    SYS_PARAM(1L, "系统参数"),
    MENU_TYPE(100001L, "菜单类型"),
    MENU_BUTTON_TYPE(100002L, "菜单按钮类型"),
    ROLE_TYPE(100011L, "角色类型"),
    LOGIN_LIMIT(100021L, "登录限制控制"),
    ;
    private final Long code;

    private final String name;


}
