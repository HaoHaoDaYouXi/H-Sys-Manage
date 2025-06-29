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
    LOGIN_ERROR_NUM(10002100001L, "登录错误次数", 6),
    LOGIN_ERROR_SHOW_CODE(10002100002L, "登录错误显示验证码", 3),
    LOGIN_LOCK_TIME(10002100003L, "登录锁定时间(单位：分钟)", 30),
    LOGIN_TOKEN_TIME(10002100004L, "登录有效期(单位：分钟)", 60 * 8),
    ;
    private final Long code;
    private final String name;
    private final Integer value;
}
