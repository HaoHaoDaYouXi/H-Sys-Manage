package com.haohaodayouxi.manage.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.haohaodayouxi.common.core.exception.BusinessException;
import com.haohaodayouxi.common.util.algorithm.aes.AesUtil;
import com.haohaodayouxi.common.util.business.IdUtil;
import com.haohaodayouxi.common.util.enums.TrueFalseEnum;
import com.haohaodayouxi.file.core.service.FileUploadCoreService;
import com.haohaodayouxi.manage.config.param.HParameter;
import com.haohaodayouxi.manage.constants.FilePathConstants;
import com.haohaodayouxi.manage.constants.enums.file.FileObjTypeEnum;
import com.haohaodayouxi.manage.constants.enums.file.FileTypeEnum;
import com.haohaodayouxi.manage.constants.enums.file.UploadStatusEnum;
import com.haohaodayouxi.manage.model.bo.file.*;
import com.haohaodayouxi.manage.model.db.FileUploadLog;
import com.haohaodayouxi.manage.model.req.file.FilePreviewReq;
import com.haohaodayouxi.manage.model.req.file.FileUploadBaseReq;
import com.haohaodayouxi.manage.model.req.file.FileUploadReq;
import com.haohaodayouxi.manage.model.res.file.FileUploadRes;
import com.haohaodayouxi.manage.service.FileDataCheckService;
import com.haohaodayouxi.manage.service.FileOsConfigService;
import com.haohaodayouxi.manage.service.FileService;
import com.haohaodayouxi.manage.service.FileUploadLogService;
import com.haohaodayouxi.manage.utils.file.FfmpegUtil;
import com.haohaodayouxi.manage.utils.file.FilePathUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.net.URI;
import java.util.*;

/**
 * FileServiceImpl
 *
 * @author TONE
 * @date 2025/4/4
 */
@Slf4j
@Service
public class FileServiceImpl implements FileService {
    @Resource
    private FileUploadLogService fileUploadLogService;
    @Resource
    private FileOsConfigService fileOsConfigService;
    @Resource
    private FileDataCheckService fileDataCheckService;
    @Resource
    private HParameter hParameter;
    @Resource
    private FileUploadCoreService fileUploadCoreService;

    private static final String ENCRYPT_KEY = "encryptKey";

