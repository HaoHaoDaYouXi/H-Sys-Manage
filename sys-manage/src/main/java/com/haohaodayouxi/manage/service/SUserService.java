package com.haohaodayouxi.manage.service;

import java.util.List;
import com.haohaodayouxi.manage.model.db.SUser;
import com.baomidou.mybatisplus.extension.service.IService;
    /**
 * SUserService
 *
 * @author TONE
 * @date 2024/12/8
 */
public interface SUserService extends IService<SUser>{


    int updateBatch(List<SUser> list);

    int updateBatchSelective(List<SUser> list);

    int batchInsert(List<SUser> list);

}
