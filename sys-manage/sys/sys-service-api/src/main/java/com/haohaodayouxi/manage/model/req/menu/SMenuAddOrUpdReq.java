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
    @NotNull(groups = UpdValid.class, message = "id不能为空")
    private Long menuId;
    /**
     * 上级ID
     */
    @NotNull(groups = {AddValid.class, UpdValid.class}, message = "上级id不能为空")
    private Long menuParentId;

    /**
     * 菜单类型(取配置表type=1) 默认1
     */
    @NotNull(groups = {AddValid.class, UpdValid.class}, message = "菜单类型不能为空")
    private Integer menuType;

    /**
     * 菜单名称
     */
    @NotBlank(groups = {AddValid.class, UpdValid.class}, message = "菜单名称不能为空")
    private String menuName;

    /**
     * 图标
     */
    @NotBlank(groups = {AddValid.class, UpdValid.class}, message = "图标不能为空")
    private String menuIcon;

    /**
     * 菜单标识(对应前端页面的path和按钮的标识)
     */
    @NotBlank(groups = {AddValid.class, UpdValid.class}, message = "菜单标识不能为空")
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
    @NotBlank(groups = {AddValid.class, UpdValid.class}, message = "组件地址不能为空")
    private String menuComponent;

    /**
     * 外部URL(是否外链) 0-不是 1-是 默认0
     */
    @NotNull(groups = {AddValid.class, UpdValid.class}, message = "外部URL不能为空")
    private Integer outUrl;

    /**
     * 顺序 默认1
     */
    @NotNull(groups = {AddValid.class, UpdValid.class}, message = "顺序不能为空")
    private Integer showOrder;

    /**
     * 禁用 0-不禁用 1-禁用 默认0
     */
    @NotNull(groups = {AddValid.class, UpdValid.class}, message = "禁用不能为空")
    private Integer disabled;

    /**
     * 隐藏 0-不隐藏 1-隐藏  默认0
     */
    @NotNull(groups = {AddValid.class, UpdValid.class}, message = "隐藏不能为空")
    private Integer hidden;

    /**
     * 是否缓存该路由页面  0-不缓存 1-缓存 默认0
     */
    @NotNull(groups = {AddValid.class, UpdValid.class}, message = "是否缓存该路由页面不能为空")
    private Integer cachedView;

    /**
     * 面包屑中显示 0-不显示 1-显示  默认1
     */
    @NotNull(groups = {AddValid.class, UpdValid.class}, message = "面包屑中显示不能为空")
    private Integer breadcrumb;

    /**
     * 固定在tags-view 0-不固定 1-固定 默认0
     */
    @NotNull(groups = {AddValid.class, UpdValid.class}, message = "固定在tags-view不能为空")
    private Integer affix;

    /**
     * 总是显示根目录  0-不显示 1-显示 默认1
     */
    @NotNull(groups = {AddValid.class, UpdValid.class}, message = "总是显示根目录不能为空")
    private Integer alwaysShow;

    /**
     * 菜单描述
     */
    private String menuDescribe;
}
