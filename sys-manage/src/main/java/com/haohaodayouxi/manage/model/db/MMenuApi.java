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
 * MMenuApi
 *
 * @author TONE
 * @date 2025/3/1
 */

/**
 * 菜单接口关系表
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "m_menu_api")
public class MMenuApi implements Serializable {
    @Serial
    private static final long serialVersionUID = 2046399217020387878L;
    /**
     * ID
     */
    @TableId(value = "menu_api_id", type = IdType.AUTO)
    private Long menuApiId;

    /**
     * 菜单ID
     */
    @TableField(value = "menu_id")
    private Long menuId;

    /**
     * 接口ID
     */
    @TableField(value = "api_id")
    private Long apiId;

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
