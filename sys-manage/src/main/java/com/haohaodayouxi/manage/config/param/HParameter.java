package com.haohaodayouxi.manage.config.param;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.Serial;
import java.io.Serializable;

/**
 * HParamData
 *
 * @author TONE
 * @date 2024/12/29
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "h")
public class HParameter implements Serializable {
    @Serial
    private static final long serialVersionUID = 4803336943919173761L;
    /**
     * 系统信息
     */
    private Info info;

    @Data
    static class Info implements Serializable {
        @Serial
        private static final long serialVersionUID = -5669195804411418747L;
        /**
         * 系统名称
         */
        private String name;
        /**
         * 系统版本
         */
        private String version;
        /**
         * 基础包
         */
        private String basePackage;
        /**
         * 父包
         */
        private String parentPackage;
    }
}
