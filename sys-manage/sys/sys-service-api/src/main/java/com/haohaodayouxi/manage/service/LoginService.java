package com.haohaodayouxi.manage.service;

import com.haohaodayouxi.common.core.model.res.Response;
import com.haohaodayouxi.manage.model.bo.login.LoginCacheBO;
import com.haohaodayouxi.manage.model.req.login.AccountLoginReq;

/**
 * LoginService
 *
 * @author TONE
 * @date 2024/12/9
 */
public interface LoginService {
    /**
     * 账号密码登录
     *
     * @param req req
     * @return res
     */
    Response<Object> accountLogin(AccountLoginReq req);

    /**
     * 获取登录缓存
     *
     * @param token
     * @return
     */
    LoginCacheBO getLoginCacheByToken(String token);
}
