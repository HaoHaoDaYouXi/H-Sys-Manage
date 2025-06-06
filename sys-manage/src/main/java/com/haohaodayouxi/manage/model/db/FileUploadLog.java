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
 * 文件上传记录表
 *
 * @author TONE
 * @date 2025/4/4
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "file_upload_log")
@JsonIgnoreProperties(value = {"createUid", "updateUid", "version", "delStatus"})
public class FileUploadLog implements Serializable {
    @Serial
    private static final long serialVersionUID = 2955916273737970923L;
    /**
     * ID
     */
    @TableId(value = "file_id", type = IdType.AUTO)
    private Long fileId;

    /**
     * 源ID
     */
    @TableField(value = "os_id")
    private Long osId;

    /**
     * 桶名 多个逗号分隔
     */
    @TableField(value = "bucket_name")
    private String bucketName;

    /**
     * 文件编码
     */
    @TableField(value = "file_code")
    private String fileCode;

    /**
     * 文件原名称，上传时获取到的文件名
     */
    @TableField(value = "file_name")
    private String fileName;

    /**
     * 文件加密信息(数据流加密存储)
     */
    @TableField(value = "encrypt_info")
    private String encryptInfo;

    /**
     * 文件真实大小(单位B，字节大小)
     */
    @TableField(value = "file_size")
    private Long fileSize;

    /**
     * 文件数据流大小(单位B，字节大小，业务层可能对数据流进行过操作大小不一致)
     */
    @TableField(value = "file_data_size")
    private Long fileDataSize;

    /**
     * 文件路径 完整路径不包含桶名 /xxxx/2025/04-02/a.png
     */
    @TableField(value = "file_path")
    private String filePath;

    /**
     * 文件缩放图，视频文件放封面
     */
    @TableField(value = "file_zoom_img")
    private String fileZoomImg;

    /**
     * 上传状态 0-上传中 1-成功 2-失败
     */
    @TableField(value = "upload_status")
    private Integer uploadStatus;

    /**
     * 错误信息
     */
    @TableField(value = "error_msg")
    private String errorMsg;

    /**
     * 对象类型 0-管理端用户 1-其他
     */
    @TableField(value = "obj_type")
    private Integer objType;

    /**
     * 对象ID
     */
    @TableField(value = "obj_id")
    private Long objId;

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
