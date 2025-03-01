package com.haohaodayouxi.manage.service;

import java.util.List;
import com.haohaodayouxi.manage.model.db.SApi;
import com.baomidou.mybatisplus.extension.service.IService;
    /**
 * SApiService
 *
 * @author TONE
 * @date 2025/3/1
 */
public interface SApiService extends IService<SApi>{


    int updateBatchSelective(List<SApi> list);

    int batchInsert(List<SApi> list);

}
