package com.haohaodayouxi.manage.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.haohaodayouxi.manage.model.db.MRoleMenu;
import com.haohaodayouxi.manage.mapper.MRoleMenuMapper;
import com.haohaodayouxi.manage.service.MRoleMenuService;
/**
 * MRoleMenuServiceImpl
 *
 * @author TONE
 * @date 2024/12/8
 */
@Service
public class MRoleMenuServiceImpl extends ServiceImpl<MRoleMenuMapper, MRoleMenu> implements MRoleMenuService{

    @Override
    public int updateBatch(List<MRoleMenu> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int updateBatchSelective(List<MRoleMenu> list) {
        return baseMapper.updateBatchSelective(list);
    }
    @Override
    public int batchInsert(List<MRoleMenu> list) {
        return baseMapper.batchInsert(list);
    }
}
