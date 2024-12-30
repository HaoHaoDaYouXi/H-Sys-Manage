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
     * 访问token示例：xxx:token:admin
     * 访问token_list示例：xxx:token:admin:list
     */
    String TOKEN = SysConstants.PROJECT_CACHE_PREFIX + ":token";

    /**
     * 账号token
     *
     * @param account 账号
     * @return redis key
     */
    static String getAccountTokenKey(String account) {
        return TOKEN + StringConstant.COLON + account;
    }

    /**
     * 账号使用的每个token
     *
     * @param account 账号
     * @return redis key
     */
    static String getAccountTokenListKey(String account) {
        return getAccountTokenKey(account) + ":list";
    }

}
