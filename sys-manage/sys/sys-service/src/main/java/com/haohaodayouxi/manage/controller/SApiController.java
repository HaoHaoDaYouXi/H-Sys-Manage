package com.haohaodayouxi.manage.controller;

import com.haohaodayouxi.common.core.enums.OkResponse;
import com.haohaodayouxi.common.core.interfaces.AddValid;
import com.haohaodayouxi.common.core.interfaces.UpdValid;
import com.haohaodayouxi.common.core.model.bo.ListObjectBO;
import com.haohaodayouxi.common.core.model.req.page.PageBaseReq;
import com.haohaodayouxi.common.core.model.res.Response;
import com.haohaodayouxi.common.core.model.vo.page.PageBaseVO;
import com.haohaodayouxi.manage.model.db.SApi;
import com.haohaodayouxi.manage.model.req.api.SApiAddOrUpdReq;
import com.haohaodayouxi.manage.model.req.api.SApiPageListReq;
import com.haohaodayouxi.manage.service.SApiService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * SApiController
 *
 * @author TONE
 * @date 2025/7/15
 */
@Slf4j
@RestController
@RequestMapping("/s_api")
public class SApiController {

    @Resource
    private SApiService apiService;

    /**
     * 获取模块列表
     *
     * @return res
     */
    @PostMapping("/getModuleList")
    public Response<PageBaseVO<String>> getModuleList(@RequestBody PageBaseReq req) {
        return OkResponse.QUERY.toResponse(apiService.getModuleList(req));
    }

    /**
     * 根据参数获取
     *
     * @return res
     */
    @PostMapping("/pageList")
    public Response<PageBaseVO<SApi>> pageList(@RequestBody SApiPageListReq req) {
        return OkResponse.QUERY.toResponse(apiService.pageList(req));
    }

    /**
     * 新增
     *
     * @return res
     */
    @PostMapping("/add")
    public Response<Boolean> add(@RequestBody @Validated(AddValid.class) SApiAddOrUpdReq req) {
        apiService.addOrUpd(req);
        return OkResponse.INSERT.toResponse(true);
    }

    /**
     * 详情
     *
     * @return res
     */
    @GetMapping("/detail/{id}")
    public Response<SApi> detail(@PathVariable Long id) {
        return OkResponse.QUERY.toResponse(apiService.getById(id));
    }

    /**
     * 修改
     *
     * @return res
     */
    @PostMapping("/upd")
    public Response<Boolean> upd(@RequestBody @Validated(UpdValid.class) SApiAddOrUpdReq req) {
        apiService.addOrUpd(req);
        return OkResponse.UPDATE.toResponse(true);
    }

    /**
     * 批量删除
     *
     * @return res
     */
    @PostMapping("/batchDel")
    public Response<Boolean> batchDel(@RequestBody @Validated ListObjectBO<Long> req) {
        apiService.batchDel(req.getList());
        return OkResponse.DELETE.toResponse(true);
    }

}
