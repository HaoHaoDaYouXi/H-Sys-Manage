package com.haohaodayouxi.manage.controller;

import com.haohaodayouxi.common.core.enums.OkResponse;
import com.haohaodayouxi.common.core.interfaces.AddValid;
import com.haohaodayouxi.common.core.interfaces.UpdValid;
import com.haohaodayouxi.common.core.model.res.Response;
import com.haohaodayouxi.manage.model.req.param.SParamAddOrUpdReq;
import com.haohaodayouxi.manage.service.SParamService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * SParamController
 *
 * @author TONE
 * @date 2025/3/27
 */
@Slf4j
@RestController
@RequestMapping("/s_param")
public class SParamController {
    @Resource
    private SParamService paramService;


    /**
     * 获取所有参数
     *
     * @return res
     */
    @GetMapping("/getAllParam")
    public Response<Object> getAllParam() {
        return OkResponse.QUERY.toResponse(paramService.getByCache(null));
    }

    /**
     * 新增
     *
     * @return res
     */
    @PostMapping("/add")
    public Response<Object> add(@RequestBody @Validated(AddValid.class) SParamAddOrUpdReq req) {
        paramService.addOrUpd(req);
        return OkResponse.INSERT.toResponse(true);
    }

    /**
     * 详情
     *
     * @return res
     */
    @GetMapping("/detail/{id}")
    public Response<Object> detail(@PathVariable Long id) {
        return OkResponse.QUERY.toResponse(paramService.getById(id));
    }

    /**
     * 修改
     *
     * @return res
     */
    @PostMapping("/upd")
    public Response<Object> upd(@RequestBody @Validated(UpdValid.class) SParamAddOrUpdReq req) {
        paramService.addOrUpd(req);
        return OkResponse.UPDATE.toResponse(true);
    }

}
