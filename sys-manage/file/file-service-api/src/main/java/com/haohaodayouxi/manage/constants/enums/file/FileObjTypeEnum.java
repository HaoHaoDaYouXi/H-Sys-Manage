package com.haohaodayouxi.manage.constants.enums.file;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * FileObjTypeEnum
 * 控制用户类型，后续扩展
 *
 * @author TONE
 * @date 2024/4/23 10:25
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum FileObjTypeEnum {
    MANAGE(0, "管理端用户", "MANAGE_"),
    OTHER(1, "其他", "OTHER_"),
    ;
    /**
     * 类型
     */
    private Integer type;

    /**
     * 名称
     */
    private String name;
    /**
     * 目录前缀
     */
    private String dirPrefix;

    public static FileObjTypeEnum getByType(Integer type) {
        for (FileObjTypeEnum typeEnum : values()) {
            if (typeEnum.getType().equals(type)) {
                return typeEnum;
            }
        }
        return OTHER;
    }
}
