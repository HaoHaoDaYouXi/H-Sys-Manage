package com.haohaodayouxi.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haohaodayouxi.common.core.interfaces.InitService;
import com.haohaodayouxi.common.util.enums.TrueFalseEnum;
import com.haohaodayouxi.file.core.service.FileUtilService;
import com.haohaodayouxi.manage.constants.enums.file.FileObjTypeEnum;
import com.haohaodayouxi.manage.mapper.FileOsConfigMapper;
import com.haohaodayouxi.manage.model.bo.file.FileUtilBO;
import com.haohaodayouxi.manage.model.db.FileOsConfig;
import com.haohaodayouxi.manage.service.FileOsConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

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
     * 文件系统开关
     */
    @Value("${h.file-os.enable:false}")
    private boolean fileOsEnable;

    /**
     * 必须设置默认的库,默认master
     */
    String PRIMARY = "master";

    /**
     * 文件工具类缓存管理
     * key=osId
     */
    ConcurrentHashMap<Long, FileUtilBO> FILE_UTILS_MAP = new ConcurrentHashMap<>();
    /**
     * 用户权限缓存
     */
    ConcurrentHashMap<String, Long> FILE_USER_AUTH_MAP = new ConcurrentHashMap<>();

    @Override
    public void init() {
        if (fileOsEnable) {
            fileOsConfig();
        } else {
            log.info("文件系统未开启");
        }
    }

    private void fileOsConfig() {
        List<FileOsConfig> configs = list(new LambdaQueryWrapper<FileOsConfig>().eq(FileOsConfig::getDelStatus, TrueFalseEnum.FALSE.getCode()));
        if (ObjectUtils.isEmpty(configs)) {
            throw new NoSuchBeanDefinitionException("未配置文件存储");
        }
        if (configs.stream().noneMatch(c -> PRIMARY.equals(c.getOsName()))) {
            throw new NoSuchBeanDefinitionException("没有默认的文件存储配置");
        } else {
            if (configs.stream().anyMatch(c -> PRIMARY.equals(c.getOsName()) && c.getBucketName().contains(","))) {
                throw new NoSuchBeanDefinitionException("默认的文件存储配置 bucketName 只能有一个");
            }
        }
        configs.forEach(f -> {
            if (ObjectUtils.anyNull(f.getOsName(), f.getOsSourceType(), f.getEndPoint(), f.getAccessKeyId(), f.getAccessKeySecret(), f.getBucketName(), f.getDomain())) {
                log.error("文件存储配置信息存在空===={}", f);
                throw new NoSuchBeanDefinitionException("文件存储配置信息存在空");
            }
            if (PRIMARY.equals(f.getOsName())) {
                FILE_USER_AUTH_MAP.put(PRIMARY, f.getOsId());
            }
            FILE_UTILS_MAP.put(f.getOsId(),
                    FileUtilBO.builder()
                            .osId(f.getOsId())
                            .bucketName(f.getBucketName())
                            .domain(f.getDomain())
                            .fileUtilService(FileUtilService.getFileUtilsParent(f.getOsSourceType(), f.getOsId().toString(), f.getAccessKeyId(), f.getAccessKeySecret(), f.getEndPoint()))
                            .build());
        });
    }

    @Override
    public FileUtilBO getFileUtil(Long osId) {
        return FILE_UTILS_MAP.get(osId);
    }

    @Override
    public FileUtilBO getFileUtil(FileObjTypeEnum objTypeEnum, Long objId) {
        Long osId = FILE_USER_AUTH_MAP.get(objTypeEnum.getDirPrefix() + objId);
        if (ObjectUtils.isNotEmpty(osId)) {
            return FILE_UTILS_MAP.get(osId);
        }
        return FILE_UTILS_MAP.get(FILE_USER_AUTH_MAP.get(PRIMARY));
    }
}
