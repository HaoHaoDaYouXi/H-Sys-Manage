package com.haohaodayouxi.manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.haohaodayouxi.manage.model.db.SRole;

import java.util.List;

/**
 * SRoleService
 *
 * @author TONE
 * @date 2024/12/8
 */
public interface SRoleService extends IService<SRole> {
    
    int updateBatch(List<SRole> list);

    int updateBatchSelective(List<SRole> list);

    int batchInsert(List<SRole> list);

}
