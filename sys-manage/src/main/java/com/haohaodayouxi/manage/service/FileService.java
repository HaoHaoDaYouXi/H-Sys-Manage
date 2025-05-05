package com.haohaodayouxi.manage.service;

import com.haohaodayouxi.manage.constants.enums.file.FileObjTypeEnum;
import com.haohaodayouxi.manage.constants.enums.file.FileTypeEnum;
import com.haohaodayouxi.manage.model.bo.file.FileInfoBO;
import com.haohaodayouxi.manage.model.req.file.FileUploadReq;
import jakarta.validation.Valid;

import java.util.Map;

/**
 * FileService
 *
 * @author TONE
 * @date 2025/4/4
 */
public interface FileService {

    /**
     * 文件上传
     *
     * @param req req
     * @return FileRes
     */
    String uploadFile(@Valid FileUploadReq req);

    /**
     * 基础 文件上传 不走前置和后置处理
     *
     * @param userId          用户ID
     * @param typeEnum        文件类型
     * @param fileName        文件名
     * @param fileIdeaDir     文件自定义目录
     * @param fileFormat      文件格式
     * @param bytes           数据流
     * @param serviceFileName 服务器地址
     * @param encryptKey      加密Key
     * @param userMetadata    自定义元数据
     * @return FileRes
     */
    FileInfoBO baseUploadFile(FileObjTypeEnum objTypeEnum,
                              Long objId,
                              Long userId,
                              FileTypeEnum typeEnum,
                              String fileName,
                              String fileIdeaDir,
                              String fileFormat,
                              byte[] bytes,
                              String serviceFileName,
                              String encryptKey,
                              Map<String, String> userMetadata);

    default FileInfoBO baseUploadFile(FileObjTypeEnum objTypeEnum,
                                      Long objId,
                                      Long userId,
                                      FileTypeEnum typeEnum,
                                      String fileName,
                                      String fileIdeaDir,
                                      String fileFormat,
                                      byte[] bytes) {
        return baseUploadFile(objTypeEnum, objId, userId, typeEnum, fileName, fileIdeaDir, fileFormat, bytes, null, null, null);
    }

    default FileInfoBO baseUploadFile(FileObjTypeEnum objTypeEnum,
                                      Long objId,
                                      Long userId,
                                      FileTypeEnum typeEnum,
                                      String fileName,
                                      String fileIdeaDir,
                                      String fileFormat,
                                      byte[] bytes,
                                      String encryptKey,
                                      Map<String, String> userMetadata) {
        return baseUploadFile(objTypeEnum, objId, userId, typeEnum, fileName, fileIdeaDir, fileFormat, bytes, null, encryptKey, userMetadata);
    }

    default FileInfoBO baseUploadFile(FileObjTypeEnum objTypeEnum,
                                      Long objId,
                                      Long userId,
                                      FileTypeEnum typeEnum,
                                      String fileName,
                                      String fileIdeaDir,
                                      String fileFormat,
                                      byte[] bytes,
                                      String encryptKey) {
        return baseUploadFile(objTypeEnum, objId, userId, typeEnum, fileName, fileIdeaDir, fileFormat, bytes, null, encryptKey, null);
    }
}
