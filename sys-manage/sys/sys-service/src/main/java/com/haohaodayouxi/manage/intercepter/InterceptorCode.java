package com.haohaodayouxi.manage.intercepter;

/**
 * 拦截器编码
 *
 * @author TONE
 * @date 2024/8/29
 */
public interface InterceptorCode {
    /**
     * 初始状态，非公开接口
     */
    Integer UN_OPEN = 0;
    /**
     * 公开接口（可以直接访问不需要鉴权）
     */
    Integer OPEN = 2;
    /**
     * token存在缓存内
     */
    Integer TOKEN = 4;
    /**
     * 用户访问的接口有权限
     */
    Integer API = 8;
    /**
     * 数据可全部访问，不需要处理
     */
    Integer DATA = 16;


}
