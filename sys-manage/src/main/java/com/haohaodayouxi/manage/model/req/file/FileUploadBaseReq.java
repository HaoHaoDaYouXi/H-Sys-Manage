package com.haohaodayouxi.manage.model.req.file;

import com.haohaodayouxi.manage.constants.enums.file.FileObjTypeEnum;
import com.haohaodayouxi.manage.constants.enums.file.FileTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

/**
 * 文件上传对象
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class FileUploadBaseReq implements Serializable {
    @Serial
    private static final long serialVersionUID = -813343012473872623L;
    /**
     * 文件格式
     */
    private String fileFormat;
    /**
     * 文件类型
     */
    private FileTypeEnum typeEnum;
    /**
     * 文件对象类型
     */
    private FileObjTypeEnum objTypeEnum;
    /**
     * 文件对象ID
     */
    private Long objId;
    /**
     * 用户ID
     */
    private Long userId;

}
