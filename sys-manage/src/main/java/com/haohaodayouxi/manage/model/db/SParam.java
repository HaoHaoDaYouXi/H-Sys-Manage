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
 * 系统参数表
 *
 * @author TONE
 * @date 2024/12/8
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "s_param")
@JsonIgnoreProperties(value = {"createUid", "updateUid", "version", "delStatus"})
public class SParam implements Serializable {
    @Serial
    private static final long serialVersionUID = 2252165608264894195L;
    /**
     * 编码（1001，每3位代表一级，非自增，由开发人员控制)
     */
    @TableId(value = "param_code", type = IdType.AUTO)
    private Long paramCode;

    /**
     * 参数分类(上级编码，0-第一级)
     */
    @TableField(value = "param_parent_code")
    private Long paramParentCode;

    /**
     * 参数名称
     */
    @TableField(value = "param_name")
    private String paramName;

    /**
     * 参数值
     */
    @TableField(value = "param_value")
    private String paramValue;

    /**
     * 参数备注
     */
    @TableField(value = "param_remark")
    private String paramRemark;

    /**
     * 排序code
     */
    @TableField(value = "param_sort_code")
    private Integer paramSortCode;

    /**
     * 版本
     */
    @TableField(value = "version")
    private Integer version;

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
     * 删除状态：0-正常；1-删除
     */
    @TableField(value = "del_status")
    private Integer delStatus;
}
