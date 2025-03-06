package com.haohaodayouxi.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haohaodayouxi.common.util.constants.StringConstant;
import com.haohaodayouxi.manage.constants.SysConstants;
import com.haohaodayouxi.manage.constants.enums.menu.MenuTypeEnum;
import com.haohaodayouxi.manage.mapper.SMenuMapper;
import com.haohaodayouxi.manage.model.bo.param.SParamBO;
import com.haohaodayouxi.manage.model.db.SMenu;
import com.haohaodayouxi.manage.model.req.menu.SMenuListReq;
import com.haohaodayouxi.manage.model.req.param.SParamReq;
import com.haohaodayouxi.manage.model.res.menu.SMenuListRes;
import com.haohaodayouxi.manage.service.SMenuService;
import com.haohaodayouxi.manage.service.SParamService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * SMenuServiceImpl
 *
 * @author TONE
 * @date 2024/12/8
 */
@Service
public class SMenuServiceImpl extends ServiceImpl<SMenuMapper, SMenu> implements SMenuService {
    @Resource
    private SParamService paramService;

    @Override
    public int updateBatch(List<SMenu> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int updateBatchSelective(List<SMenu> list) {
        return baseMapper.updateBatchSelective(list);
    }

    @Override
    public int batchInsert(List<SMenu> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public List<SMenuListRes> listByParent(SMenuListReq req) {
        if (ObjectUtils.isEmpty(req.getMenuParentId())) {
            req.setMenuParentId(SysConstants.TOP_LEVEL_ID);
        }
        List<SMenu> data = baseMapper.selectList(new LambdaQueryWrapper<SMenu>()
                .eq(SMenu::getMenuParentId, req.getMenuParentId())
                .eq(ObjectUtils.isNotEmpty(req.getMenuType()), SMenu::getMenuType, req.getMenuType())
                .eq(ObjectUtils.isNotEmpty(req.getMenuName()), SMenu::getMenuName, req.getMenuName())
                .eq(ObjectUtils.isNotEmpty(req.getDisabled()), SMenu::getDisabled, req.getDisabled())
                .orderByDesc(SMenu::getShowOrder)
                .orderByDesc(SMenu::getUpdateTime)
        );
        if (ObjectUtils.isEmpty(data)) {
            return new ArrayList<>();
        }
        List<SParamBO> paramBOS = paramService.getByCache(SParamReq.builder().paramCodes(Arrays.stream(MenuTypeEnum.values()).map(m -> m.getCode().toString()).collect(Collectors.joining(StringConstant.EN_COMMA))).build());
        Map<Integer, String> paramMap = paramBOS.stream().collect(Collectors.toMap(k -> Integer.valueOf(k.getParamValue()), SParamBO::getParamName, (v1, v2) -> v2));
        return data.stream().map(m -> SMenuListRes.builder().menuId(m.getMenuId()).menuParentId(m.getMenuParentId()).menuType(m.getMenuType()).menuTypeStr(paramMap.getOrDefault(m.getMenuType(), MenuTypeEnum.getByValue(m.getMenuType()).getName())).menuName(m.getMenuName()).menuIcon(m.getMenuIcon()).menuKey(m.getMenuKey()).menuComponent(m.getMenuComponent()).disabled(m.getDisabled()).updateTime(m.getUpdateTime()).build()).toList();
    }
}
