package com.haohaodayouxi.manage.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.haohaodayouxi.manage.mapper.SMenuMapper;
import com.haohaodayouxi.manage.model.db.SMenu;
import com.haohaodayouxi.manage.service.SMenuService;
/**
 * SMenuServiceImpl
 *
 * @author TONE
 * @date 2024/12/8
 */
@Service
public class SMenuServiceImpl extends ServiceImpl<SMenuMapper, SMenu> implements SMenuService{

    @Override
    public int updateBatch(List<SMenu> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int updateBatchSelective(List<SMenu> list) {
        return baseMapper.updateBatchSelective(list);
    }
    @Override
    public int batchInsert(List<SMenu> list) {
        return baseMapper.batchInsert(list);
    }
}
