package com.haohaodayouxi.manage.controller;

import com.haohaodayouxi.common.core.enums.OkResponse;
import com.haohaodayouxi.common.core.model.res.Response;
import com.haohaodayouxi.manage.service.SUserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

}
