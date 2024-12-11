package com.haohaodayouxi.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.haohaodayouxi.manage.model.bo.user.UserRoleBO;
import com.haohaodayouxi.manage.model.db.MUserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    List<UserRoleBO> selectUserRoleList(@Param("userId") Long userId);
}
