package com.haohaodayouxi.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.haohaodayouxi.manage.model.db.MUserRole;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * MUserRoleMapper
 *
 * @author TONE
 * @date 2024/12/8
 */
public interface MUserRoleMapper extends BaseMapper<MUserRole> {
    int updateBatch(List<MUserRole> list);

    int updateBatchSelective(List<MUserRole> list);

    int batchInsert(@Param("list") List<MUserRole> list);
}