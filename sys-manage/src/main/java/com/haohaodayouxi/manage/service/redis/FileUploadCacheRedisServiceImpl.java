package com.haohaodayouxi.manage.service.redis;

import com.alibaba.fastjson2.JSON;
import com.haohaodayouxi.common.redis.service.impl.CommonRedisServiceImpl;
import com.haohaodayouxi.common.util.constants.StringConstant;
import com.haohaodayouxi.file.core.model.res.FileCompleteMultipartUploadResult;
import com.haohaodayouxi.file.core.model.res.FileInitiateMultipartUploadResult;
import com.haohaodayouxi.file.core.model.res.FileUploadPartResult;
import com.haohaodayouxi.file.core.service.FileUploadCacheService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * FileUploadCacheRedisServiceImpl
 *
 * @author TONE
 * @date 2025/4/8
 */
@Primary
@Service
public class FileUploadCacheRedisServiceImpl implements FileUploadCacheService {

    @Resource
    private CommonRedisServiceImpl<String> stringRedisService;

    /**
     * 文件上传缓存key前缀
     */
    private static final String KEY_PREFIX = "file_upload_cache";
    /**
     * 分段上传缓存key前缀
     */
    private static final String MULTIPART_KEY_PREFIX = KEY_PREFIX + ":multipart";

    /**
     * 分段的key示例：
     * file_upload_cache:multipart:uploadId:init
     * file_upload_cache:multipart:uploadId:part
     * file_upload_cache:multipart:uploadId:complete
     * 这样删除key时统一删除file_upload_cache:multipart:uploadId
     *
     * @param key key
     * @return key
     */
    public String getMultipartKey(String key) {
        return MULTIPART_KEY_PREFIX + StringConstant.COLON + key;
    }

    public String getEncryptKey(String key) {
        return getMultipartKey(key) + ":encrypt";
    }

    public String getInitKey(String key) {
        return getMultipartKey(key) + ":init";
    }

    public String getPartKey(String key) {
        return getMultipartKey(key) + ":part";
    }

    public String getCompleteKey(String key) {
        return getMultipartKey(key) + ":complete";
    }

    public void setEncryptKeyCache(String key, String encryptKey) {
        stringRedisService.set(getEncryptKey(key), encryptKey);
    }

    public String getEncryptKeyCache(String key) {
        return stringRedisService.get(getEncryptKey(key), String.class);
    }

    @Override
    public void setMultipartResultCache(String key, FileInitiateMultipartUploadResult result) {
        stringRedisService.set(getInitKey(key), JSON.toJSONString(result));
    }

    @Override
    public FileInitiateMultipartUploadResult getMultipartResultCache(String key) {
        return JSON.parseObject(stringRedisService.get(getInitKey(key), String.class), FileInitiateMultipartUploadResult.class);
    }

    @Override
    public void addPartResultCache(String key, FileUploadPartResult result) {
        // 可以改成list结构存储
        if (ObjectUtils.isNotEmpty(result)) {
            String partKey = getPartKey(key);
            stringRedisService.listRightPush(partKey, JSON.toJSONString(result));
        }
    }

    @Override
    public List<FileUploadPartResult> getAllPartResultCacheByMultipartResult(String key) {
        List<String> v = stringRedisService.listRange(getPartKey(key), 0L, -1L, String.class);
        if (ObjectUtils.isNotEmpty(v)) {
            return v.stream().map(m -> JSON.parseObject(m, FileUploadPartResult.class)).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public void setCompleteResultCache(String key, FileCompleteMultipartUploadResult result) {
        stringRedisService.set(getCompleteKey(key), JSON.toJSONString(result));
    }

    @Override
    public FileCompleteMultipartUploadResult getCompleteResultCache(String key) {
        String s = stringRedisService.get(getCompleteKey(key), String.class);
        if (ObjectUtils.isNotEmpty(s)) {
            return JSON.parseObject(s, FileCompleteMultipartUploadResult.class);
        }
        return null;
    }

    @Override
    public void delAllMultipartUploadResultCache(String key) {
        stringRedisService.delBySelectKeys(getMultipartKey(key));
    }
}
