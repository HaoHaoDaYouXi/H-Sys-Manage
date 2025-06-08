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
 * 文件系统配置表
 *
 * @author TONE
 * @date 2025/4/2
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "file_os_config")
@JsonIgnoreProperties(value = {"createUid", "updateUid", "version", "delStatus"})
public class FileOsConfig implements Serializable {
    @Serial
    private static final long serialVersionUID = -1246103441548957881L;
    /**
     * ID
     */
    @TableId(value = "os_id", type = IdType.AUTO)
    private Long osId;

    /**
     * 文件系统名称
     */
    @TableField(value = "os_name")
    private String osName;

    /**
     * 文件系统源类型，如：LOCAL=本地存储、HW=华为云、ALI=阿里云、MINIO=MinIO，具体支持源由底层工具控制
     */
    @TableField(value = "os_source_type")
    private String osSourceType;

    /**
     * 节点地址
     */
    @TableField(value = "end_point")
    private String endPoint;

    /**
     * 账号
     */
    @TableField(value = "access_key_id")
    private String accessKeyId;

    /**
     * 密钥
     */
    @TableField(value = "access_key_secret")
    private String accessKeySecret;

    /**
     * 桶名 多个逗号分隔
     */
    @TableField(value = "bucket_name")
    private String bucketName;

    /**
     * 代理域名 多个逗号分隔
     */
    @TableField(value = "`domain`")
    private String domain;

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
