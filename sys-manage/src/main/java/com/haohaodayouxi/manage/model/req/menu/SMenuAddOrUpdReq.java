package com.haohaodayouxi.manage.model.req.menu;

import com.haohaodayouxi.common.core.interfaces.AddValid;
import com.haohaodayouxi.common.core.interfaces.UpdValid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * SMenuAddOrUpdReq
 *
 * @author TONE
 * @date 2025/3/6
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SMenuAddOrUpdReq implements Serializable {

    @Serial
    private static final long serialVersionUID = 6080407195683515908L;

    /**
     * ID
     */
    @NotNull(groups = UpdValid.class)
    private Long menuId;
    /**
     * 上级ID
     */
    @NotNull(groups = AddValid.class)
    private Long menuParentId;

    /**
     * 菜单类型(取配置表type=1) 默认1
     */
    @NotNull(groups = AddValid.class)
    private Integer menuType;

    /**
     * 菜单名称
     */
    @NotBlank(groups = AddValid.class)
    private String menuName;

    /**
     * 图标
     */
    @NotBlank(groups = AddValid.class)
    private String menuIcon;

    /**
     * 菜单标识(对应前端页面的path和按钮的标识)
     */
    @NotBlank(groups = AddValid.class)
    private String menuKey;

    /**
     * 选中菜单(填写高亮的path,resKey)
     */
    private String activeMenu;

    /**
     * 重定向地址
     */
    private String redirect;

    /**
     * 组件地址
     */
    @NotBlank(groups = AddValid.class)
    private String menuComponent;

    /**
     * 外部URL(是否外链) 0-不是 1-是 默认0
     */
    @NotNull(groups = AddValid.class)
    private Integer outUrl;

    /**
     * 顺序 默认1
     */
    @NotNull(groups = AddValid.class)
    private Integer showOrder;

    /**
     * 禁用 0-不禁用 1-禁用 默认0
     */
    @NotNull(groups = AddValid.class)
    private Integer disabled;

    /**
     * 隐藏 0-不隐藏 1-隐藏  默认0
     */
    @NotNull(groups = AddValid.class)
    private Integer hidden;

    /**
     * 是否缓存该路由页面  0-不缓存 1-缓存 默认0
     */
    @NotNull(groups = AddValid.class)
    private Integer cachedView;

    /**
     * 面包屑中显示 0-不显示 1-显示  默认1
     */
    @NotNull(groups = AddValid.class)
    private Integer breadcrumb;

    /**
     * 固定在tags-view 0-不固定 1-固定 默认0
     */
    @NotNull(groups = AddValid.class)
    private Integer affix;

    /**
     * 总是显示根目录  0-不显示 1-显示 默认1
     */
    @NotNull(groups = AddValid.class)
    private Integer alwaysShow;

    /**
     * 菜单描述
     */
    private String menuDescribe;
}
