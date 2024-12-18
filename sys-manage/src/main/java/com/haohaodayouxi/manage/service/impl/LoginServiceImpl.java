package com.haohaodayouxi.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.haohaodayouxi.common.core.enums.ErrorResponse;
import com.haohaodayouxi.common.core.enums.OkResponse;
import com.haohaodayouxi.common.core.exception.BusinessException;
import com.haohaodayouxi.common.core.model.res.Response;
import com.haohaodayouxi.common.util.algorithm.md5.Md5Util;
import com.haohaodayouxi.common.util.business.TokenUtil;
import com.haohaodayouxi.common.util.enums.TrueFalseEnum;
import com.haohaodayouxi.manage.model.bo.login.LoginCacheBO;
import com.haohaodayouxi.manage.model.bo.login.UserLinkLoginCacheBO;
import com.haohaodayouxi.manage.model.bo.login.UserLoginCacheBO;
import com.haohaodayouxi.manage.model.bo.user.UserRoleBO;
import com.haohaodayouxi.manage.model.db.SUser;
import com.haohaodayouxi.manage.model.req.login.AccountLoginReq;
import com.haohaodayouxi.manage.service.LoginService;
import com.haohaodayouxi.manage.service.MUserRoleService;
import com.haohaodayouxi.manage.service.SUserService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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

    /**
     * 基础登录方法 判断账号是否可以使用，角色是否可以使用等账号使用权限问题
     *
     * @param user
     * @return
     */
    private LoginCacheBO baseLogin(SUser user) {
        // 根据登录用户信息，生成token，并获取用户关联信息(角色身份等)
        // 用户是否可多人使用判断是否清除其余token缓存
        List<UserRoleBO> userRoleBOS = userRoleService.selectUserRoleList(user.getUserId());
        if (ObjectUtils.isEmpty(userRoleBOS)) {
            throw new BusinessException("用户无可使用角色，请联系管理员");
        }
        String token = TokenUtil.generateToken(user.getUserId());
        LoginCacheBO cacheBO = LoginCacheBO.builder()
                .hToken(token)
                .userLoginCacheBO(UserLoginCacheBO.builder()
                        .userId(user.getUserId())
                        .account(user.getAccount())
                        .userCode(user.getUserCode())
                        .userName(user.getUserName())
                        .userAvatar(user.getUserAvatar())
                        .userContact(user.getUserContact())
                        .remarks(user.getRemarks()).build())
                .userLinkLoginCacheBO(UserLinkLoginCacheBO.builder().userRoles(userRoleBOS).build())
                .build();
        loginSuccess(cacheBO);
        return cacheBO;

    }

    /**
     * 登录成功 账号信息一切正常都可以使用表示登录成功，进行缓存设置和登录时间变更等登录成功的操作
     *
     * @param loginCacheBO 登录成功返回的缓存信息
     */
    private void loginSuccess(LoginCacheBO loginCacheBO) {
        // 更新登录时间
        userService.update(new LambdaUpdateWrapper<SUser>().eq(SUser::getUserId, loginCacheBO.getUserLoginCacheBO().getUserId()).set(SUser::getLastLoginTime, new Date()));
    }

    /**
     * 登录失败 进行账号限制等操作
     *
     * @param account 账号
     * @return 错误次数
     */
    private Integer loginError(String account) {
        // 登录错误达到错误次数，进行锁定
        int loginCount = 1;
        return loginCount;
    }
}
