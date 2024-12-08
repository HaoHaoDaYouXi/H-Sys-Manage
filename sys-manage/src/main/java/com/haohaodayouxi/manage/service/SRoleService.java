package com.haohaodayouxi.manage.service;

import java.util.List;
import com.haohaodayouxi.manage.model.db.SRole;
import com.baomidou.mybatisplus.extension.service.IService;
    /**
 * SRoleService
 *
 * @author TONE
 * @date 2024/12/8
 */
public interface SRoleService extends IService<SRole>{


    int updateBatch(List<SRole> list);

    int updateBatchSelective(List<SRole> list);

    int batchInsert(List<SRole> list);

}
