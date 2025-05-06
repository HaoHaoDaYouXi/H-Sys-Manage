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
import com.haohaodayouxi.manage.service.FileService;
import com.haohaodayouxi.manage.utils.LoginCacheUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
     * 预览文件
     *
     * @param req
     * @return
     */
    @OpenApi
    @GetMapping(value = "/preview")
    public void detail(HttpServletRequest request, HttpServletResponse response, FilePreviewReq req) {
        Assert.notNull(req, "无效的请求");
        Assert.notNull(req.getToken(), "认证身份无效");
        LoginCacheBO bo = loginCacheUtil.getLoginCache(req.getToken());
        Assert.notNull(bo, "请先登录");
        fileService.previewFile(request, response, req);
    }

    /**
     * 上传图片
     *
     * @param req 文件
     * @return FileRes
     */
    @ResponseBody
    @PostMapping(value = "/upload/img")
    public Response<String> uploadImg(@Validated FileUploadReq req) {
        LoginCacheBO bo = (LoginCacheBO) CurrentUserContextHolder.get();
        req.setUserId(bo.getUserLoginCacheBO().getUserId());
        req.setObjTypeEnum(FileObjTypeEnum.MANAGE);
        req.setObjId(bo.getUserLoginCacheBO().getUserId());
        req.setTypeEnum(FileTypeEnum.IMAGE);
        return OkResponse.UPLOAD_FILE.toResponse(fileService.uploadFile(req));
    }

    /**
     * 上传文档
     *
     * @param req 文件
     * @return FileRes
     */
    @ResponseBody
    @PostMapping(value = "/upload/doc")
    public Response<String> uploadDoc(@Validated FileUploadReq req) {
        LoginCacheBO bo = (LoginCacheBO) CurrentUserContextHolder.get();
        req.setUserId(bo.getUserLoginCacheBO().getUserId());
        req.setObjTypeEnum(FileObjTypeEnum.MANAGE);
        req.setObjId(bo.getUserLoginCacheBO().getUserId());
        req.setTypeEnum(FileTypeEnum.DOC);
        return OkResponse.UPLOAD_FILE.toResponse(fileService.uploadFile(req));
    }

    /**
     * 上传图片或文档
     *
     * @param req 文件
     * @return FileRes
     */
    @ResponseBody
    @PostMapping(value = "/upload/imgOrDoc")
    public Response<String> uploadImgOrDoc(@Validated FileUploadReq req) {
        LoginCacheBO bo = (LoginCacheBO) CurrentUserContextHolder.get();
        req.setUserId(bo.getUserLoginCacheBO().getUserId());
        req.setObjTypeEnum(FileObjTypeEnum.MANAGE);
        req.setObjId(bo.getUserLoginCacheBO().getUserId());
        req.setTypeEnum(FileTypeEnum.IMAGE_OR_DOC);
        return OkResponse.UPLOAD_FILE.toResponse(fileService.uploadFile(req));
    }

    /**
     * 上传音频
     *
     * @param req 文件
     * @return FileRes
     */
    @ResponseBody
    @PostMapping(value = "/upload/audio")
    public Response<String> uploadAudio(@Validated FileUploadReq req) {
        LoginCacheBO bo = (LoginCacheBO) CurrentUserContextHolder.get();
        req.setUserId(bo.getUserLoginCacheBO().getUserId());
        req.setObjTypeEnum(FileObjTypeEnum.MANAGE);
        req.setObjId(bo.getUserLoginCacheBO().getUserId());
        req.setTypeEnum(FileTypeEnum.AUDIO);
        return OkResponse.UPLOAD_FILE.toResponse(fileService.uploadFile(req));
    }

    /**
     * 上传视频
     *
     * @param req 文件
     * @return FileRes
     */
    @ResponseBody
    @PostMapping(value = "/upload/video")
    public Response<String> uploadVideo(@Validated FileUploadReq req) {
        LoginCacheBO bo = (LoginCacheBO) CurrentUserContextHolder.get();
        req.setUserId(bo.getUserLoginCacheBO().getUserId());
        req.setObjTypeEnum(FileObjTypeEnum.MANAGE);
        req.setObjId(bo.getUserLoginCacheBO().getUserId());
        req.setTypeEnum(FileTypeEnum.VIDEO);
        return OkResponse.UPLOAD_FILE.toResponse(fileService.uploadFile(req));
    }
}
