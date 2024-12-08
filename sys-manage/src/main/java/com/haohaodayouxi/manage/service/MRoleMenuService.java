package com.haohaodayouxi.manage.service;

import java.util.List;
import com.haohaodayouxi.manage.model.db.MRoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;
    /**
 * MRoleMenuService
 *
 * @author TONE
 * @date 2024/12/8
 */
public interface MRoleMenuService extends IService<MRoleMenu>{


    int updateBatch(List<MRoleMenu> list);

    int updateBatchSelective(List<MRoleMenu> list);

    int batchInsert(List<MRoleMenu> list);

}
