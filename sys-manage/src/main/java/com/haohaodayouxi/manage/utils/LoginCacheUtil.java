package com.haohaodayouxi.manage.utils;

import com.haohaodayouxi.common.redis.service.impl.CommonRedisServiceImpl;
import com.haohaodayouxi.manage.constants.RedisConstants;
import com.haohaodayouxi.manage.constants.enums.login.LoginLimitEnum;
import com.haohaodayouxi.manage.model.bo.login.LoginCacheBO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * LoginCacheUtil
 *
 * @author TONE
 * @date 2025/2/24
 */
@Slf4j
@Component
public class LoginCacheUtil {
    @Resource
    private CommonRedisServiceImpl<LoginCacheBO> loginRedisServiceImpl;

    /**
     * 设置登录缓存
     *
     * @param bo bo
     */
    public LoginCacheBO getLoginCache(LoginCacheBO bo) {
        String tokenRedisKey = RedisConstants.getAccountTokenKey(bo.getHToken());
        return loginRedisServiceImpl.get(tokenRedisKey, LoginCacheBO.class);
    }

    /**
     * 设置登录缓存
     *
     * @param bo bo
     */
    public void setLoginCache(LoginCacheBO bo) {
        String tokenRedisKey = RedisConstants.getAccountTokenKey(bo.getHToken());
        loginRedisServiceImpl.set(tokenRedisKey, bo, Long.valueOf(LoginLimitEnum.LOGIN_TOKEN_TIME.getValue()), TimeUnit.MINUTES);
    }
}
