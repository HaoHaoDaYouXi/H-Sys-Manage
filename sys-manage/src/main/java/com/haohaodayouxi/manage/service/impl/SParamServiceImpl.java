package com.haohaodayouxi.manage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haohaodayouxi.common.core.interfaces.InitService;
import com.haohaodayouxi.manage.mapper.SParamMapper;
import com.haohaodayouxi.manage.model.db.SParam;
import com.haohaodayouxi.manage.service.SParamService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * SParamServiceImpl
 *
 * @author TONE
 * @date 2024/12/8
 */
@Service
public class SParamServiceImpl extends ServiceImpl<SParamMapper, SParam> implements SParamService, InitService {

    @Override
    public void init() {
        // 查询并放入缓存
        // 根据查询出来的更新本地参数
    }

    @Override
    public int updateBatch(List<SParam> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int updateBatchSelective(List<SParam> list) {
        return baseMapper.updateBatchSelective(list);
    }

    @Override
    public int batchInsert(List<SParam> list) {
        return baseMapper.batchInsert(list);
    }
}
