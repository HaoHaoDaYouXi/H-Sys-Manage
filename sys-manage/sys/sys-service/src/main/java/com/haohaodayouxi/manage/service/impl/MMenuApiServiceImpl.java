package com.haohaodayouxi.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haohaodayouxi.common.core.constants.CurrentUserContextHolder;
import com.haohaodayouxi.common.core.model.vo.keyValue.LabelValueVO;
import com.haohaodayouxi.common.util.enums.TrueFalseEnum;
import com.haohaodayouxi.manage.mapper.MMenuApiMapper;
import com.haohaodayouxi.manage.model.bo.login.LoginCacheBO;
import com.haohaodayouxi.manage.model.db.MMenuApi;
import com.haohaodayouxi.manage.model.db.SApi;
import com.haohaodayouxi.manage.service.MMenuApiService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * MMenuApiServiceImpl
 *
 * @author TONE
 * @date 2025/3/1
 */
@Service
public class MMenuApiServiceImpl extends ServiceImpl<MMenuApiMapper, MMenuApi> implements MMenuApiService {

    @Override
    public int updateBatchSelective(List<MMenuApi> list) {
        return baseMapper.updateBatchSelective(list);
    }

    @Override
    public int batchInsert(List<MMenuApi> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int batchInsert(Long menuId, List<Long> apiIds) {
        LoginCacheBO bo = (LoginCacheBO) CurrentUserContextHolder.get();
        deleteByMenuId(menuId);
        Date now = new Date();
        return baseMapper.batchInsert(apiIds.stream().map(m-> MMenuApi.builder().menuId(menuId).apiId(m)
                .version(1L).createUid(bo.getUserLoginCacheBO().getUserId()).createTime(now).updateUid(bo.getUserLoginCacheBO().getUserId()).updateTime(now).delStatus(TrueFalseEnum.FALSE.getCode()).build())
                .collect(Collectors.toList()));
    }

    @Override
    public void deleteByMenuId(Long menuId) {
        baseMapper.update(new LambdaUpdateWrapper<MMenuApi>()
                .set(MMenuApi::getDelStatus, TrueFalseEnum.TRUE.getCode())
                .set(MMenuApi::getUpdateTime, new Date())
                .eq(MMenuApi::getDelStatus, TrueFalseEnum.FALSE.getCode())
                .in(MMenuApi::getMenuId, menuId)
        );
    }

    @Override
    public List<Long> getMenuApiIdsByMenuId(Long menuId) {
        List<LabelValueVO<String, Long>> menuApiByMenuId = baseMapper.getMenuApiByMenuId(menuId);
        if (menuApiByMenuId.isEmpty()) {
            return new ArrayList<>();
        }
        return menuApiByMenuId.stream().map(LabelValueVO::getValue).collect(Collectors.toList());
    }

    @Override
    public List<LabelValueVO<String, Long>> getApiByMenuId(Long menuId) {
        return baseMapper.getMenuApiByMenuId(menuId);
    }
}
