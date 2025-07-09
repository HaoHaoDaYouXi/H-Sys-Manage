package com.haohaodayouxi.manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.haohaodayouxi.manage.model.db.SApi;

import java.util.List;

/**
 * SApiService
 *
 * @author TONE
 * @date 2025/3/1
 */
public interface SApiService extends IService<SApi> {

    int updateBatchSelective(List<SApi> list);

    int batchInsert(List<SApi> list);

    boolean checkRoleApi(Long roleId, String apiKey);
}
