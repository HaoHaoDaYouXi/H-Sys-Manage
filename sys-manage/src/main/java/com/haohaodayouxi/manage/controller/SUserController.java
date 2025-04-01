package com.haohaodayouxi.manage.controller;

import com.haohaodayouxi.common.core.enums.OkResponse;
import com.haohaodayouxi.common.core.interfaces.AddValid;
import com.haohaodayouxi.common.core.interfaces.UpdValid;
import com.haohaodayouxi.common.core.model.bo.ListObjectBO;
import com.haohaodayouxi.common.core.model.res.Response;
import com.haohaodayouxi.manage.model.req.user.SUserAddOrUpdReq;
import com.haohaodayouxi.manage.model.req.user.SUserPageListReq;
import com.haohaodayouxi.manage.service.SUserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * MUserRoleController
 *
 * @author TONE
 * @date 2025/3/27
 */
@Slf4j
@RestController
@RequestMapping("/s_user")
public class SUserController {

    @Resource
    private SUserService userService;


    /**
     * 改变使用角色
     *
     * @return res
     */
    @GetMapping("/changeUseRole")
    public Response<Object> changeUseRole(@RequestParam("id") Long id) {
        userService.changeUseRole(id);
        return OkResponse.OK.toResponse(true);
    }


    /**
     * 列表查询
     *
     * @return res
     */
    @PostMapping("/pageList")
    public Response<Object> listByParent(@RequestBody SUserPageListReq req) {
        return OkResponse.QUERY.toResponse(userService.pageList(req));
    }

    /**
     * 新增
     *
     * @return res
     */
    @PostMapping("/add")
    public Response<Object> add(@RequestBody @Validated(AddValid.class) SUserAddOrUpdReq req) {
        userService.addOrUpd(req);
        return OkResponse.INSERT.toResponse(true);
    }

    /**
     * 详情
     *
     * @return res
     */
    @GetMapping("/detail/{id}")
    public Response<Object> detail(@PathVariable Long id) {
        return OkResponse.QUERY.toResponse(userService.detail(id));
    }

    /**
     * 修改
     *
     * @return res
     */
    @PostMapping("/upd")
    public Response<Object> upd(@RequestBody @Validated(UpdValid.class) SUserAddOrUpdReq req) {
        userService.addOrUpd(req);
        return OkResponse.UPDATE.toResponse(true);
    }

    /**
     * 批量删除
     *
     * @return res
     */
    @PostMapping("/batchDel")
    public Response<Object> batchDel(@RequestBody @Validated ListObjectBO<Long> req) {
        userService.batchDel(req.getList());
        return OkResponse.DELETE.toResponse(true);
    }

}
