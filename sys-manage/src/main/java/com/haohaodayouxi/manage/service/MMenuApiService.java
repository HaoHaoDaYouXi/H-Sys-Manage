package com.haohaodayouxi.manage.service;

import com.haohaodayouxi.manage.model.db.MMenuApi;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
    /**
 * MMenuApiService
 *
 * @author TONE
 * @date 2025/3/1
 */
public interface MMenuApiService extends IService<MMenuApi>{


    int updateBatchSelective(List<MMenuApi> list);

    int batchInsert(List<MMenuApi> list);

}
