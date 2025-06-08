package com.haohaodayouxi.manage.constants.enums.file;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * UploadStatusEnum
 *
 * @author TONE
 * @date 2025/4/1
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum UploadStatusEnum {
    /**
     * 上传状态 0-上传中 1-成功 2-失败
     */
    UPLOADING(0, "上传中"),
    SUCCESS(1, "成功"),
    FAIL(2, "失败"),
    ;

    private Integer code;
    private String name;
}
