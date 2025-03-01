package com.haohaodayouxi.manage.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.haohaodayouxi.manage.model.db.SApi;
import com.haohaodayouxi.manage.mapper.SApiMapper;
import com.haohaodayouxi.manage.service.SApiService;
/**
 * SApiServiceImpl
 *
 * @author TONE
 * @date 2025/3/1
 */
@Service
public class SApiServiceImpl extends ServiceImpl<SApiMapper, SApi> implements SApiService{

    @Override
    public int updateBatchSelective(List<SApi> list) {
        return baseMapper.updateBatchSelective(list);
    }
    @Override
    public int batchInsert(List<SApi> list) {
        return baseMapper.batchInsert(list);
    }
}
