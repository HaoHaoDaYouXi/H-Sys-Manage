package com.haohaodayouxi.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haohaodayouxi.common.core.constants.CurrentUserContextHolder;
import com.haohaodayouxi.common.util.enums.TrueFalseEnum;
import com.haohaodayouxi.manage.mapper.SUserMapper;
import com.haohaodayouxi.manage.model.bo.login.LoginCacheBO;
import com.haohaodayouxi.manage.model.bo.user.UserRoleBO;
import com.haohaodayouxi.manage.model.db.MUserRole;
import com.haohaodayouxi.manage.model.db.SUser;
import com.haohaodayouxi.manage.model.req.user.SUserAddOrUpdReq;
import com.haohaodayouxi.manage.model.req.user.SUserPageListReq;
import com.haohaodayouxi.manage.model.res.user.SUserDetailRes;
import com.haohaodayouxi.manage.service.MUserRoleService;
import com.haohaodayouxi.manage.service.SUserService;
import com.haohaodayouxi.manage.utils.login.LoginCacheUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * SUserServiceImpl
 *
 * @author TONE
 * @date 2024/12/8
 */
@Service
public class SUserServiceImpl extends ServiceImpl<SUserMapper, SUser> implements SUserService {

    @Resource
    private LoginCacheUtil loginCacheUtil;
    @Resource
    private MUserRoleService userRoleService;

    @Override
    public int updateBatch(List<SUser> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int updateBatchSelective(List<SUser> list) {
        return baseMapper.updateBatchSelective(list);
    }

    @Override
    public int batchInsert(List<SUser> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public void changeUseRole(Long id) {
        LoginCacheBO bo = (LoginCacheBO) CurrentUserContextHolder.get();
        Date now = new Date();
        userRoleService.update(new LambdaUpdateWrapper<MUserRole>()
                .set(MUserRole::getUseStatus, TrueFalseEnum.FALSE.getCode())
                .set(MUserRole::getUpdateUid, bo.getUserLoginCacheBO().getUserId())
                .set(MUserRole::getUpdateTime, now)
                .eq(MUserRole::getDelStatus, TrueFalseEnum.FALSE.getCode())
                .eq(MUserRole::getUseStatus, TrueFalseEnum.TRUE.getCode())
                .eq(MUserRole::getUserId, bo.getUserLoginCacheBO().getUserId())
        );
        userRoleService.update(new LambdaUpdateWrapper<MUserRole>()
                .set(MUserRole::getUseStatus, TrueFalseEnum.TRUE.getCode())
                .set(MUserRole::getUpdateUid, bo.getUserLoginCacheBO().getUserId())
                .set(MUserRole::getUpdateTime, now)
                .eq(MUserRole::getDelStatus, TrueFalseEnum.FALSE.getCode())
                .eq(MUserRole::getId, id)
                .eq(MUserRole::getUserId, bo.getUserLoginCacheBO().getUserId())
        );
        bo.getUserLinkLoginCacheBO().getUserRoles().forEach(f -> {
            if (f.getId().equals(id)) {
                f.setUseStatus(TrueFalseEnum.TRUE.getCode());
            } else {
                f.setUseStatus(TrueFalseEnum.FALSE.getCode());
            }
        });
        bo.getUserLinkLoginCacheBO().getUserRoles().sort(Comparator.comparing(UserRoleBO::getUseStatus).reversed());
        loginCacheUtil.setLoginCache(bo);
    }

    @Override
    public List<SUser> pageList(SUserPageListReq req) {
        return List.of();
    }

    @Override
    public void addOrUpd(SUserAddOrUpdReq req) {

    }

    @Override
    public SUserDetailRes detail(Long id) {
        return null;
    }

    @Override
    public void batchDel(List<Long> ids) {

    }
}
