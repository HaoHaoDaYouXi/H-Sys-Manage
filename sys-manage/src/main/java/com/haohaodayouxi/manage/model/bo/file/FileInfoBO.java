package com.haohaodayouxi.manage.model.bo.file;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 文件信息对象
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileInfoBO implements Serializable {
    @Serial
    private static final long serialVersionUID = 301501306588526684L;
    /**
     * 域名前缀
     */
    private String prefixUrl;
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 文件大小(字节大小)
     */
    private Long fileSize;
    /**
     * 服务器文件地址
     */
    private String serviceFileName;
    /**
     * 缩放文件地址 例如：视频封面图、图片缩略图 等源文件压缩后的文件
     */
    private String zoomFile;
    /**
     * 缩放文件大小(字节大小)
     */
    private Long zoomFileSize;
}
