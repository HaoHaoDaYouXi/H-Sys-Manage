package com.haohaodayouxi.manage.config;

import com.haohaodayouxi.common.util.business.SpringContextHolder;
import com.haohaodayouxi.manage.intercepter.ApiCheckInterceptor;
import com.haohaodayouxi.manage.intercepter.InitParamInterceptor;
import com.haohaodayouxi.manage.intercepter.PathCheckInterceptor;
import com.haohaodayouxi.manage.intercepter.TokenCheckInterceptor;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebAuthConfig
 *
 * @author TONE
 * @date 2024/8/29
 */
@Slf4j
@Order(-1)
@Configuration
public class WebAuthConfig implements WebMvcConfigurer {

    @Resource
    private SysAuthProperties sysAuthProperties;

    /**
     * 拦截器注册
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(SpringContextHolder.getBean(InitParamInterceptor.class)).addPathPatterns("/**");
        registry.addInterceptor(SpringContextHolder.getBean(PathCheckInterceptor.class)).addPathPatterns("/**");
        registry.addInterceptor(SpringContextHolder.getBean(TokenCheckInterceptor.class)).addPathPatterns("/**");
        if (!sysAuthProperties.isSkipApiCheck()) {
            registry.addInterceptor(SpringContextHolder.getBean(ApiCheckInterceptor.class)).addPathPatterns("/**");
        }
    }
}
