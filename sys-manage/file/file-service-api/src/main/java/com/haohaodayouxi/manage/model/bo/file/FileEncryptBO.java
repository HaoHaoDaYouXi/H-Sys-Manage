package com.haohaodayouxi.manage.model.bo.file;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * FileEncryptBO
 *
 * @author TONE
 * @date 2025/5/5
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileEncryptBO implements Serializable {
    @Serial
    private static final long serialVersionUID = 2939286935670104740L;
    /**
     * 文件加密Key
     */
    private String encryptKey;
    /**
     * 段的加密信息 若不是分段加密的 此数据可以为空
     */
    private List<FilePartEncryptBO> partEncryptBOList;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FilePartEncryptBO implements Serializable {
        @Serial
        private static final long serialVersionUID = -1540832376888984433L;
        /**
         * 段编号
         */
        private int partNumber;
        /**
         * 段加密后的大小，下载文件时使用
         */
        private Long dataSize;
    }
}
