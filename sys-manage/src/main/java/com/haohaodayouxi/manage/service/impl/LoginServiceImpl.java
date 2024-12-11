package com.haohaodayouxi.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.haohaodayouxi.common.core.exception.BusinessException;
import com.haohaodayouxi.common.core.model.res.Response;
import com.haohaodayouxi.common.util.algorithm.md5.Md5Util;
import com.haohaodayouxi.common.util.enums.TrueFalseEnum;
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

    @Override
    public Response<Object> accountLogin(AccountLoginReq req) {
        SUser user = userService.getOne(new LambdaQueryWrapper<SUser>().eq(SUser::getAccount, req.getAccount()).eq(SUser::getDelStatus, TrueFalseEnum.FALSE.getCode()));
        if (ObjectUtils.isEmpty(user)) {
            throw new BusinessException("账号名或密码错误，请检查并重试");
        }
        if (!Md5Util.encryptPWD(req.getPwd()).equals(user.getPwd())) {
            throw new BusinessException("账号名或密码错误，请检查并重试");
        }


        return null;
    }
}
