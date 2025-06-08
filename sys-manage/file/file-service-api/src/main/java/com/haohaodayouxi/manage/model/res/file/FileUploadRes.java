package com.haohaodayouxi.manage.model.res.file;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 文件上传返回对象
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileUploadRes implements Serializable {
    @Serial
    private static final long serialVersionUID = 1633990845803880459L;
    /**
     * 文件编码
     */
    private String fileCode;
    /**
     * 预览地址
     */
    private String previewUrl;
}
