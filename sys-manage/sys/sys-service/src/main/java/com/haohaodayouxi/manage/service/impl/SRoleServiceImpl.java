package com.haohaodayouxi.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.haohaodayouxi.common.core.constants.CurrentUserContextHolder;
import com.haohaodayouxi.common.core.exception.BusinessException;
import com.haohaodayouxi.common.core.model.vo.page.PageBaseVO;
import com.haohaodayouxi.common.util.constants.StringConstant;
import com.haohaodayouxi.common.util.enums.TrueFalseEnum;
import com.haohaodayouxi.manage.constants.enums.role.RoleTypeEnum;
import com.haohaodayouxi.manage.mapper.MRoleMenuApiMapper;
import com.haohaodayouxi.manage.mapper.SRoleMapper;
import com.haohaodayouxi.manage.model.bo.login.LoginCacheBO;
import com.haohaodayouxi.manage.model.bo.param.SParamBO;
import com.haohaodayouxi.manage.model.bo.role.RoleUserSelectBO;
import com.haohaodayouxi.manage.model.db.MRoleMenu;
import com.haohaodayouxi.manage.model.db.MRoleMenuApi;
import com.haohaodayouxi.manage.model.db.SRole;
import com.haohaodayouxi.manage.model.req.param.SParamReq;
import com.haohaodayouxi.manage.model.req.role.RoleUserSelectPageListReq;
import com.haohaodayouxi.manage.model.req.role.SRoleAddOrUpdReq;
import com.haohaodayouxi.manage.model.req.role.SRolePageListReq;
import com.haohaodayouxi.manage.model.res.role.RoleMenuDetailRes;
import com.haohaodayouxi.manage.model.res.role.SRolePageListRes;
import com.haohaodayouxi.manage.service.MRoleMenuService;
import com.haohaodayouxi.manage.service.SParamService;
import com.haohaodayouxi.manage.service.SRoleService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Service
public class SRoleServiceImpl extends ServiceImpl<SRoleMapper, SRole> implements SRoleService {
    @Resource
    private SParamService paramService;
    @Resource
    private MRoleMenuService roleMenuService;
    @Resource
    private MRoleMenuApiMapper roleMenuApiMapper;

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
        Date now = new Date();
        LoginCacheBO bo = (LoginCacheBO) CurrentUserContextHolder.get();
        role.setUpdateUid(bo.getUserLoginCacheBO().getUserId());
        role.setUpdateTime(now);
        if (ObjectUtils.isEmpty(req.getRoleId())) {
            role.setCreateUid(bo.getUserLoginCacheBO().getUserId());
            role.setCreateTime(role.getUpdateTime());
            baseMapper.insert(role);
            if (ObjectUtils.isNotEmpty(req.getMenuIds())) {
                roleMenuService.saveBatch(req.getMenuIds().stream().map(m -> MRoleMenu.builder()
                        .roleId(role.getRoleId())
                        .menuId(m)
                        .createUid(bo.getUserLoginCacheBO().getUserId())
                        .updateUid(bo.getUserLoginCacheBO().getUserId())
                        .createTime(now)
                        .updateTime(now)
                        .build()).toList());
            }
        } else {
            baseMapper.updateById(role);
            if (ObjectUtils.isNotEmpty(req.getMenuIds())) {
                List<MRoleMenu> list = roleMenuService.list(new LambdaQueryWrapper<MRoleMenu>()
                        .eq(MRoleMenu::getRoleId, role.getRoleId())
                        .eq(MRoleMenu::getDelStatus, TrueFalseEnum.FALSE.getCode()));
                if (ObjectUtils.isNotEmpty(list)) {
                    // 原有ID和新ID比较，是否有更新，在使用saveOrUpdate进行批量更新和新增
                    Map<Long, MRoleMenu> map = list.stream().collect(Collectors.toMap(MRoleMenu::getMenuId, m -> m));
                    List<MRoleMenu> addOrUpdList = new ArrayList<>(req.getMenuIds().stream().map(m -> {
                        MRoleMenu mr = MRoleMenu.builder()
                                .roleId(role.getRoleId())
                                .menuId(m)
                                .updateUid(bo.getUserLoginCacheBO().getUserId())
                                .updateTime(now)
                                .build();
                        if (map.containsKey(m)) {
                            mr.setId(map.get(m).getId());
                            mr.setVersion(map.get(m).getVersion() + 1);
                            map.remove(m);
                        } else {
                            mr.setCreateUid(bo.getUserLoginCacheBO().getUserId());
                            mr.setCreateTime(now);
                        }
                        return mr;
                    }).toList());
                    if (ObjectUtils.isNotEmpty(map)) {
                        map.forEach((k, v) -> {
                            v.setDelStatus(TrueFalseEnum.TRUE.getCode());
                            v.setUpdateUid(bo.getUserLoginCacheBO().getUserId());
                            v.setUpdateTime(role.getUpdateTime());
                            addOrUpdList.add(v);
                        });
                    }
                    roleMenuService.saveOrUpdateBatch(addOrUpdList);
                } else {
                    roleMenuService.saveBatch(req.getMenuIds().stream().map(m -> MRoleMenu.builder()
                            .roleId(role.getRoleId())
                            .menuId(m)
                            .createUid(bo.getUserLoginCacheBO().getUserId())
                            .updateUid(bo.getUserLoginCacheBO().getUserId())
                            .createTime(now)
                            .updateTime(now)
                            .build()).toList());
                }
            }
        }
        roleMenuApiMapper.update(new LambdaUpdateWrapper<MRoleMenuApi>()
                .set(MRoleMenuApi::getDelStatus, TrueFalseEnum.TRUE.getCode())
                .set(MRoleMenuApi::getUpdateUid, bo.getUserLoginCacheBO().getUserId())
                .set(MRoleMenuApi::getUpdateTime, now)
                .eq(MRoleMenuApi::getRoleId, role.getRoleId()).eq(MRoleMenuApi::getDelStatus, TrueFalseEnum.FALSE.getCode()));
        if (ObjectUtils.isNotEmpty(req.getMenuApiIds())) {
            roleMenuApiMapper.insert(req.getMenuApiIds().stream().map(m -> MRoleMenuApi.builder()
                    .roleId(role.getRoleId())
                    .menuApiId(m)
                    .createUid(bo.getUserLoginCacheBO().getUserId())
                    .updateUid(bo.getUserLoginCacheBO().getUserId())
                    .createTime(now)
                    .updateTime(now)
                    .version(1L)
                    .delStatus(TrueFalseEnum.FALSE.getCode())
                    .build()).toList());
        }
    }

    @Override
    public RoleMenuDetailRes getDetail(Long id) {
        SRole role = baseMapper.selectById(id);
        if (ObjectUtils.isEmpty(role)) {
            throw new BusinessException("角色数据有误");
        }
        List<Long> menuIds = roleMenuService.list(new LambdaQueryWrapper<MRoleMenu>()
                .eq(MRoleMenu::getRoleId, role.getRoleId())
                .eq(MRoleMenu::getDelStatus, TrueFalseEnum.FALSE.getCode())).stream().map(MRoleMenu::getMenuId).toList();
        return RoleMenuDetailRes.builder()
                .roleId(role.getRoleId())
                .roleName(role.getRoleName())
                .roleType(role.getRoleType())
                .menuIds(menuIds)
                .build();
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
