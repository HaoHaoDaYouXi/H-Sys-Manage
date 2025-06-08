package com.haohaodayouxi.manage.model.res.file;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 文件预览返回对象
 * 字段名简写 减少加密后长度
 * 转JSON后加密返回，前端再调用预览接口传过来
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FilePreviewRes implements Serializable {
    @Serial
    private static final long serialVersionUID = -6402870128500707772L;

    /**
     * 文件token
     */
    private String t;

    /**
     * 文件过期时间戳 毫秒时间戳 expire
     */
    private Long e;

    /**
     * 文件名称 fileName
     */
    private String n;
    /**
     * 域名前缀 domain
     */
    private String d;
    /**
     * 预览地址 prefixUrl
     */
    private String p;
    /**
     * 缩放文件地址 例如：视频封面图、图片缩略图 等源文件压缩后的文件
     * zoomFile
     */
    private String z;
}
