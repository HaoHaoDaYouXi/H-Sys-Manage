package com.haohaodayouxi.manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.haohaodayouxi.manage.model.db.MRoleMenu;

import java.util.List;

/**
 * MRoleMenuService
 *
 * @author TONE
 * @date 2024/12/8
 */
public interface MRoleMenuService extends IService<MRoleMenu> {

    int updateBatch(List<MRoleMenu> list);

    int updateBatchSelective(List<MRoleMenu> list);

    int batchInsert(List<MRoleMenu> list);

}
