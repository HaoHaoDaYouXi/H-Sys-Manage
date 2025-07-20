package com.haohaodayouxi.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haohaodayouxi.common.core.constants.CurrentUserContextHolder;
import com.haohaodayouxi.common.core.model.req.page.PageBaseReq;
import com.haohaodayouxi.common.core.model.vo.page.PageBaseVO;
import com.haohaodayouxi.common.util.enums.TrueFalseEnum;
import com.haohaodayouxi.manage.mapper.SApiMapper;
import com.haohaodayouxi.manage.model.bo.login.LoginCacheBO;
import com.haohaodayouxi.manage.model.db.SApi;
import com.haohaodayouxi.manage.model.req.api.SApiAddOrUpdReq;
import com.haohaodayouxi.manage.model.req.api.SApiPageListReq;
import com.haohaodayouxi.manage.service.SApiService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * SApiServiceImpl
 *
 * @author TONE
 * @date 2025/3/1
 */
@Service
public class SApiServiceImpl extends ServiceImpl<SApiMapper, SApi> implements SApiService {

    @Override
    public int updateBatchSelective(List<SApi> list) {
        return baseMapper.updateBatchSelective(list);
    }

    @Override
    public int batchInsert(List<SApi> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public boolean checkRoleApi(Long roleId, String apiKey) {
        // todo 角色 api权限 通过缓存 判断
        return baseMapper.checkRoleApi(roleId, apiKey);
    }

    @Override
    public PageBaseVO<String> getModuleList(PageBaseReq req) {
        Page<SApi> page = new Page<>(req.getPageNum(), req.getPageSize());
        List<SApi> data = baseMapper.selectList(page, new LambdaQueryWrapper<SApi>().select(SApi::getModuleName).groupBy(SApi::getModuleName));
        if (ObjectUtils.isEmpty(data)) {
            return new PageBaseVO<>(new ArrayList<>(), 0L);
        }
        return new PageBaseVO<>(data.stream().map(SApi::getModuleName).toList(), page.getTotal());
    }

    @Override
    public PageBaseVO<SApi> pageList(SApiPageListReq req) {
        Page<SApi> page = new Page<>(req.getPageNum(), req.getPageSize());
        baseMapper.selectPage(page, new LambdaQueryWrapper<SApi>()
                .eq(ObjectUtils.isNotEmpty(req.getModuleName()), SApi::getModuleName, req.getModuleName())
                .orderByDesc(List.of(SApi::getUpdateTime, SApi::getApiId))
        );
        if (ObjectUtils.isEmpty(page.getRecords())) {
            return new PageBaseVO<>(new ArrayList<>(), 0L);
        }
        return new PageBaseVO<>(page.getRecords(), page.getTotal());
    }

    @Override
    public void addOrUpd(SApiAddOrUpdReq req) {
        SApi api = SApi.builder()
                .apiId(req.getApiId())
                .moduleName(req.getModuleName())
                .apiName(req.getApiName())
                .apiType(req.getApiType())
                .apiKey(req.getApiKey())
                .urlType(req.getUrlType())
                .requestMethod(req.getRequestMethod())
                .openStatus(req.getOpenStatus())
                .build();
        LoginCacheBO bo = (LoginCacheBO) CurrentUserContextHolder.get();
        api.setUpdateUid(bo.getUserLoginCacheBO().getUserId());
        api.setUpdateTime(new Date());
        if (ObjectUtils.isEmpty(req.getApiId())) {
            api.setCreateUid(bo.getUserLoginCacheBO().getUserId());
            api.setCreateTime(api.getUpdateTime());
            baseMapper.insert(api);
        } else {
            baseMapper.updateById(api);
        }
    }

    @Override
    public void batchDel(List<Long> ids) {
        baseMapper.update(new LambdaUpdateWrapper<SApi>()
                .set(SApi::getDelStatus, TrueFalseEnum.TRUE.getCode())
                .set(SApi::getUpdateTime, new Date())
                .eq(SApi::getDelStatus, TrueFalseEnum.FALSE.getCode())
                .in(SApi::getApiId, ids)
        );
    }
}
