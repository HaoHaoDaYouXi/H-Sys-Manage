package com.haohaodayouxi.manage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haohaodayouxi.manage.mapper.SUserMapper;
import com.haohaodayouxi.manage.model.db.SUser;
import com.haohaodayouxi.manage.service.SUserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * SUserServiceImpl
 *
 * @author TONE
 * @date 2024/12/8
 */
@Service
public class SUserServiceImpl extends ServiceImpl<SUserMapper, SUser> implements SUserService {

    @Override
    public int updateBatch(List<SUser> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int updateBatchSelective(List<SUser> list) {
        return baseMapper.updateBatchSelective(list);
    }

    @Override
    public int batchInsert(List<SUser> list) {
        return baseMapper.batchInsert(list);
    }
}
