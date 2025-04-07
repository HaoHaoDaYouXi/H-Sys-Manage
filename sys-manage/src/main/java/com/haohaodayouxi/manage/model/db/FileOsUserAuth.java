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
 * 文件系统用户权限表
 *
 * @author TONE
 * @date 2025/4/7
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "file_os_user_auth")
public class FileOsUserAuth implements Serializable {
    @Serial
    private static final long serialVersionUID = 6650844266604963058L;
    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 源ID 若要限制不能用 该值为0
     */
    @TableField(value = "os_id")
    private Long osId;

    /**
     * 对象类型
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
