package com.haohaodayouxi.manage.model.bo.file;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * FileDataCheckBO
 *
 * @author TONE
 * @date 2025/4/9
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileDataCheckBO implements Serializable {
    @Serial
    private static final long serialVersionUID = 960605367939412363L;
    /**
     * 文件数据
     */
    private byte[] data;
    /**
     * 缩略图数据
     */
    private byte[] zoomData;
}
