package com.haohaodayouxi.manage.model.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 菜单表
 *
 * @author TONE
 * @date 2024/12/8
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "s_menu")
public class SMenu implements Serializable {
    @Serial
    private static final long serialVersionUID = 4546698355826283739L;
    /**
     * ID
     */
    @TableId(value = "menu_id", type = IdType.AUTO)
    private Long menuId;

    /**
     * 菜单父级ID
     */
    @TableField(value = "menu_parent_id")
    private Long menuParentId;

    /**
     * 菜单类型(取配置表type=1) 默认1
     */
    @TableField(value = "menu_type")
    private Integer menuType;

    /**
     * 菜单名称
     */
    @TableField(value = "menu_name")
    private String menuName;

    /**
     * 图标
     */
    @TableField(value = "menu_icon")
    private String menuIcon;

    /**
     * 菜单标识(对应前端页面的path和按钮的标识)
     */
    @TableField(value = "menu_key")
    private String menuKey;

    /**
     * 选中菜单(填写高亮的path,resKey)
     */
    @TableField(value = "active_menu")
    private String activeMenu;

    /**
     * 重定向地址
     */
    @TableField(value = "redirect")
    private String redirect;

    /**
     * 组件地址
     */
    @TableField(value = "menu_component")
    private String menuComponent;

    /**
     * 外部URL(是否外链) 0-不是 1-是 默认0
     */
    @TableField(value = "out_url")
    private Integer outUrl;

    /**
     * 顺序 默认1
     */
    @TableField(value = "show_order")
    private Integer showOrder;

    /**
     * 禁用 0-不禁用 1-禁用 默认0
     */
    @TableField(value = "disabled")
    private Integer disabled;

    /**
     * 隐藏 0-不隐藏 1-隐藏  默认0
     */
    @TableField(value = "hidden")
    private Integer hidden;

    /**
     * 是否缓存该路由页面  0-不缓存 1-缓存 默认0
     */
    @TableField(value = "cached_view")
    private Integer cachedView;

    /**
     * 面包屑中显示 0-不显示 1-显示  默认1
     */
    @TableField(value = "breadcrumb")
    private Integer breadcrumb;

    /**
     * 固定在tags-view 0-不固定 1-固定 默认0
     */
    @TableField(value = "affix")
    private Integer affix;

    /**
     * 总是显示根目录  0-不显示 1-显示 默认1
     */
    @TableField(value = "always_show")
    private Integer alwaysShow;

    /**
     * 菜单描述
     */
    @TableField(value = "menu_describe")
    private String menuDescribe;

    /**
     * 创建人
     */
    @TableField(value = "create_uid")
    private Long createUid;

    /**
     * 修改人
     */
    @TableField(value = "update_uid")
    private Long updateUid;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(value = "update_time")
    private Date updateTime;

    /**
     * 版本
     */
    @TableField(value = "version")
    private Long version;

    /**
     * 删除状态：0-正常；1-删除 默认0
     */
    @TableField(value = "del_status")
    private Integer delStatus;
}
