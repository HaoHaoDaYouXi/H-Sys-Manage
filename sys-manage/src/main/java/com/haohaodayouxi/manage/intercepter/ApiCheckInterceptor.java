package com.haohaodayouxi.manage.intercepter;

import com.haohaodayouxi.common.core.annotation.PermissionApi;
import com.haohaodayouxi.common.core.constants.CurrentParam;
import com.haohaodayouxi.common.core.constants.InterceptorCode;
import com.haohaodayouxi.manage.config.SysAuthProperties;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Api访问权限拦截器
 *
 * @author TONE
 * @date 2024/8/29
 **/
@Slf4j
@Component
public class ApiCheckInterceptor implements HandlerInterceptor {

    @Resource
    private SysAuthProperties sysAuthProperties;

    /**
     * 用户访问的接口判断、检查用户的角色和菜单的关系
     *
     * @param request  request
     * @param response response
     * @param handler  handler
     * @return true-继续访问 false-返回结果
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.debug("ApiCheckInterceptor status={}", CurrentParam.get(CurrentParam.AUTH_STATUS_KEY));
        // 1010 authStatus=10  公开接口，token无效，用户可访问
        // 1100 authStatus=12  非公开接口，token有效，用户可访问
        // 1110 authStatus=14  公开接口，token有效，用户可访问
        Integer authStatus = (Integer) CurrentParam.get(CurrentParam.AUTH_STATUS_KEY);
        // 10&12=8   12&12=12   14&12=12
        if ((authStatus & InterceptorCode.USER_TOKEN) != InterceptorCode.USER_TOKEN) {
            // 检查用户的角色和菜单的关系
            String key;
            PermissionApi permissionApi = (PermissionApi) CurrentParam.get(CurrentParam.PERMISSION_API_KEY);
            if (ObjectUtils.isEmpty(permissionApi)) {
                // 若没有注解定义，则使用请求路径
                key = request.getRequestURI();
            } else {
                key = permissionApi.value();
            }
            if (!CurrentParam.has(CurrentParam.WHITE_API_KEY) && sysAuthProperties.getWhiteApis().stream().anyMatch(key::contains)) {
                // todo 获取api权限缓存 判断是否可访问
                // if (不可访问) {
                //     log.error("Api不可访问");
                //     InterceptorErrorResponse.responseErrorByCode(response, InterceptorCode.API);
                //     return false;
                // }
            }
        }
        CurrentParam.put(CurrentParam.AUTH_STATUS_KEY, authStatus | InterceptorCode.API);
        // 11010 authStatus=26   公开接口，token无效，用户可访问，接口可以访问
        // 11100 authStatus=28  非公开接口，token有效，用户可访问，接口可以访问
        // 11110 authStatus=30  公开接口，token有效，用户可访问，接口可以访问
        log.debug("ApiCheckInterceptor status={}", CurrentParam.get(CurrentParam.AUTH_STATUS_KEY));
        return true;
    }
}
