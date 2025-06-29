package com.haohaodayouxi.manage.constants;

import com.haohaodayouxi.common.util.constants.StringConstant;

/**
 * SysConstants
 *
 * @author TONE
 * @date 2024/12/26
 */
public interface SysConstants {
    /**
     * 最顶级ID
     * 目前使用场景：菜单顶级
     */
    Long TOP_LEVEL_ID = 0L;
    /**
     * 最顶级系统用户ID
     * 目前使用场景：默认系统用户时可赋值
     */
    Long TOP_SYS_USER_ID = 1L;

    /**
     * 参数ID长度
     */
    Integer PARAM_ID_LENGTH = 5;

    /**
     * 系统名称
     */
    String PROJECT_NAME = System.getProperty("spring.application.name", "manage");

    /**
     * 系统端口
     */
    String PROJECT_PORT = System.getProperty("server.port", "10000");

    /**
     * 项目缓存前缀
     */
    String PROJECT_CACHE_PREFIX = PROJECT_NAME + StringConstant.COLON + PROJECT_PORT;

}
