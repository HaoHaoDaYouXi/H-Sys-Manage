package com.haohaodayouxi.manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.haohaodayouxi.manage.model.bo.user.UserRoleBO;
import com.haohaodayouxi.manage.model.db.MUserRole;

import java.util.List;

/**
 * MUserRoleService
 *
 * @author TONE
 * @date 2024/12/8
 */
public interface MUserRoleService extends IService<MUserRole> {

    int updateBatch(List<MUserRole> list);

    int updateBatchSelective(List<MUserRole> list);

    int batchInsert(List<MUserRole> list);

    List<UserRoleBO> selectUserRoleList(Long userId);

}
