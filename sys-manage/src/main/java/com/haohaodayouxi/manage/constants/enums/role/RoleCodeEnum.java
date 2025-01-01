package com.haohaodayouxi.manage.constants.enums.role;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * RoleCodeEnum
 *
 * @author TONE
 * @date 2025/1/1
 */
@Getter
@AllArgsConstructor
public enum RoleCodeEnum {

    SYS_ADMIN(11001L, "系统管理员"),
    REGULAR_USER(11002L, "普通用户"),
    ;
    private final Long code;

    private final String name;
}
