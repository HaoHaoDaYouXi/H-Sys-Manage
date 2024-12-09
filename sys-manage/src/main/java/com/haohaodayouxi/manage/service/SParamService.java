package com.haohaodayouxi.manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.haohaodayouxi.manage.model.db.SParam;

import java.util.List;

/**
 * SParamService
 *
 * @author TONE
 * @date 2024/12/8
 */
public interface SParamService extends IService<SParam> {
    
    int updateBatch(List<SParam> list);

    int updateBatchSelective(List<SParam> list);

    int batchInsert(List<SParam> list);

}
