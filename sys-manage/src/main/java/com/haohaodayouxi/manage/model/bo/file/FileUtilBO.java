package com.haohaodayouxi.manage.model.bo.file;

import com.haohaodayouxi.file.core.service.FileUtilService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * FileUtilBO
 *
 * @author TONE
 * @date 2024/4/21 15:03
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileUtilBO implements Serializable {
    @Serial
    private static final long serialVersionUID = 7342657072656371471L;
    /**
     * 文件服务器ID
     */
    private Long osId;
    /**
     * 桶名 多桶实现也可以在这控制
     */
    private String bucketName;
    /**
     * 域名
     */
    private String domain;
    /**
     * 文件工具类
     */
    private FileUtilService fileUtilService;
}
