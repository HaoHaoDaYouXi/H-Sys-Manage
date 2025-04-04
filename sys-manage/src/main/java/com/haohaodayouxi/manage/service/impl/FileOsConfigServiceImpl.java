package com.haohaodayouxi.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haohaodayouxi.common.core.interfaces.InitService;
import com.haohaodayouxi.common.util.enums.TrueFalseEnum;
import com.haohaodayouxi.file.core.service.FileUtilService;
import com.haohaodayouxi.manage.mapper.FileOsConfigMapper;
import com.haohaodayouxi.manage.model.bo.file.FileUtilBO;
import com.haohaodayouxi.manage.model.db.FileOsConfig;
import com.haohaodayouxi.manage.service.FileOsConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.stereotype.Service;

/**
 * FileOsConfigServiceImpl
 *
 * @author TONE
 * @date 2025/4/2
 */
@Slf4j
@Service
public class FileOsConfigServiceImpl extends ServiceImpl<FileOsConfigMapper, FileOsConfig> implements FileOsConfigService, InitService {

    /**
     * 必须设置默认的库,默认master
     */
    String PRIMARY = "master";

    /**
     * 文件工具类
     */
    FileUtilBO fileUtilBO;

    @Override
    public void init() {
        fileOsConfig();
    }

    private void fileOsConfig() {
        FileOsConfig config = getOne(new LambdaQueryWrapper<FileOsConfig>().eq(FileOsConfig::getOsName, PRIMARY).eq(FileOsConfig::getDelStatus, TrueFalseEnum.FALSE.getCode()).last("limit 1"));
        if (ObjectUtils.isEmpty(config)) {
            throw new NoSuchBeanDefinitionException("没有默认的文件存储配置");
        }
        if (ObjectUtils.anyNull(config.getOsName(), config.getOsSourceType(), config.getEndPoint(), config.getAccessKeyId(), config.getAccessKeySecret(), config.getBucketName(), config.getDomain())) {
            log.error("文件存储配置信息存在空===={}", config);
            throw new NoSuchBeanDefinitionException("文件存储配置信息存在空");
        }
        if (config.getBucketName().contains(",")) {
            throw new NoSuchBeanDefinitionException("默认的文件存储配置 bucketName 只能有一个");
        }
        fileUtilBO = FileUtilBO.builder()
                .osId(config.getOsId())
                .bucketName(config.getBucketName())
                .domain(config.getDomain())
                .fileUtilService(FileUtilService.getFileUtilsParent(config.getOsSourceType(), config.getOsId().toString(), config.getAccessKeyId(), config.getAccessKeySecret(), config.getEndPoint()))
                .build();
    }

    @Override
    public FileUtilBO getFileUtil() {
        return fileUtilBO;
    }
}
