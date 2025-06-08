package com.haohaodayouxi.manage.utils.login;

import com.haohaodayouxi.common.redis.service.impl.CommonRedisServiceImpl;
import com.haohaodayouxi.manage.constants.RedisConstants;
import com.haohaodayouxi.manage.constants.enums.login.LoginLimitEnum;
import com.haohaodayouxi.manage.model.bo.login.LoginCacheBO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
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
     * 获取登录缓存
     *
     * @param token token
     */
    public LoginCacheBO getLoginCache(String token) {
        String tokenRedisKey = RedisConstants.getAccountTokenKey(token);
        LoginCacheBO bo = loginRedisServiceImpl.get(tokenRedisKey, LoginCacheBO.class);
        if (ObjectUtils.isNotEmpty(bo)) {
            // 刷新过期时间
            loginRedisServiceImpl.expire(tokenRedisKey, Long.valueOf(LoginLimitEnum.LOGIN_TOKEN_TIME.getValue()), TimeUnit.MINUTES);
        }
        return bo;
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
