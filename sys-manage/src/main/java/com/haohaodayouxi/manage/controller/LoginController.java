package com.haohaodayouxi.manage.controller;

import com.haohaodayouxi.common.core.annotation.OpenApi;
import com.haohaodayouxi.common.core.annotation.TokenApi;
import com.haohaodayouxi.common.core.enums.OkResponse;
import com.haohaodayouxi.common.core.model.res.Response;
import com.haohaodayouxi.manage.model.req.login.AccountLoginReq;
import com.haohaodayouxi.manage.service.LoginService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录接口 开放不需要token
 *
 * @author TONE
 * @date 2024/12/9
 */
@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController {
    @Resource
    private LoginService loginService;


    /**
     * 账号密码登录
     *
     * @param req req
     * @return res
     */
    @OpenApi
    @TokenApi
    @PostMapping("/account")
    public Response<Object> accountLogin(@RequestBody @Validated AccountLoginReq req) {
        return OkResponse.LOGIN.toResponse(loginService.accountLogin(req));
    }

}
