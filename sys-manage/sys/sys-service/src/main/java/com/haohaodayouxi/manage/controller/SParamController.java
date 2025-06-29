package com.haohaodayouxi.manage.controller;

import com.haohaodayouxi.common.core.enums.OkResponse;
import com.haohaodayouxi.common.core.interfaces.AddValid;
import com.haohaodayouxi.common.core.interfaces.UpdValid;
import com.haohaodayouxi.common.core.model.bo.ListObjectBO;
import com.haohaodayouxi.common.core.model.res.Response;
import com.haohaodayouxi.manage.constants.enums.param.RootParamCodeEnum;
import com.haohaodayouxi.manage.model.bo.param.SParamBO;
import com.haohaodayouxi.manage.model.db.SParam;
import com.haohaodayouxi.manage.model.req.param.SParamAddOrUpdReq;
import com.haohaodayouxi.manage.model.req.param.SParamReq;
import com.haohaodayouxi.manage.service.SParamService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public Response<List<SParamBO>> getAllParam() {
        return OkResponse.QUERY.toResponse(paramService.getByCache(null));
    }

    /**
     * 根据参数获取
     *
     * @return res
     */
    @GetMapping("/getSParamByParentCode")
    public Response<List<SParamBO>> getSParamByReq(@RequestParam("paramParentCode") Long paramParentCode) {
        if (paramParentCode == null) {
            paramParentCode = RootParamCodeEnum.SYS_PARAM.getCode();
        }
        return OkResponse.QUERY.toResponse(paramService.getByCache(SParamReq.builder().paramParentCode(paramParentCode).build()));
    }

    /**
     * 新增
     *
     * @return res
     */
    @PostMapping("/add")
    public Response<Boolean> add(@RequestBody @Validated(AddValid.class) SParamAddOrUpdReq req) {
        paramService.addOrUpd(req);
        return OkResponse.INSERT.toResponse(true);
    }

    /**
     * 详情
     *
     * @return res
     */
    @GetMapping("/detail/{id}")
    public Response<SParam> detail(@PathVariable Long id) {
        return OkResponse.QUERY.toResponse(paramService.getById(id));
    }

    /**
     * 修改
     *
     * @return res
     */
    @PostMapping("/upd")
    public Response<Boolean> upd(@RequestBody @Validated(UpdValid.class) SParamAddOrUpdReq req) {
        paramService.addOrUpd(req);
        return OkResponse.UPDATE.toResponse(true);
    }

    /**
     * 批量删除
     *
     * @return res
     */
    @PostMapping("/batchDel")
    public Response<Boolean> batchDel(@RequestBody @Validated ListObjectBO<Long> req) {
        paramService.batchDel(req.getList());
        return OkResponse.DELETE.toResponse(true);
    }

}
