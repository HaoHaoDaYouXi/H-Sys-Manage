package com.haohaodayouxi.manage.model.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户角色关联表
 *
 * @author TONE
 * @date 2024/12/8
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "m_user_role")
@JsonIgnoreProperties(value = {"createUid", "updateUid", "version", "delStatus"})
public class MUserRole implements Serializable {
    @Serial
    private static final long serialVersionUID = 4201549815994994654L;
    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 角色ID
     */
    @TableField(value = "role_id")
    private Long roleId;

    /**
     * 当前使用状态 0-非使用 1-使用 默认0
     */
    @TableField(value = "use_status")
    private Integer useStatus;

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
}
