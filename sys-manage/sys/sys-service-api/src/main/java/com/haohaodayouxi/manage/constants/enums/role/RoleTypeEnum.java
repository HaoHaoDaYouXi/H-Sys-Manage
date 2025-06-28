package com.haohaodayouxi.manage.constants.enums.role;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * RoleTypeEnum
 *
 * @author TONE
 * @date 2025/1/1
 */
@Getter
@AllArgsConstructor
public enum RoleTypeEnum {

    SYS_ADMIN("1100001", "系统管理员"),
    REGULAR_USER("1100002", "普通用户"),
    ;
    private final String code;

    private final String name;

    public static RoleTypeEnum getByCode(String code) {
        for (RoleTypeEnum typeEnum : RoleTypeEnum.values()) {
            if (typeEnum.getCode().equals(code)) {
                return typeEnum;
            }
        }
        return REGULAR_USER;
    }
}
