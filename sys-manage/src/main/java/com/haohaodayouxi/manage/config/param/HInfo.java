package com.haohaodayouxi.manage.config.param;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * HInfo
 *
 * @author TONE
 * @date 2025/4/28
 */
@Data
public class HInfo implements Serializable {
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
