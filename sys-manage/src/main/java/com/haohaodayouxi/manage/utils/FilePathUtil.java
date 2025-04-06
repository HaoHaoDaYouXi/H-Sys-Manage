package com.haohaodayouxi.manage.utils;

import com.haohaodayouxi.common.util.base.DateUtil;
import com.haohaodayouxi.common.util.business.IdUtil;
import com.haohaodayouxi.common.util.constants.StringConstant;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * 文件路径工具类
 *
 * @author TONE
 * @date 2025/4/5
 */
public class FilePathUtil {

    /**
     * 生成文件地址
     *
     * @param fileBasePath 文件基本路径
     * @param date         时间
     * @param fileFormat   文件格式
     * @return 文件地址
     */
    public static String generateFilePath(String fileBasePath, Date date, String fileFormat) {
        if (ObjectUtils.isEmpty(date)) {
            date = new Date();
        }
        return getFilePath(fileBasePath, date, IdUtil.getUUID() + StringConstant.POINT + fileFormat);
    }

    /**
     * 根据日期获取 basePath/2024/01-01/fileName
     *
     * @param fileBasePath 文件基本路径
     * @param date         时间
     * @param fileName     文件名
     * @return 文件地址
     */
    public static String getFilePath(String fileBasePath, Date date, String fileName) {
        if (ObjectUtils.isEmpty(date)) {
            date = new Date();
        }
        return fileBasePath + StringConstant.SLASH + DateUtil.getNowYear(date) + StringConstant.SLASH + DateUtil.date2StringByMD(date) + StringConstant.SLASH + fileName;
    }

    /**
     * 根据文件路径获取路径
     *
     * @param filePath /2024/01-01/a.png
     * @return /2024/01-01/
     */
    public static String getPathByFilePath(String filePath) {
        return filePath.replace(StringUtils.substringAfterLast(filePath, StringConstant.SLASH), "");
    }

    /**
     * 根据文件路径获取文件名称
     *
     * @param filePath /2024/01-01/a.png
     * @return a.png
     */
    public static String getFileNameByFilePath(String filePath) {
        return StringUtils.substringAfterLast(filePath, StringConstant.SLASH);
    }

    /**
     * 获取文件格式
     *
     * @param fileName a.png
     * @return png
     */
    public static String getFileFormat(String fileName) {
        if (ObjectUtils.isEmpty(fileName) || !fileName.contains(StringConstant.POINT)) {
            return StringConstant.EMPTY;
        }
        return StringUtils.substringAfterLast(fileName, StringConstant.POINT).toLowerCase();
    }

}
