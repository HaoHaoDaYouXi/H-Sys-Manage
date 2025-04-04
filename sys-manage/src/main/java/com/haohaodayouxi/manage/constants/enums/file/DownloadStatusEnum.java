package com.haohaodayouxi.manage.constants.enums.file;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * DownloadStatusEnum
 *
 * @author TONE
 * @date 2025/4/4
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum DownloadStatusEnum {
    /**
     * 下载状态 0-待生成 1-生成中 2-可下载 3-不可下载
     */
    WAIT_GENERATION(0, "待生成"),
    GENERATING(1, "生成中"),
    DOWNLOADABLE(2, "可下载"),
    NOT_DOWNLOADABLE(3, "不可下载"),
    ;

    private Integer code;

    private String name;

    public static String getNameByCode(Integer code) {
        for (DownloadStatusEnum value : values()) {
            if (value.getCode().equals(code)) {
                return value.getName();
            }
        }
        return NOT_DOWNLOADABLE.getName();
    }
}
