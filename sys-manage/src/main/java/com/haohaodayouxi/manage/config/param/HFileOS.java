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
    @Value("${h.file-os.sliceSize:5242880}")
    private Long sliceSize;
    /**
     * 文件下载任务最大数量 默认5个
     */
    @Value("${h.file-os.downloadTaskMaxSize:5}")
    private Integer downloadTaskMaxSize;

    /**
     * 视频封面宽度
     */
    @Value("${h.file-os.videoCoverWith:400}")
    private Integer videoCoverWith;

    /**
     * 视频封面格式
     */
    @Value("${h.file-os.videoCoverFormat:png}")
    private String videoCoverFormat;

    /**
     * 预览接口地址
     */
    @Value("${h.file-os.previewInterface:/manage/file/preview}")
    private String previewInterface;

    /**
     * 预览有效期 默认5分钟 单位ms
     */
    @Value("${h.file-os.previewExpire:300000}")
    private Long previewExpire;
}
