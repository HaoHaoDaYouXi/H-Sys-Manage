package com.haohaodayouxi.manage.controller;

import com.haohaodayouxi.common.core.enums.OkResponse;
import com.haohaodayouxi.common.core.interfaces.AddValid;
import com.haohaodayouxi.common.core.interfaces.UpdValid;
import com.haohaodayouxi.common.core.model.res.Response;
import com.haohaodayouxi.manage.model.req.menu.SMenuAddOrUpdReq;
import com.haohaodayouxi.manage.model.req.menu.SMenuListReq;
import com.haohaodayouxi.manage.service.SMenuService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


    /**
     * 列表查询
     *
     * @return res
     */
    @PostMapping("/listByParent")
    public Response<Object> listByParent(@RequestBody SMenuListReq req) {
        return OkResponse.OK.toResponse(sMenuService.listByParent(req));
    }

    /**
     * 新增
     *
     * @return res
     */
    @PostMapping("/add")
    public Response<Object> add(@RequestBody @Validated(AddValid.class) SMenuAddOrUpdReq req) {
        sMenuService.addOrUpdSMenu(req);
        return OkResponse.INSERT.toResponse(true);
    }

    /**
     * 修改
     *
     * @return res
     */
    @PostMapping("/upd")
    public Response<Object> upd(@RequestBody @Validated(UpdValid.class) SMenuAddOrUpdReq req) {
        sMenuService.addOrUpdSMenu(req);
        return OkResponse.UPDATE.toResponse(true);
    }

}
