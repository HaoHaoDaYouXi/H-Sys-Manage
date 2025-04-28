package com.haohaodayouxi.manage.config.param;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import java.io.Serial;
import java.io.Serializable;

/**
 * HFileOS
 *
 * @author TONE
 * @date 2025/4/28
 */
@Data
public class HFileOS implements Serializable {
    @Serial
    private static final long serialVersionUID = 5601258633564097375L;
    /**
     * 是否开启
     */
    private Boolean enable;

    /**
     * 文件切片大小 默认5M
     */
    @Value("${jw.file.custom.sliceSize:5242880}")
    private Long sliceSize;
    /**
     * 文件下载任务最大数量 默认5个
     */
    @Value("${jw.file.custom.downloadTaskMaxSize:5}")
    private Integer downloadTaskMaxSize;

    /**
     * 视频封面宽度
     */
    @Value("${jw.file.custom.videoCoverWith:400}")
    private Integer videoCoverWith;

    /**
     * 视频封面格式
     */
    @Value("${jw.file.custom.videoCoverFormat:png}")
    private String videoCoverFormat;
}
