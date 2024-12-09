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
 * 用户表
 *
 * @author TONE
 * @date 2024/12/8
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "s_user")
public class SUser implements Serializable {
    @Serial
    private static final long serialVersionUID = 3554138626820505409L;
    /**
     * ID
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;

    /**
     * 账号
     */
    @TableField(value = "account")
    private String account;

    /**
     * 密码
     */
    @TableField(value = "pwd")
    private String pwd;

    /**
     * 用户编码(UUID，可使用于密码等方面)
     */
    @TableField(value = "user_code")
    private String userCode;

    /**
     * 用户名称
     */
    @TableField(value = "user_name")
    private String userName;

    /**
     * 用户头像
     */
    @TableField(value = "user_avatar")
    private String userAvatar;

    /**
     * 用户联系方式
     */
    @TableField(value = "user_contact")
    private String userContact;

    /**
     * 备注
     */
    @TableField(value = "remarks")
    private String remarks;

    /**
     * 多人使用状态 0-否 1-是
     */
    @TableField(value = "multiple_status")
    private Integer multipleStatus;

    /**
     * 最近登录时间
     */
    @TableField(value = "last_login_time")
    private Date lastLoginTime;

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
