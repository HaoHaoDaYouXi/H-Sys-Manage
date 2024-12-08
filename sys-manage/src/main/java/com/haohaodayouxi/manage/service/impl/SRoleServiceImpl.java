package com.haohaodayouxi.manage.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haohaodayouxi.manage.mapper.SRoleMapper;
import java.util.List;
import com.haohaodayouxi.manage.model.db.SRole;
import com.haohaodayouxi.manage.service.SRoleService;
/**
 * SRoleServiceImpl
 *
 * @author TONE
 * @date 2024/12/8
 */
@Service
public class SRoleServiceImpl extends ServiceImpl<SRoleMapper, SRole> implements SRoleService{

    @Override
    public int updateBatch(List<SRole> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int updateBatchSelective(List<SRole> list) {
        return baseMapper.updateBatchSelective(list);
    }
    @Override
    public int batchInsert(List<SRole> list) {
        return baseMapper.batchInsert(list);
    }
}
