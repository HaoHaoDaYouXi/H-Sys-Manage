package com.haohaodayouxi.manage.controller;

import com.haohaodayouxi.common.core.enums.OkResponse;
import com.haohaodayouxi.common.core.model.res.Response;
import com.haohaodayouxi.manage.model.req.menu.SMenuListReq;
import com.haohaodayouxi.manage.service.SMenuService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
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
     * 获取登录缓存
     *
     * @return res
     */
    @PostMapping("/listByParent")
    public Response<Object> listByParent(@RequestBody SMenuListReq req) {
        return OkResponse.OK.toResponse(sMenuService.listByParent(req));
    }

}
