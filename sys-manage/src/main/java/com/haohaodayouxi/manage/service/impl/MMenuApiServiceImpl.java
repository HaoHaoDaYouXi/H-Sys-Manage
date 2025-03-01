package com.haohaodayouxi.manage.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haohaodayouxi.manage.model.db.MMenuApi;
import java.util.List;
import com.haohaodayouxi.manage.mapper.MMenuApiMapper;
import com.haohaodayouxi.manage.service.MMenuApiService;
/**
 * MMenuApiServiceImpl
 *
 * @author TONE
 * @date 2025/3/1
 */
@Service
public class MMenuApiServiceImpl extends ServiceImpl<MMenuApiMapper, MMenuApi> implements MMenuApiService{

    @Override
    public int updateBatchSelective(List<MMenuApi> list) {
        return baseMapper.updateBatchSelective(list);
    }
    @Override
    public int batchInsert(List<MMenuApi> list) {
        return baseMapper.batchInsert(list);
    }
}
