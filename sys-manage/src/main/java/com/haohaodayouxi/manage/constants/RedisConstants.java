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
     * 账号token示例：xxx:token:admin 方便统一处理
     * 访问token示例：xxx:token:admin:token值
     */
    String TOKEN = SysConstants.PROJECT_CACHE_PREFIX + ":token";

    /**
     * 账号token
     *
     * @param account 账号
     * @return redis key
     */
    static String getTokenAccountKey(String account) {
        return TOKEN + StringConstant.COLON + account;
    }

    /**
     * 账号token
     *
     * @param account 账号
     * @return redis key
     */
    static String getAccountTokenKey(String account, String token) {
        return getTokenAccountKey(account) + StringConstant.COLON + token;
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
