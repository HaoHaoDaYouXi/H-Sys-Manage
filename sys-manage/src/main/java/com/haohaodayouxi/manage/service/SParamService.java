package com.haohaodayouxi.manage.service;

import java.util.List;
import com.haohaodayouxi.manage.model.db.SParam;
import com.baomidou.mybatisplus.extension.service.IService;
    /**
 * SParamService
 *
 * @author TONE
 * @date 2024/12/8
 */
public interface SParamService extends IService<SParam>{


    int updateBatch(List<SParam> list);

    int updateBatchSelective(List<SParam> list);

    int batchInsert(List<SParam> list);

}
