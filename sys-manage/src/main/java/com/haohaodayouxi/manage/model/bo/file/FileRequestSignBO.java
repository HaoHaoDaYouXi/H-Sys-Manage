package com.haohaodayouxi.manage.model.bo.file;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 文件访问 sign 签名
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileRequestSignBO implements Serializable {
    @Serial
    private static final long serialVersionUID = 749174877951439444L;
    /**
     * 文件完整地址
     */
    private String fileRealPath;

    /**
     * 文件token
     */
    private String token;

    /**
     * 文件过期时间戳
     */
    private Long expire;

}
