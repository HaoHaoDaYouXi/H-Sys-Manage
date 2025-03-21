package com.haohaodayouxi.manage.controller;

import com.haohaodayouxi.common.core.enums.OkResponse;
import com.haohaodayouxi.common.core.interfaces.AddValid;
import com.haohaodayouxi.common.core.interfaces.UpdValid;
import com.haohaodayouxi.common.core.model.bo.ListObjectBO;
import com.haohaodayouxi.common.core.model.res.Response;
import com.haohaodayouxi.common.core.model.vo.keyValue.LabelValueVO;
import com.haohaodayouxi.common.util.constants.StringConstant;
import com.haohaodayouxi.manage.constants.enums.menu.MenuTypeEnum;
import com.haohaodayouxi.manage.model.bo.param.SParamBO;
import com.haohaodayouxi.manage.model.req.menu.ChangeDisableReq;
import com.haohaodayouxi.manage.model.req.menu.SMenuAddOrUpdReq;
import com.haohaodayouxi.manage.model.req.menu.SMenuListReq;
import com.haohaodayouxi.manage.model.req.param.SParamReq;
import com.haohaodayouxi.manage.service.SMenuService;
import com.haohaodayouxi.manage.service.SParamService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * SMenuController
 *
 * @author TONE
 * @date 2025/3/4
 */
@Slf4j
@RestController
@RequestMapping("/s_menu")
public class SMenuController {

    @Resource
    private SMenuService sMenuService;
    @Resource
    private SParamService paramService;


    /**
     * 菜单类型
     *
     * @return res
     */
    @GetMapping("/getMenuType")
    public Response<Object> getMenuType() {
        List<SParamBO> paramBOS = paramService.getByCache(SParamReq.builder().paramCodes(Arrays.stream(MenuTypeEnum.values()).map(m -> m.getCode().toString()).collect(Collectors.joining(StringConstant.EN_COMMA))).build());
        if (ObjectUtils.isEmpty(paramBOS)) {
            return OkResponse.QUERY.toResponse(Arrays.stream(MenuTypeEnum.values()).map(m -> LabelValueVO.builder().label(m.getName()).value(m.getValue()).build()).toList());
        } else {
            return OkResponse.QUERY.toResponse(paramBOS.stream().map(m -> LabelValueVO.builder().label(m.getParamName()).value(m.getParamValue()).build()).toList());
        }
    }

    /**
     * 列表查询
     *
     * @return res
     */
    @PostMapping("/listByParent")
    public Response<Object> listByParent(@RequestBody SMenuListReq req) {
        return OkResponse.QUERY.toResponse(sMenuService.listByParent(req));
    }

    /**
     * 列表查询
     *
     * @return res
     */
    @GetMapping("/labelValueByParent/{parentId}")
    public Response<Object> labelValueByParent(@PathVariable Long parentId) {
        return OkResponse.QUERY.toResponse(sMenuService.labelValueByParent(parentId));
    }

    /**
     * 新增
     *
     * @return res
     */
    @PostMapping("/add")
    public Response<Object> add(@RequestBody @Validated(AddValid.class) SMenuAddOrUpdReq req) {
        sMenuService.addOrUpd(req);
        return OkResponse.INSERT.toResponse(true);
    }

    /**
     * 详情
     *
     * @return res
     */
    @GetMapping("/detail/{id}")
    public Response<Object> detail(@PathVariable String id) {
        return OkResponse.QUERY.toResponse(sMenuService.getById(id));
    }

    /**
     * 修改
     *
     * @return res
     */
    @PostMapping("/upd")
    public Response<Object> upd(@RequestBody @Validated(UpdValid.class) SMenuAddOrUpdReq req) {
        sMenuService.addOrUpd(req);
        return OkResponse.UPDATE.toResponse(true);
    }

    /**
     * 修改禁用状态
     *
     * @return res
     */
    @PostMapping("/changeDisable")
    public Response<Object> changeDisable(@RequestBody @Validated ChangeDisableReq req) {
        sMenuService.changeDisable(req);
        return OkResponse.UPDATE.toResponse(true);
    }

    /**
     * 批量删除
     *
     * @return res
     */
    @PostMapping("/batchDel")
    public Response<Object> batchDel(@RequestBody @Validated ListObjectBO<Long> req) {
        sMenuService.batchDel(req.getList());
        return OkResponse.DELETE.toResponse(true);
    }

}
