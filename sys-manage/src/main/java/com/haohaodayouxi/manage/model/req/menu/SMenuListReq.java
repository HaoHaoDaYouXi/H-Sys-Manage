package com.haohaodayouxi.manage.model.req.menu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * SMenuListRes
 *
 * @author TONE
 * @date 2025/3/5
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SMenuListReq implements Serializable {
    @Serial
    private static final long serialVersionUID = -4828253061179843056L;

    /**
     * 上级ID
     */
    private Long menuParentId;

    /**
     * 菜单类型(取配置表type=1) 默认1
     */
    private Integer menuType;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 禁用 0-不禁用 1-禁用 默认0
     */
    private Integer disabled;
}
