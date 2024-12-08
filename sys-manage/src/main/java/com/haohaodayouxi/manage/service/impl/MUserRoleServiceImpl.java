package com.haohaodayouxi.manage.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.haohaodayouxi.manage.mapper.MUserRoleMapper;
import com.haohaodayouxi.manage.model.db.MUserRole;
import com.haohaodayouxi.manage.service.MUserRoleService;
/**
 * MUserRoleServiceImpl
 *
 * @author TONE
 * @date 2024/12/8
 */
@Service
public class MUserRoleServiceImpl extends ServiceImpl<MUserRoleMapper, MUserRole> implements MUserRoleService{

    @Override
    public int updateBatch(List<MUserRole> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int updateBatchSelective(List<MUserRole> list) {
        return baseMapper.updateBatchSelective(list);
    }
    @Override
    public int batchInsert(List<MUserRole> list) {
        return baseMapper.batchInsert(list);
    }
}
