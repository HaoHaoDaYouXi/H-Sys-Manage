package com.haohaodayouxi.manage.constants.enums.login;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 登录限制相关参数
 *
 * @author TONE
 * @date 2024/12/23
 */
@Getter
@AllArgsConstructor
public enum LoginLimitEnum {
    LOGIN_ERROR_NUM("21001", "登录错误次数", 6),
    LOGIN_ERROR_SHOW_CODE("21002", "登录错误显示验证码", 3),
    LOGIN_LOCK_TIME("21003", "登录锁定分钟", 30),
    ;
    private final String code;
    private final String name;
    private final Integer value;
}
