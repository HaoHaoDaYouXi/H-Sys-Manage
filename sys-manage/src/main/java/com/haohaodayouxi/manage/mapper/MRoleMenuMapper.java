package com.haohaodayouxi.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.haohaodayouxi.manage.model.db.MRoleMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * MRoleMenuMapper
 *
 * @author TONE
 * @date 2024/12/8
 */
public interface MRoleMenuMapper extends BaseMapper<MRoleMenu> {

    int updateBatch(List<MRoleMenu> list);

    int updateBatchSelective(List<MRoleMenu> list);

    int batchInsert(@Param("list") List<MRoleMenu> list);
}
