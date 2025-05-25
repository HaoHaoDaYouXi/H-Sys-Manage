package com.haohaodayouxi.manage.constants;

import com.haohaodayouxi.common.util.constants.StringConstant;

/**
 * 文件路径常量
 */
public interface FilePathConstants {

    /**
     * 临时目录
     */
    String TMP_FOLDER_PATH = "tmp";

    /**
     * 临时目录
     */
    static String getTmpPath(String objCode) {
        return TMP_FOLDER_PATH + StringConstant.SLASH + objCode;
    }

    /**
     * 公共资源基础路径
     */
    String COM_RES_BASE_FILES_PATH = "common_res";

    /**
     * 业务基础路径
     */
    String BUS_BASE_FILES_PATH = "bus/" + SysConstants.PROJECT_NAME;

    /**
     * 普通上传基础路径
     */
    String UPLOAD_BASE_FILES_PATH = BUS_BASE_FILES_PATH + "/upload";

    /**
     * 获取上传文件路径
     */
    static String getUploadFilePath(String filePath) {
        return UPLOAD_BASE_FILES_PATH + StringConstant.SLASH + filePath;
    }

}
