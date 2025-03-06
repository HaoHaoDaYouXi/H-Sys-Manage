package com.haohaodayouxi.manage.model.res.menu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

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
public class SMenuListRes implements Serializable {
    @Serial
    private static final long serialVersionUID = 2265239520245564207L;


    private Long menuId;
    private Long menuParentId;

    /**
     * 菜单类型(取配置表type=1) 默认1
     */
    private Integer menuType;
    private String menuTypeStr;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 图标
     */
    private String menuIcon;

    /**
     * 菜单标识(对应前端页面的path和按钮的标识)
     */
    private String menuKey;

    /**
     * 组件地址
     */
    private String menuComponent;

    /**
     * 禁用 0-不禁用 1-禁用 默认0
     */
    private Integer disabled;

    /**
     * 修改时间
     */
    private Date updateTime;

}