    @Override
    public void prefixFileByFileCode(HttpServletRequest request, HttpServletResponse response, String fileCode) {
        try {
            FileUploadLog uploadLog = fileUploadLogService.getOne(new LambdaQueryWrapper<FileUploadLog>().eq(FileUploadLog::getDelStatus, TrueFalseEnum.FALSE.getCode()).eq(FileUploadLog::getFileCode, fileCode));
            if (ObjectUtils.isNotEmpty(uploadLog)) {
                FileUtilBO utilBO = fileOsConfigService.getFileUtil(uploadLog.getOsId());
                if (ObjectUtils.isNotEmpty(utilBO)) {
                    requestForwarding(request, response, (utilBO.getDomain() + uploadLog.getFilePath()));
                } else {
                    response.setStatus(404);
                    response.getWriter().print("404 FILE OS IS NOT FOUND");
                }
            } else {
                // todo 404
                response.setStatus(404);
                response.getWriter().print("404 FILE IS NOT FOUND");
            }
        } catch (Exception e) {
            log.error("response error code 500", e);
            response.setStatus(500);
            try {
                response.getWriter().print("500");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public String getPreviewUrlByFileCode(String fileCode) {
        FileUploadLog uploadLog = fileUploadLogService.getOne(new LambdaQueryWrapper<FileUploadLog>().eq(FileUploadLog::getDelStatus, TrueFalseEnum.FALSE.getCode()).eq(FileUploadLog::getFileCode, fileCode));
        if (ObjectUtils.isEmpty(uploadLog)) {
            throw new BusinessException("数据访问错误，请稍后重试");
        }
        FileUtilBO utilBO = fileOsConfigService.getFileUtil(uploadLog.getOsId());
        if (ObjectUtils.isEmpty(utilBO)) {
            throw new BusinessException("数据访问错误，请稍后重试");
        }
        return FilePathUtil.previewUrl(utilBO, FilePathConstants.getUploadFilePath(uploadLog.getFilePath()), uploadLog.getFileName(), hParameter.getFileOS().getPreviewExpire(), null, hParameter.getFileOS().getPreviewInterface());
    }

    @Override
    public void previewFile(HttpServletRequest request, HttpServletResponse response, FilePreviewReq req) {
        try {
            if (ObjectUtils.allNotNull(req.getS(), req.getT(), req.getC())) {
                String decrypt = AesUtil.decryptECB(req.getS());
                if (StringUtils.isNotBlank(decrypt)) {
                    FileRequestSignBO signBO = JSON.parseObject(decrypt, FileRequestSignBO.class);
                    long timeMillis = System.currentTimeMillis();
                    if (Objects.nonNull(signBO) && signBO.getToken().equals(req.getT()) && signBO.getRandomCode().equals(req.getC()) && timeMillis <= signBO.getExpire()) {
                        requestForwarding(request, response, signBO.getFileRealPath());
                    } else {
                        throw new BusinessException("数据访问错误，请稍后重试");
                    }
                } else {
                    throw new BusinessException("数据访问错误，请稍后重试");
                }
            } else {
                throw new BusinessException("数据访问错误，请稍后重试");
            }
        } catch (Exception e) {
            log.error("数据访问错误，请稍后重试", e);
            throw new BusinessException("数据访问错误，请稍后重试");
        }
    }

    /**
     * 请求转发
     *
     * @param request
     * @param response
     * @param url
     * @throws Exception
     */
    private void requestForwarding(HttpServletRequest request, HttpServletResponse response, String url) throws Exception {
        URI uri = new URI(url);
        // 执行代理查询
        String methodName = request.getMethod();
        HttpMethod httpMethod = HttpMethod.valueOf(methodName);
        ClientHttpRequest delegate = new SimpleClientHttpRequestFactory().createRequest(uri, httpMethod);
        Enumeration<String> headerNames = request.getHeaderNames();
        // 设置请求头
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            Enumeration<String> v = request.getHeaders(headerName);
            List<String> arr = new ArrayList<>();
            while (v.hasMoreElements()) {
                arr.add(v.nextElement());
            }
            delegate.getHeaders().addAll(headerName, arr);
        }
        StreamUtils.copy(request.getInputStream(), delegate.getBody());
        // 执行远程调用
        ClientHttpResponse clientHttpResponse = delegate.execute();
        response.setStatus(clientHttpResponse.getStatusCode().value());
        // 设置响应头
        clientHttpResponse.getHeaders().forEach((key, value) -> value.forEach(it -> response.setHeader(key, it)));
        StreamUtils.copy(clientHttpResponse.getBody(), response.getOutputStream());
    }

    @Override
    public FileUploadRes uploadFile(@Valid FileUploadReq req) {
        checkUploadFileReq(req);
        try {
            FileInfoBO bo = baseUploadFile(req.getObjTypeEnum(), req.getObjId(), req.getUserId(), req.getTypeEnum(), req.getFile().getOriginalFilename(), FilePathConstants.UPLOAD_BASE_FILES_PATH, req.getFileFormat(), req.getFile().getBytes(), req.getEncryptKey(), req.getUserMetadata());
            // bo转换成预览地址
            String previewUrl = FilePathUtil.previewUrl(fileOsConfigService.getFileUtil(req.getObjTypeEnum(), req.getObjId()), FilePathConstants.getUploadFilePath(bo.getFilePath()), bo.getFileName(), hParameter.getFileOS().getPreviewExpire(), null, hParameter.getFileOS().getPreviewInterface());
            return FileUploadRes.builder().fileCode(bo.getFileCode()).previewUrl(previewUrl).build();
        } catch (IOException e) {
            throw new BusinessException("文件上传异常", e);
        }
    }

    /**
     * 校验数据
     */
    private void checkUploadFileReq(FileUploadReq req) {
        checkBaseReq(req);
        // 文件校验
        if (ObjectUtils.isEmpty(req.getFile()) || req.getFile().getSize() <= 0 || !Objects.requireNonNull(req.getFile().getOriginalFilename()).contains(".")) {
            throw new BusinessException("请提供正确的文件");
        }
        String fileFormat = FilePathUtil.getFileFormat(req.getFile().getOriginalFilename());
        FileTypeEnum fileFormatType = FileTypeEnum.getFileType(req.getTypeEnum(), fileFormat);
        req.setFileFormat(fileFormat);
        req.setTypeEnum(fileFormatType);
    }

    /**
     * 基础校验
     *
     * @param req
     */
    public void checkBaseReq(FileUploadBaseReq req) {
        if (ObjectUtils.anyNull(req.getObjTypeEnum(), req.getObjId())) {
            throw new IllegalArgumentException("对象信息不能为空");
        }
    }

    @Override
    public FileInfoBO baseUploadFile(FileObjTypeEnum objTypeEnum,
                                     Long objId,
                                     Long userId,
                                     FileTypeEnum typeEnum,
                                     String fileName,
                                     String fileIdeaDir,
                                     String fileFormat,
                                     byte[] bytes,
                                     String filePath,
                                     String encryptKey,
                                     Map<String, String> userMetadata) {
        FileUtilBO utilBO = fileOsConfigService.getFileUtil(objTypeEnum, objId);
        if (ObjectUtils.isEmpty(utilBO)) {
            throw new BusinessException("当前用户不能上传文件");
        }
        FileDataCheckBO checkBO = fileDataCheck(typeEnum, bytes);
        Date now = new Date();
        Long fileSize = (long) bytes.length;
        if (ObjectUtils.isEmpty(fileIdeaDir)) {
            fileIdeaDir = FilePathConstants.UPLOAD_BASE_FILES_PATH;
        }
        FileUploadLog uploadLog;
        String fileCode = IdUtil.getUUID();
        if (ObjectUtils.isEmpty(filePath)) {
            filePath = FilePathUtil.generateFilePath(fileIdeaDir, now, fileCode, fileFormat);
            uploadLog = addFileUploadLog(utilBO.getOsId(), utilBO.getBucketName(), fileCode, fileName, encryptKey, fileSize, filePath, now, objTypeEnum, objId, userId);
        } else {
            filePath = fileIdeaDir + filePath;
            uploadLog = fileUploadLogService.getOne(new LambdaQueryWrapper<FileUploadLog>().eq(FileUploadLog::getFilePath, filePath).eq(FileUploadLog::getDelStatus, TrueFalseEnum.FALSE.getCode()).last(" limit 1"));
            if (ObjectUtils.isEmpty(uploadLog)) {
                uploadLog = addFileUploadLog(utilBO.getOsId(), utilBO.getBucketName(), fileCode, fileName, encryptKey, fileSize, filePath, now, objTypeEnum, objId, userId);
            } else {
                uploadLog = updFileUploadLog(uploadLog.getFileId(), utilBO.getOsId(), utilBO.getBucketName(), fileCode, fileName, encryptKey, fileSize, filePath, now, objTypeEnum, objId, userId);
            }
        }
        try {
            if (ObjectUtils.isEmpty(userMetadata)) {
                userMetadata = new HashMap<>();
            }
            if (ObjectUtils.isNotEmpty(encryptKey)) {
                bytes = AesUtil.encryptECB(bytes, Base64.decodeBase64(encryptKey));
                userMetadata.put(ENCRYPT_KEY, encryptKey);
            }
            fileUploadCoreService.uploadFile(utilBO.getFileUtilService(), utilBO.getBucketName(), filePath, bytes, userMetadata);
            FileInfoBO res = FileInfoBO.builder()
                    .fileCode(fileCode)
                    .prefixUrl(utilBO.getDomain())
                    .fileName(fileName)
                    .fileSize(fileSize)
                    .filePath(filePath.replace(fileIdeaDir, ""))
                    .build();
            if (ObjectUtils.isNotEmpty(checkBO.getZoomData())) {
                uploadLog.setFileZoomImg(res.getZoomFile());
                res.setZoomFile(zoomFile(utilBO, typeEnum, now, checkBO.getZoomData(), fileCode));
                res.setZoomFileSize((long) checkBO.getZoomData().length);
            }
            uploadLog.setUploadStatus(UploadStatusEnum.SUCCESS.getCode());
            return res;
        } catch (Exception e) {
            log.error("基础 文件上传异常 typeEnum:{} fileName:{} fileFormat:{}", typeEnum, fileName, fileFormat);
            uploadLog.setUploadStatus(UploadStatusEnum.FAIL.getCode());
            uploadLog.setErrorMsg("文件上传异常 typeEnum:{" + typeEnum + "}" + " fileName:{" + fileName + "}" + " fileFormat:{" + fileFormat + "}" + e);
            throw new BusinessException("文件上传异常", e);
        } finally {
            fileUploadLogService.updateById(uploadLog);
        }
    }

    /**
     * 缩放文件操作
     *
     * @param utilBO
     * @param typeEnum
     * @param now
     * @param bytes
     */
    private String zoomFile(FileUtilBO utilBO, FileTypeEnum typeEnum, Date now, byte[] bytes, String fileCode) {
        String zoomFilePath = null;
        if (ObjectUtils.isNotEmpty(bytes)) {
            if (typeEnum == FileTypeEnum.VIDEO) {
                zoomFilePath = FilePathUtil.generateFilePath(FilePathConstants.UPLOAD_BASE_FILES_PATH, now, fileCode, hParameter.getFileOS().getVideoCoverFormat());
                fileUploadCoreService.uploadFile(utilBO.getFileUtilService(), utilBO.getBucketName(), zoomFilePath, bytes);
                zoomFilePath = zoomFilePath.replace(FilePathConstants.UPLOAD_BASE_FILES_PATH, "");
            }
        }
        return zoomFilePath;
    }

    /**
     * 添加文件上传日志
     */
    private FileUploadLog addFileUploadLog(Long osId,
                                           String bucketName,
                                           String fileCode,
                                           String fileName,
                                           String encryptKey,
                                           Long fileSize,
                                           String filePath,
                                           Date now,
                                           FileObjTypeEnum objTypeEnum,
                                           Long objId,
                                           Long userId) {
        FileUploadLog log = FileUploadLog.builder()
                .osId(osId)
                .bucketName(bucketName)
                .fileCode(fileCode)
                .fileName(fileName)
                .encryptInfo(JSON.toJSONString(FileEncryptBO.builder().encryptKey(encryptKey).build()))
                .fileSize(fileSize)
                .filePath(filePath)
                .objType(objTypeEnum.getType())
                .objId(objId)
                .uploadStatus(UploadStatusEnum.UPLOADING.getCode())
                .createUid(userId)
                .updateUid(userId)
                .createTime(now)
                .updateTime(now)
                .version(1)
                .delStatus(TrueFalseEnum.FALSE.getCode())
                .build();
        fileUploadLogService.save(log);
        return log;
    }

    /**
     * 更新文件上传日志
     * 先删除旧数据，再添加新数据
     */
    private FileUploadLog updFileUploadLog(Long id,
                                           Long osId,
                                           String bucketName,
                                           String fileCode,
                                           String fileName,
                                           String encryptKey,
                                           Long fileSize,
                                           String filePath,
                                           Date now,
                                           FileObjTypeEnum objTypeEnum,
                                           Long objId,
                                           Long userId) {
        FileUploadLog log = FileUploadLog.builder()
                .fileId(id)
                .updateUid(userId)
                .updateTime(now)
                .delStatus(TrueFalseEnum.TRUE.getCode())
                .build();
        fileUploadLogService.updateById(log);
        return addFileUploadLog(osId, bucketName, fileCode, fileName, encryptKey, fileSize, filePath, now, objTypeEnum, objId, userId);
    }

    /**
     * 文件数据检测
     *
     * @param typeEnum
     * @param bytes
     */
    private FileDataCheckBO fileDataCheck(FileTypeEnum typeEnum, byte[] bytes) {
        byte[] zoomData = null;
        boolean nsfw = false;
        if (typeEnum.equals(FileTypeEnum.IMAGE)) {
            nsfw = fileDataCheckService.checkNsfw(bytes);
        } else if (typeEnum.equals(FileTypeEnum.VIDEO)) {
            zoomData = FfmpegUtil.getVideoCoverImg(bytes, hParameter.getFileOS().getVideoCoverFormat(), hParameter.getFileOS().getVideoCoverWith());
            nsfw = fileDataCheckService.checkNsfw(zoomData);
        }
        if (nsfw) {
            throw new BusinessException("文件含有违规内容，不能上传");
        }
        return FileDataCheckBO.builder().data(bytes).zoomData(zoomData).build();
    }
}
