package com.haohaodayouxi.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.haohaodayouxi.manage.model.bo.role.RoleUserSelectBO;
import com.haohaodayouxi.manage.model.db.SRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * SRoleMapper
 *
 * @author TONE
 * @date 2024/12/8
 */
public interface SRoleMapper extends BaseMapper<SRole> {

    int updateBatch(@Param("list") List<SRole> list);

    int updateBatchSelective(@Param("list") List<SRole> list);

    int batchInsert(@Param("list") List<SRole> list);
}
