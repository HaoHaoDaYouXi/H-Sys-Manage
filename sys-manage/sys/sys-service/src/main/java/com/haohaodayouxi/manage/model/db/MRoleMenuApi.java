package com.haohaodayouxi.manage.model.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色菜单关联表
 *
 * @author TONE
 * @date 2025/7/13
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "m_role_menu_api")
public class MRoleMenuApi implements Serializable {
    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 角色ID
     */
    @TableField(value = "role_id")
    private Long roleId;

    /**
     * 菜单接口关联ID(m_menu_api)
     */
    @TableField(value = "menu_api_id")
    private Long menuApiId;

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
     * 删除状态（0，正常，1已删除）
     */
    @TableField(value = "del_status")
    private Integer delStatus;

    private static final long serialVersionUID = 1L;
}
