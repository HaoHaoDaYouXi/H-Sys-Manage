package com.haohaodayouxi.manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.haohaodayouxi.manage.constants.enums.file.FileObjTypeEnum;
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
     * 根据文件系统ID获取
     *
     * @param osId
     * @return
     */
    FileUtilBO getFileUtil(Long osId);

    /**
     * 根据对象类型和对象id获取文件工具类
     *
     * @param objTypeEnum 文件对象类型
     * @param objId       文件对象id
     * @return 文件工具类
     */
    FileUtilBO getFileUtil(FileObjTypeEnum objTypeEnum, Long objId);
}
