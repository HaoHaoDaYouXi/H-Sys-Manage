package com.haohaodayouxi.manage.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.haohaodayouxi.common.core.enums.ErrorResponse;
import com.haohaodayouxi.common.core.enums.OkResponse;
import com.haohaodayouxi.common.core.exception.BusinessException;
import com.haohaodayouxi.common.core.model.res.Response;
import com.haohaodayouxi.common.redis.service.impl.CommonRedisServiceImpl;
import com.haohaodayouxi.common.util.algorithm.md5.Md5Util;
import com.haohaodayouxi.common.util.business.TokenUtil;
import com.haohaodayouxi.common.util.enums.TrueFalseEnum;
import com.haohaodayouxi.manage.constants.RedisConstants;
import com.haohaodayouxi.manage.constants.enums.login.LoginLimitEnum;
import com.haohaodayouxi.manage.model.bo.login.LoginCacheBO;
import com.haohaodayouxi.manage.model.bo.login.UserLinkLoginCacheBO;
import com.haohaodayouxi.manage.model.bo.login.UserLoginCacheBO;
import com.haohaodayouxi.manage.model.bo.user.UserRoleBO;
import com.haohaodayouxi.manage.model.db.SParam;
import com.haohaodayouxi.manage.model.db.SUser;
import com.haohaodayouxi.manage.model.req.login.AccountLoginReq;
import com.haohaodayouxi.manage.service.LoginService;
import com.haohaodayouxi.manage.service.MUserRoleService;
import com.haohaodayouxi.manage.service.SParamService;
import com.haohaodayouxi.manage.service.SUserService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
    @Resource
    private SParamService paramService;
    @Resource
    private CommonRedisServiceImpl<String> stringRedisServiceImpl;
    @Resource
    private CommonRedisServiceImpl<LoginCacheBO> loginRedisServiceImpl;

    @Override
    public Response<Object> accountLogin(AccountLoginReq req) {
        SUser user = userService.getOne(new LambdaQueryWrapper<SUser>().eq(SUser::getAccount, req.getAccount()));
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
     * @param bo 登录成功返回的缓存信息
     */
    private void loginSuccess(LoginCacheBO bo) {
        // 更新登录时间
        userService.update(new LambdaUpdateWrapper<SUser>().eq(SUser::getUserId, bo.getUserLoginCacheBO().getUserId()).set(SUser::getLastLoginTime, new Date()));
        // 是否是多人在线模式
        checkMultipleStatus(bo);
        // 设置登录缓存
        setLoginCache(bo);
        // 删除登录锁定缓存
        delLoginLockCache(bo.getUserLoginCacheBO().getAccount());
        // todo 进行消息通知 登录成功
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
        // todo 改成缓存拿 不查询数据库
        List<SParam> loginParam = paramService.list(new LambdaQueryWrapper<SParam>().in(SParam::getParamCode, Arrays.stream(LoginLimitEnum.values()).map(LoginLimitEnum::getCode).toList()));
        Map<Long, Integer> loginParamMap = loginParam.stream().collect(Collectors.toMap(SParam::getParamCode, v -> Integer.valueOf(v.getParamValue()), (v1, v2) -> v2));
        int loginErrorNum = loginParamMap.getOrDefault(LoginLimitEnum.LOGIN_ERROR_NUM.getCode(), LoginLimitEnum.LOGIN_ERROR_NUM.getValue());
        int loginLockTime = loginParamMap.getOrDefault(LoginLimitEnum.LOGIN_LOCK_TIME.getCode(), LoginLimitEnum.LOGIN_LOCK_TIME.getValue());
        String loginLimitCountKey = RedisConstants.getLoginLimitAccountCountKey(account);
        String loginLimitTimeKey = RedisConstants.getLoginLimitAccountTimeKey(account);
        String loginCountRedis = stringRedisServiceImpl.get(loginLimitCountKey, String.class);
        if (ObjectUtils.isNotEmpty(loginCountRedis)) {
            loginCount += Integer.parseInt(loginCountRedis);
            if (loginCount >= loginErrorNum) {
                stringRedisServiceImpl.set(loginLimitTimeKey, DateUtil.offsetMinute(new Date(), loginLockTime).toString());
                stringRedisServiceImpl.expire(loginLimitTimeKey, (long) loginLockTime, TimeUnit.MINUTES);
            }
        }
        stringRedisServiceImpl.set(loginLimitCountKey, Integer.toString(loginCount));
        stringRedisServiceImpl.expire(loginLimitCountKey, (long) loginLockTime, TimeUnit.MINUTES);
        return loginCount;
    }

    /**
     * 是否是多人使用状态
     *
     * @param bo bo
     */
    private void checkMultipleStatus(LoginCacheBO bo) {
        String tokenListRedisKey = RedisConstants.getAccountTokenListKey(bo.getUserLoginCacheBO().getAccount());
        if (Objects.equals(bo.getUserLoginCacheBO().getMultipleStatus(), TrueFalseEnum.TRUE.getCode())) {
            List<String> tokenList = stringRedisServiceImpl.listRange(tokenListRedisKey, 0L, -1L, String.class);
            if (ObjectUtils.isNotEmpty(tokenList)) {
                stringRedisServiceImpl.del(tokenList.stream().map(RedisConstants::getAccountTokenKey).toList().toArray(new String[]{}));
            }
            stringRedisServiceImpl.listDel(tokenListRedisKey);
        }
        stringRedisServiceImpl.listLeftPush(tokenListRedisKey, bo.getHToken());
        stringRedisServiceImpl.expire(tokenListRedisKey, Long.valueOf(LoginLimitEnum.LOGIN_TOKEN_TIME.getValue()), TimeUnit.MINUTES);
    }

    /**
     * 设置登录缓存
     *
     * @param bo bo
     */
    private void setLoginCache(LoginCacheBO bo) {
        String tokenRedisKey = RedisConstants.getAccountTokenKey(bo.getHToken());
        loginRedisServiceImpl.set(tokenRedisKey, bo);
        stringRedisServiceImpl.expire(tokenRedisKey, Long.valueOf(LoginLimitEnum.LOGIN_TOKEN_TIME.getValue()), TimeUnit.MINUTES);
    }

    /**
     * 删除登录锁定缓存
     *
     * @param account 账号
     */
    private void delLoginLockCache(String account) {
        stringRedisServiceImpl.delBySelectKeys(RedisConstants.getLoginLimitAccountKey(account) + "*");
    }
}
