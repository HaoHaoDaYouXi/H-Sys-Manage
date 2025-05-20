package com.haohaodayouxi.manage.utils.file;

import com.alibaba.fastjson2.JSON;
import com.haohaodayouxi.common.util.algorithm.aes.AesUtil;
import com.haohaodayouxi.common.util.base.DateUtil;
import com.haohaodayouxi.common.util.business.IdUtil;
import com.haohaodayouxi.common.util.constants.StringConstant;
import com.haohaodayouxi.manage.model.bo.file.FileRequestSignBO;
import com.haohaodayouxi.manage.model.bo.file.FileUtilBO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * 文件路径工具类
 *
 * @author TONE
 * @date 2025/4/5
 */
@Slf4j
public class FilePathUtil {

    /**
     * 生成预览地址
     *
     * @param utilBO           文件工具类
     * @param filePath         文件路径
     * @param fileName         文件名
     * @param expire           过期时间 如果想永久访问，请将expire设置长点为100年
     * @param randomCode       随机码 为空默认使用当前时间戳10000求余
     * @param previewInterface 预览接口
     * @return 预览地址
     */
    public static String previewUrl(FileUtilBO utilBO, String filePath, String fileName, Long expire, String randomCode, String previewInterface) {
        try {
            if (StringUtils.isBlank(fileName)) {
                fileName = FilePathUtil.getFileNameByFilePath(filePath);
            }
            fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);
            long timeMillis = System.currentTimeMillis();
            FileRequestSignBO signRes = FileRequestSignBO.builder()
                    .fileRealPath(utilBO.getDomain() + filePath)
                    .token(IdUtil.getUUID())
                    .expire(timeMillis + expire)
                    .randomCode(ObjectUtils.isEmpty(randomCode) ? String.valueOf(timeMillis % 10000) : randomCode)
                    .build();
            String signStr = JSON.toJSON(signRes).toString();
            String encrypt = AesUtil.encryptECB(signStr);
            String sign = URLEncoder.encode(encrypt, StandardCharsets.UTF_8);
            String parma = String.format("s=%s&t=%s&c=%s", sign, signRes.getToken(), signRes.getRandomCode());
            return String.format("%s/%s?%s", previewInterface, fileName, parma);
        } catch (Exception e) {
            log.error("预览地址生成异常", e);
        }
        return "";
    }

    /**
     * 生成文件地址
     *
     * @param fileBasePath 文件基本路径
     * @param date         时间
     * @param fileFormat   文件格式
     * @return 文件地址 fileBasePath/2024/01-01/UUID.png
     */
    public static String generateFilePath(String fileBasePath, Date date, String fileCode, String fileFormat) {
        if (date == null) {
            date = new Date();
        }
        if (ObjectUtils.isEmpty(fileCode)) {
            fileCode = IdUtil.getUUID();
        }
        return getFilePath(fileBasePath, date, (fileCode + StringConstant.POINT + fileFormat));
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
