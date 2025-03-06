package com.haohaodayouxi.manage.constants.enums.menu;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * MenuTypeEnum
 *
 * @author TONE
 * @date 2025/1/1
 */
@Getter
@AllArgsConstructor
public enum MenuTypeEnum {

    FOLDER(1001L, "目录", 1),
    PAGE(1002L, "页面", 2),
    BUTTON(1003L, "按钮", 3),
    ;
    private final Long code;

    private final String name;

    private final Integer value;

    public static MenuTypeEnum getByCode(Long code) {
        for (MenuTypeEnum menuTypeEnum : MenuTypeEnum.values()) {
            if (menuTypeEnum.getCode().equals(code)) {
                return menuTypeEnum;
            }
        }
        return FOLDER;
    }

    public static MenuTypeEnum getByValue(Integer value) {
        for (MenuTypeEnum menuTypeEnum : MenuTypeEnum.values()) {
            if (menuTypeEnum.getValue().equals(value)) {
                return menuTypeEnum;
            }
        }
        return FOLDER;
    }
}
