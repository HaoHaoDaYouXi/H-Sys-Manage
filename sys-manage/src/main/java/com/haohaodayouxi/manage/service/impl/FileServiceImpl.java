package com.haohaodayouxi.manage.service.impl;

import com.haohaodayouxi.manage.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * FileServiceImpl
 *
 * @author TONE
 * @date 2025/4/4
 */
@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Override
    public String uploadFile(Long userId, MultipartFile file) {
        
        return "";
    }
}
