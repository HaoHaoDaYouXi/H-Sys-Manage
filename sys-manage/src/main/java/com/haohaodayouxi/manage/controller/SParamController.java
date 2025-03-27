package com.haohaodayouxi.manage.controller;

import com.haohaodayouxi.common.core.enums.OkResponse;
import com.haohaodayouxi.common.core.model.res.Response;
import com.haohaodayouxi.manage.service.SParamService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
