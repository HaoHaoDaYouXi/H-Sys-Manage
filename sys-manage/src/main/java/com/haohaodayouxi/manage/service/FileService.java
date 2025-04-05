package com.haohaodayouxi.manage.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * FileService
 *
 * @author TONE
 * @date 2025/4/4
 */
public interface FileService {

    /**
     * 文件上传
     *
     * @param userId 用户id
     * @param file   文件数据
     * @return 预览地址
     */
    String uploadFile(Long userId, MultipartFile file);
}
