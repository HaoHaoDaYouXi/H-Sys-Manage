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
}
