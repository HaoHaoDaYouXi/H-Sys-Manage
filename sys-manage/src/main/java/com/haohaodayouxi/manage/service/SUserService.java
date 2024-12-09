package com.haohaodayouxi.manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.haohaodayouxi.manage.model.db.SUser;

import java.util.List;

/**
 * SUserService
 *
 * @author TONE
 * @date 2024/12/8
 */
public interface SUserService extends IService<SUser> {

    int updateBatch(List<SUser> list);

    int updateBatchSelective(List<SUser> list);

    int batchInsert(List<SUser> list);

}
