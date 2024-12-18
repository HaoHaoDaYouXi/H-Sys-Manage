package com.haohaodayouxi.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.haohaodayouxi.common.core.exception.BusinessException;
import com.haohaodayouxi.common.util.algorithm.md5.Md5Util;
import com.haohaodayouxi.common.util.enums.TrueFalseEnum;
import com.haohaodayouxi.manage.model.bo.login.LoginCacheBO;
import com.haohaodayouxi.manage.model.db.SUser;
import com.haohaodayouxi.manage.model.req.login.AccountLoginReq;
import com.haohaodayouxi.manage.service.LoginService;
import com.haohaodayouxi.manage.service.SUserService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

/**
 * LoginServiceImpl
 *
 * @author TONE
 * @date 2024/12/9
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private SUserService userService;
    @Resource
    private MUserRoleService userRoleService;

    @Override
    public Response<Object> accountLogin(AccountLoginReq req) {
        SUser user = userService.getOne(new LambdaQueryWrapper<SUser>().eq(SUser::getAccount, req.getAccount()).eq(SUser::getDelStatus, TrueFalseEnum.FALSE.getCode()));
        if (ObjectUtils.isEmpty(user) || (!Md5Util.encryptPWD(req.getPwd(), user.getUserCode()).equals(user.getPwd()))) {
            return ErrorResponse.LOGIN_ERROR.toResponse("账号名或密码错误，请检查并重试", loginError(req.getAccount()));
        }
        return OkResponse.LOGIN.toResponse(baseLogin(user));
    }
}
