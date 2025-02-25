package com.haohaodayouxi.manage.constants;

import com.haohaodayouxi.common.util.constants.StringConstant;

/**
 * RedisConstants
 *
 * @author TONE
 * @date 2024/12/26
 */
public interface RedisConstants {

    /**
     * 登录限制
     */
    String LOGIN_LIMIT = SysConstants.PROJECT_CACHE_PREFIX + ":login:limit";

    /**
     * 获取登录限制账号前缀
     *
     * @param account 账号
     * @return redis key
     */
    static String getLoginLimitAccountKey(String account) {
        return LOGIN_LIMIT + StringConstant.COLON + account;
    }

    /**
     * 获取登录限制账号次数
     *
     * @param account 账号
     * @return redis key
     */
    static String getLoginLimitAccountCountKey(String account) {
        return getLoginLimitAccountKey(account) + ":count";
    }

    /**
     * 获取登录限制账号时间
     *
     * @param account 账号
     * @return redis key
     */
    static String getLoginLimitAccountTimeKey(String account) {
        return getLoginLimitAccountKey(account) + ":time";
    }

    /**
     * token前缀
     * 访问token 示例：xxx:token:token值
     * 使用中的token 示例：xxx:token:list:admin
     */
    String TOKEN = SysConstants.PROJECT_CACHE_PREFIX + ":token";

    /**
     * 访问token
     *
     * @param token token
     * @return redis key
     */
    static String getAccountTokenKey(String token) {
        return TOKEN + StringConstant.COLON + token;
    }

    /**
     * 使用中的token
     *
     * @param account 账号
     * @return redis key
     */
    static String getAccountTokenListKey(String account) {
        return TOKEN + ":list:" + account;
    }

    /**
     * 参数缓存前缀
     * 示例：xxx:param:1，xxx:param:1001
     */
    String PARAM_KEY = SysConstants.PROJECT_CACHE_PREFIX + ":param";

    static String getParamKey(Long paramCode) {
        return PARAM_KEY + StringConstant.COLON + paramCode;
    }

}
