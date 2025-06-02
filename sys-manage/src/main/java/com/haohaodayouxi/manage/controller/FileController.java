package com.haohaodayouxi.manage.controller;

import com.haohaodayouxi.common.core.annotation.OpenApi;
import com.haohaodayouxi.common.core.constants.CurrentUserContextHolder;
import com.haohaodayouxi.common.core.enums.OkResponse;
import com.haohaodayouxi.common.core.model.res.Response;
import com.haohaodayouxi.manage.constants.enums.file.FileObjTypeEnum;
import com.haohaodayouxi.manage.constants.enums.file.FileTypeEnum;
import com.haohaodayouxi.manage.model.bo.login.LoginCacheBO;
import com.haohaodayouxi.manage.model.req.file.FilePreviewReq;
import com.haohaodayouxi.manage.model.req.file.FileUploadReq;
import com.haohaodayouxi.manage.model.res.file.FileUploadRes;
import com.haohaodayouxi.manage.service.FileService;
import com.haohaodayouxi.manage.utils.LoginCacheUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * FileController
 *
 * @author TONE
 * @date 2025/5/6
 */
@Slf4j
@Controller
@RequestMapping("/file")
public class FileController {
    @Resource
    private FileService fileService;
    @Resource
    private LoginCacheUtil loginCacheUtil;

    /**
     * 根据编码获取文件
     *
     * @param request  req
     * @param response res
     * @param fileCode fileCode
     */
    @GetMapping(value = "/detail/{fileCode}")
    public void previewByFileCode(HttpServletRequest request, HttpServletResponse response, @PathVariable("fileCode") String fileCode) {
        fileService.prefixFileByFileCode(request, response, fileCode);
    }

    /**
     * 根据编码获取文件预览地址
     *
     * @param fileCode fileCode
     */
    @GetMapping(value = "/getPreviewUrl/{fileCode}")
    public Response<String> getPreviewUrlByFileCode(@PathVariable("fileCode") String fileCode) {
        return OkResponse.OK.toResponse(fileService.getPreviewUrlByFileCode(fileCode));
    }

    /**
     * 预览文件
     *
     * @param req req
     */
    @OpenApi
    @GetMapping(value = "/preview/{fileName}")
    public void openPreviewFile(HttpServletRequest request, HttpServletResponse response, @PathVariable("fileName") String fileName, FilePreviewReq req) {
        Assert.notNull(req, "无效的请求");
        Assert.notNull(req.getT(), "认证身份无效");
        LoginCacheBO bo = loginCacheUtil.getLoginCache(req.getT());
        Assert.notNull(bo, "请先登录");
        req.setFileName(fileName);
        fileService.previewFile(request, response, req);
    }

    /**
     * 上传图片
     *
     * @param file 文件
     * @return FileRes
     */
    @ResponseBody
    @PostMapping(value = "/upload/img")
    public Response<FileUploadRes> uploadImg(@Validated @NotNull(message = "文件不能为空") MultipartFile file) {
        LoginCacheBO bo = (LoginCacheBO) CurrentUserContextHolder.get();
        return OkResponse.UPLOAD_FILE.toResponse(fileService.uploadFile(FileUploadReq.builder()
                .file(file)
                .userId(bo.getUserLoginCacheBO().getUserId())
                .objTypeEnum(FileObjTypeEnum.MANAGE)
                .objId(bo.getUserLoginCacheBO().getUserId())
                .typeEnum(FileTypeEnum.IMAGE)
                .build()));
    }

    /**
     * 上传文档
     *
     * @param file 文件
     * @return FileRes
     */
    @ResponseBody
    @PostMapping(value = "/upload/doc")
    public Response<FileUploadRes> uploadDoc(@Validated @NotNull(message = "文件不能为空") MultipartFile file) {
        LoginCacheBO bo = (LoginCacheBO) CurrentUserContextHolder.get();
        return OkResponse.UPLOAD_FILE.toResponse(fileService.uploadFile(FileUploadReq.builder()
                .file(file)
                .userId(bo.getUserLoginCacheBO().getUserId())
                .objTypeEnum(FileObjTypeEnum.MANAGE)
                .objId(bo.getUserLoginCacheBO().getUserId())
                .typeEnum(FileTypeEnum.DOC)
                .build()));
    }

    /**
     * 上传图片或文档
     *
     * @param file 文件
     * @return FileRes
     */
    @ResponseBody
    @PostMapping(value = "/upload/imgOrDoc")
    public Response<FileUploadRes> uploadImgOrDoc(@Validated @NotNull(message = "文件不能为空") MultipartFile file) {
        LoginCacheBO bo = (LoginCacheBO) CurrentUserContextHolder.get();
        return OkResponse.UPLOAD_FILE.toResponse(fileService.uploadFile(FileUploadReq.builder()
                .file(file)
                .userId(bo.getUserLoginCacheBO().getUserId())
                .objTypeEnum(FileObjTypeEnum.MANAGE)
                .objId(bo.getUserLoginCacheBO().getUserId())
                .typeEnum(FileTypeEnum.IMAGE_OR_DOC)
                .build()));
    }

    /**
     * 上传音频
     *
     * @param file 文件
     * @return FileRes
     */
    @ResponseBody
    @PostMapping(value = "/upload/audio")
    public Response<FileUploadRes> uploadAudio(@Validated @NotNull(message = "文件不能为空") MultipartFile file) {
        LoginCacheBO bo = (LoginCacheBO) CurrentUserContextHolder.get();
        return OkResponse.UPLOAD_FILE.toResponse(fileService.uploadFile(FileUploadReq.builder()
                .file(file)
                .userId(bo.getUserLoginCacheBO().getUserId())
                .objTypeEnum(FileObjTypeEnum.MANAGE)
                .objId(bo.getUserLoginCacheBO().getUserId())
                .typeEnum(FileTypeEnum.AUDIO)
                .build()));
    }

    /**
     * 上传视频
     *
     * @param file 文件
     * @return FileRes
     */
    @ResponseBody
    @PostMapping(value = "/upload/video")
    public Response<FileUploadRes> uploadVideo(@Validated @NotNull(message = "文件不能为空") MultipartFile file) {
        LoginCacheBO bo = (LoginCacheBO) CurrentUserContextHolder.get();
        return OkResponse.UPLOAD_FILE.toResponse(fileService.uploadFile(FileUploadReq.builder()
                .file(file)
                .userId(bo.getUserLoginCacheBO().getUserId())
                .objTypeEnum(FileObjTypeEnum.MANAGE)
                .objId(bo.getUserLoginCacheBO().getUserId())
                .typeEnum(FileTypeEnum.VIDEO)
                .build()));
    }
}
