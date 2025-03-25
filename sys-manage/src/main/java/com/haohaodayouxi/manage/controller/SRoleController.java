package com.haohaodayouxi.manage.controller;

import com.haohaodayouxi.common.core.enums.OkResponse;
import com.haohaodayouxi.common.core.interfaces.AddValid;
import com.haohaodayouxi.common.core.interfaces.UpdValid;
import com.haohaodayouxi.common.core.model.bo.ListObjectBO;
import com.haohaodayouxi.common.core.model.res.Response;
import com.haohaodayouxi.common.core.model.vo.keyValue.LabelValueVO;
import com.haohaodayouxi.common.util.constants.StringConstant;
import com.haohaodayouxi.manage.constants.enums.role.RoleTypeEnum;
import com.haohaodayouxi.manage.model.bo.param.SParamBO;
import com.haohaodayouxi.manage.model.req.param.SParamReq;
import com.haohaodayouxi.manage.model.req.role.SRoleAddOrUpdReq;
import com.haohaodayouxi.manage.model.req.role.SRolePageListReq;
import com.haohaodayouxi.manage.model.res.role.RoleMenuDetailRes;
import com.haohaodayouxi.manage.service.SParamService;
import com.haohaodayouxi.manage.service.SRoleService;
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
@RequestMapping("/s_role")
public class SRoleController {

    @Resource
    private SRoleService sRoleService;
    @Resource
    private SParamService paramService;


    /**
     * 角色类型
     *
     * @return res
     */
    @GetMapping("/getRoleType")
    public Response<List<LabelValueVO<String, String>>> getRoleType() {
        List<SParamBO> paramBOS = paramService.getByCache(SParamReq.builder().paramCodes(Arrays.stream(RoleTypeEnum.values()).map(RoleTypeEnum::getCode).collect(Collectors.joining(StringConstant.EN_COMMA))).build());
        if (ObjectUtils.isEmpty(paramBOS)) {
            return OkResponse.QUERY.toResponse(Arrays.stream(RoleTypeEnum.values()).map(m -> LabelValueVO.<String, String>builder().label(m.getName()).value(m.getCode()).build()).toList());
        } else {
            return OkResponse.QUERY.toResponse(paramBOS.stream().map(m -> LabelValueVO.<String, String>builder().label(m.getParamName()).value(m.getParamValue()).build()).toList());
        }
    }

    /**
     * 列表查询
     *
     * @return res
     */
    @PostMapping("/pageList")
    public Response<Object> listByParent(@RequestBody SRolePageListReq req) {
        return OkResponse.QUERY.toResponse(sRoleService.pageList(req));
    }

    /**
     * 新增
     *
     * @return res
     */
    @PostMapping("/add")
    public Response<Object> add(@RequestBody @Validated(AddValid.class) SRoleAddOrUpdReq req) {
        sRoleService.addOrUpd(req);
        return OkResponse.INSERT.toResponse(true);
    }

    /**
     * 详情
     *
     * @return res
     */
    @GetMapping("/detail/{id}")
    public Response<RoleMenuDetailRes> detail(@PathVariable Long id) {
        return OkResponse.QUERY.toResponse(sRoleService.getDetail(id));
    }

    /**
     * 修改
     *
     * @return res
     */
    @PostMapping("/upd")
    public Response<Object> upd(@RequestBody @Validated(UpdValid.class) SRoleAddOrUpdReq req) {
        sRoleService.addOrUpd(req);
        return OkResponse.UPDATE.toResponse(true);
    }

    /**
     * 批量删除
     *
     * @return res
     */
    @PostMapping("/batchDel")
    public Response<Object> batchDel(@RequestBody @Validated ListObjectBO<Long> req) {
        sRoleService.batchDel(req.getList());
        return OkResponse.DELETE.toResponse(true);
    }

}
