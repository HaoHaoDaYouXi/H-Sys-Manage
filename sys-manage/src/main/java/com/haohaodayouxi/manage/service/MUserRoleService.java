package com.haohaodayouxi.manage.service;

import java.util.List;
import com.haohaodayouxi.manage.model.db.MUserRole;
import com.baomidou.mybatisplus.extension.service.IService;
    /**
 * MUserRoleService
 *
 * @author TONE
 * @date 2024/12/8
 */
public interface MUserRoleService extends IService<MUserRole>{


    int updateBatch(List<MUserRole> list);

    int updateBatchSelective(List<MUserRole> list);

    int batchInsert(List<MUserRole> list);

}
