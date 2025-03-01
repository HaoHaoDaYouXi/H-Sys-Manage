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
 * SApi
 *
 * @author TONE
 * @date 2025/3/1
 */

/**
 * 接口表
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "s_api")
public class SApi implements Serializable {
    @Serial
    private static final long serialVersionUID = 7083336506974674004L;
    /**
     * 接口id
     */
    @TableId(value = "api_id", type = IdType.AUTO)
    private Long apiId;

    /**
     * 模块名称
     */
    @TableField(value = "module_name")
    private String moduleName;

    /**
     * 接口名称
     */
    @TableField(value = "api_name")
    private String apiName;

    /**
     * 接口类型 增=1 删=2 改=3 查=4
     */
    @TableField(value = "api_type")
    private Integer apiType;

    /**
     * 接口标识（可通过注解设置，也可以直接使用接口地址）
     */
    @TableField(value = "api_key")
    private String apiKey;

    /**
     * Url类型,0 普通 1 路径中有参数
     */
    @TableField(value = "url_type")
    private Integer urlType;

    /**
     * 请求类型
     */
    @TableField(value = "request_method")
    private String requestMethod;

    /**
     * 开放状态 0 未开放,需要菜单权限,1开放
     */
    @TableField(value = "open_status")
    private Integer openStatus;

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
