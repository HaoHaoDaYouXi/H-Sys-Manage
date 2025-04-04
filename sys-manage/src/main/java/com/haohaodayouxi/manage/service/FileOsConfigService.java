package com.haohaodayouxi.manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.haohaodayouxi.manage.model.bo.file.FileUtilBO;
import com.haohaodayouxi.manage.model.db.FileOsConfig;

/**
 * FileOsConfigService
 *
 * @author TONE
 * @date 2025/4/2
 */
public interface FileOsConfigService extends IService<FileOsConfig> {
    /**
     * 获取文件工具类
     *
     * @return 文件工具类
     */
    FileUtilBO getFileUtil();
}
