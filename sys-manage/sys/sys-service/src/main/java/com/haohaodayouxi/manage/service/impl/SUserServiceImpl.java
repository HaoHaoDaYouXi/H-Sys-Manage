package com.haohaodayouxi.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haohaodayouxi.common.core.constants.CurrentUserContextHolder;
import com.haohaodayouxi.common.core.model.vo.page.PageBaseVO;
import com.haohaodayouxi.common.util.algorithm.md5.Md5Util;
import com.haohaodayouxi.common.util.business.IdUtil;
import com.haohaodayouxi.common.util.enums.TrueFalseEnum;
import com.haohaodayouxi.manage.mapper.SUserMapper;
import com.haohaodayouxi.manage.model.bo.login.LoginCacheBO;
import com.haohaodayouxi.manage.model.bo.user.UserRoleBO;
import com.haohaodayouxi.manage.model.db.MUserRole;
import com.haohaodayouxi.manage.model.db.SApi;
import com.haohaodayouxi.manage.model.db.SUser;
import com.haohaodayouxi.manage.model.req.user.SUserAddOrUpdReq;
import com.haohaodayouxi.manage.model.req.user.SUserPageListReq;
import com.haohaodayouxi.manage.model.res.user.SUserDetailRes;
import com.haohaodayouxi.manage.service.MUserRoleService;
import com.haohaodayouxi.manage.service.SUserService;
import com.haohaodayouxi.manage.utils.login.LoginCacheUtil;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    public PageBaseVO<SUser> pageList(SUserPageListReq req) {
        Page<SUser> page = new Page<>(req.getPageNum(), req.getPageSize());
        baseMapper.selectPage(page, new LambdaQueryWrapper<SUser>()
                .eq(ObjectUtils.isNotEmpty(req.getAccount()), SUser::getAccount, req.getAccount())
                .eq(ObjectUtils.isNotEmpty(req.getUserName()), SUser::getUserName, req.getUserName())
                .orderByDesc(List.of(SUser::getUpdateTime, SUser::getUserId)));
        if (ObjectUtils.isEmpty(page.getRecords())) {
            return new PageBaseVO<>(new ArrayList<>(), 0L);
        }
        return new PageBaseVO<>(page.getRecords(), page.getTotal());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOrUpd(SUserAddOrUpdReq req) {
        LoginCacheBO bo = (LoginCacheBO) CurrentUserContextHolder.get();
        Date now = new Date();
        SUser user = SUser.builder()
                .userId(req.getUserId())
                .account(req.getAccount())
                .userName(req.getUserName())
                .userAvatar(req.getUserAvatar())
                .userContact(req.getUserContact())
                .remarks(req.getRemarks())
                .multipleStatus(req.getMultipleStatus())
                .updateUid(bo.getUserLoginCacheBO().getUserId())
                .updateTime(now)
                .build();
        boolean add = ObjectUtils.isEmpty(req.getUserId());
        if (add) {
            user.setUserCode(IdUtil.getUUID());
            user.setPwd(Md5Util.encryptPWD(req.getPwd(), user.getUserCode()));
            user.setCreateUid(bo.getUserLoginCacheBO().getUserId());
            user.setCreateTime(now);
            user.setVersion(1L);
            user.setDelStatus(TrueFalseEnum.FALSE.getCode());
        }
        baseMapper.insertOrUpdate(user);
        if (!add) {
            userRoleService.update(new LambdaUpdateWrapper<MUserRole>()
                    .set(MUserRole::getDelStatus, TrueFalseEnum.TRUE.getCode())
                    .set(MUserRole::getUpdateUid, bo.getUserLoginCacheBO().getUserId())
                    .set(MUserRole::getUpdateTime, now)
                    .eq(MUserRole::getDelStatus, TrueFalseEnum.FALSE.getCode())
                    .eq(MUserRole::getUserId, bo.getUserLoginCacheBO().getUserId()));
        }
        if (ObjectUtils.isNotEmpty(req.getRoleIds())) {
            userRoleService.batchInsert(req.getRoleIds().stream()
                    .map(m -> MUserRole.builder()
                            .userId(user.getUserId())
                            .roleId(m)
                            .useStatus(TrueFalseEnum.FALSE.getCode())
                            .createUid(bo.getUserLoginCacheBO().getUserId())
                            .updateUid(bo.getUserLoginCacheBO().getUserId())
                            .createTime(now)
                            .updateTime(now)
                            .version(1L)
                            .delStatus(TrueFalseEnum.FALSE.getCode())
                            .build())
                    .toList());
        }
    }

    @Override
    public SUserDetailRes detail(Long id) {
        SUser user = baseMapper.selectOne(new LambdaQueryWrapper<SUser>().eq(SUser::getUserId, id));
        if (ObjectUtils.isEmpty(user)) {
            return null;
        }
        List<MUserRole> userRoleList = userRoleService.list(new LambdaQueryWrapper<MUserRole>()
                .select(MUserRole::getRoleId)
                .eq(MUserRole::getDelStatus, TrueFalseEnum.FALSE.getCode())
                .eq(MUserRole::getUserId, id));
        List<Long> roleIds = new ArrayList<>();
        if (ObjectUtils.isNotEmpty(userRoleList)) {
            roleIds = userRoleList.stream().map(MUserRole::getRoleId).toList();
        }
        return SUserDetailRes.builder()
                .userId(user.getUserId())
                .account(user.getAccount())
                .userName(user.getUserName())
                .userAvatar(user.getUserAvatar())
                .userContact(user.getUserContact())
                .remarks(user.getRemarks())
                .multipleStatus(user.getMultipleStatus())
                .roleIds(roleIds)
                .build();
    }

    @Override
    public void batchDel(List<Long> ids) {
        LoginCacheBO bo = (LoginCacheBO) CurrentUserContextHolder.get();
        baseMapper.update(new LambdaUpdateWrapper<SUser>()
                .set(SUser::getDelStatus, TrueFalseEnum.TRUE.getCode())
                .set(SUser::getUpdateUid, bo.getUserLoginCacheBO().getUserId())
                .set(SUser::getUpdateTime, new Date())
                .eq(SUser::getDelStatus, TrueFalseEnum.FALSE.getCode())
                .in(SUser::getUserId, ids)
        );
    }
}
