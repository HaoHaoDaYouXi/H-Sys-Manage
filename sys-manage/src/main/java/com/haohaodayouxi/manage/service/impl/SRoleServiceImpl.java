package com.haohaodayouxi.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haohaodayouxi.common.core.constants.CurrentUserContextHolder;
import com.haohaodayouxi.common.core.model.vo.page.PageBaseVO;
import com.haohaodayouxi.common.util.constants.StringConstant;
import com.haohaodayouxi.common.util.enums.TrueFalseEnum;
import com.haohaodayouxi.manage.constants.enums.role.RoleTypeEnum;
import com.haohaodayouxi.manage.mapper.SRoleMapper;
import com.haohaodayouxi.manage.model.bo.login.LoginCacheBO;
import com.haohaodayouxi.manage.model.bo.param.SParamBO;
import com.haohaodayouxi.manage.model.db.SRole;
import com.haohaodayouxi.manage.model.req.param.SParamReq;
import com.haohaodayouxi.manage.model.req.role.SRoleAddOrUpdReq;
import com.haohaodayouxi.manage.model.req.role.SRolePageListReq;
import com.haohaodayouxi.manage.model.res.role.SRolePageListRes;
import com.haohaodayouxi.manage.service.SParamService;
import com.haohaodayouxi.manage.service.SRoleService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * SRoleServiceImpl
 *
 * @author TONE
 * @date 2024/12/8
 */
@Service
public class SRoleServiceImpl extends ServiceImpl<SRoleMapper, SRole> implements SRoleService {
    @Resource
    private SParamService paramService;

    @Override
    public int updateBatch(List<SRole> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int updateBatchSelective(List<SRole> list) {
        return baseMapper.updateBatchSelective(list);
    }

    @Override
    public int batchInsert(List<SRole> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public PageBaseVO<SRolePageListRes> pageList(SRolePageListReq req) {
        Page<SRole> page = new Page<>(req.getPageNum(), req.getPageSize());
        baseMapper.selectPage(page, new LambdaQueryWrapper<SRole>()
                .eq(ObjectUtils.isNotEmpty(req.getRoleName()), SRole::getRoleName, req.getRoleName())
                .eq(ObjectUtils.isNotEmpty(req.getRoleType()), SRole::getRoleType, req.getRoleType())
                .orderByDesc(SRole::getUpdateTime)
        );
        if (ObjectUtils.isEmpty(page.getRecords())) {
            return new PageBaseVO<>(new ArrayList<>(), 0L);
        }
        List<SParamBO> paramBOS = paramService.getByCache(SParamReq.builder().paramCodes(Arrays.stream(RoleTypeEnum.values()).map(RoleTypeEnum::getCode).collect(Collectors.joining(StringConstant.EN_COMMA))).build());
        Map<String, String> paramMap = paramBOS.stream().collect(Collectors.toMap(SParamBO::getParamValue, SParamBO::getParamName, (v1, v2) -> v2));
        return new PageBaseVO<>(page.getRecords().stream().map(m -> SRolePageListRes.builder().roleId(m.getRoleId()).roleName(m.getRoleName()).roleType(m.getRoleType()).roleTypeStr(paramMap.getOrDefault(m.getRoleType(), RoleTypeEnum.getByCode(m.getRoleType()).getName())).updateTime(m.getUpdateTime()).build()).toList(), page.getTotal());
    }

    @Override
    public void addOrUpd(SRoleAddOrUpdReq req) {
        SRole role = SRole.builder()
                .roleId(req.getRoleId())
                .roleName(req.getRoleName())
                .roleType(req.getRoleType())
                .build();
        LoginCacheBO bo = (LoginCacheBO) CurrentUserContextHolder.get();
        role.setUpdateUid(bo.getUserLoginCacheBO().getUserId());
        role.setUpdateTime(new Date());
        if (ObjectUtils.isEmpty(req.getRoleId())) {
            role.setCreateUid(bo.getUserLoginCacheBO().getUserId());
            role.setCreateTime(role.getUpdateTime());
            baseMapper.insert(role);
        } else {
            baseMapper.updateById(role);
        }
    }

    @Override
    public void batchDel(List<Long> ids) {
        baseMapper.update(new LambdaUpdateWrapper<SRole>()
                .set(SRole::getDelStatus, TrueFalseEnum.TRUE.getCode())
                .set(SRole::getUpdateTime, new Date())
                .eq(SRole::getDelStatus, TrueFalseEnum.FALSE.getCode())
                .in(SRole::getRoleId, ids)
        );
    }
}
