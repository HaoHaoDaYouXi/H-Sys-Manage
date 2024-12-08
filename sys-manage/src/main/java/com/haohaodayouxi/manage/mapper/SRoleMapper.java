package com.haohaodayouxi.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.haohaodayouxi.manage.model.db.SRole;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * SRoleMapper
 *
 * @author TONE
 * @date 2024/12/8
 */
public interface SRoleMapper extends BaseMapper<SRole> {
    int updateBatch(List<SRole> list);

    int updateBatchSelective(List<SRole> list);

    int batchInsert(@Param("list") List<SRole> list);
}