package com.haohaodayouxi.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haohaodayouxi.common.core.constants.CurrentUserContextHolder;
import com.haohaodayouxi.common.core.exception.BusinessException;
import com.haohaodayouxi.common.util.constants.StringConstant;
import com.haohaodayouxi.common.util.enums.TrueFalseEnum;
import com.haohaodayouxi.manage.constants.SysConstants;
import com.haohaodayouxi.manage.constants.enums.menu.MenuTypeEnum;
import com.haohaodayouxi.manage.mapper.SMenuMapper;
import com.haohaodayouxi.manage.model.bo.login.LoginCacheBO;
import com.haohaodayouxi.manage.model.bo.param.SParamBO;
import com.haohaodayouxi.manage.model.db.SMenu;
import com.haohaodayouxi.manage.model.req.menu.ChangeDisableReq;
import com.haohaodayouxi.manage.model.req.menu.SMenuAddOrUpdReq;
import com.haohaodayouxi.manage.model.req.menu.SMenuListReq;
import com.haohaodayouxi.manage.model.req.param.SParamReq;
import com.haohaodayouxi.manage.model.res.menu.MenuTreeRes;
import com.haohaodayouxi.manage.model.res.menu.SMenuListRes;
import com.haohaodayouxi.manage.service.SMenuService;
import com.haohaodayouxi.manage.service.SParamService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
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
    public List<SMenuListRes> listByParent(SMenuListReq req) {
        if (ObjectUtils.isEmpty(req.getMenuParentId())) {
            req.setMenuParentId(SysConstants.TOP_LEVEL_ID);
        }
        List<SMenuListRes> res = baseMapper.listByParent(req);
        if (ObjectUtils.isNotEmpty(res)) {
            List<SParamBO> paramBOS = paramService.getByCache(SParamReq.builder().paramCodes(Arrays.stream(MenuTypeEnum.values()).map(m -> m.getCode().toString()).collect(Collectors.joining(StringConstant.EN_COMMA))).build());
            Map<Integer, String> paramMap = paramBOS.stream().collect(Collectors.toMap(k -> Integer.valueOf(k.getParamValue()), SParamBO::getParamName, (v1, v2) -> v2));
            res.forEach(f -> f.setMenuTypeStr(paramMap.getOrDefault(f.getMenuType(), MenuTypeEnum.getByValue(f.getMenuType()).getName())));
        }
        return res;
    }

    @Override
    public List<MenuTreeRes> labelValueByParent(Long parentId) {
        if (ObjectUtils.isEmpty(parentId)) {
            parentId = SysConstants.TOP_LEVEL_ID;
        }
        return baseMapper.labelValueByParent(parentId);
    }

    @Override
    public void addOrUpd(SMenuAddOrUpdReq req) {
        SMenu menu = SMenu.builder()
                .menuId(req.getMenuId())
                .menuParentId(ObjectUtils.isEmpty(req.getMenuParentId()) ? SysConstants.TOP_LEVEL_ID : req.getMenuParentId())
                .menuType(req.getMenuType())
                .menuName(req.getMenuName())
                .menuIcon(req.getMenuIcon())
                .menuKey(req.getMenuKey())
                .activeMenu(req.getActiveMenu())
                .redirect(req.getRedirect())
                .menuComponent(req.getMenuComponent())
                .outUrl(req.getOutUrl())
                .showOrder(req.getShowOrder())
                .disabled(req.getDisabled())
                .hidden(req.getHidden())
                .cachedView(req.getCachedView())
                .breadcrumb(req.getBreadcrumb())
                .affix(req.getAffix())
                .alwaysShow(req.getAlwaysShow())
                .menuDescribe(req.getMenuDescribe())
                .build();
        boolean add = ObjectUtils.isEmpty(menu.getMenuId());
        if (!add && menu.getMenuId().equals(menu.getMenuParentId())) {
            throw new BusinessException("父级菜单不能是自身");
        }
        if (!menu.getMenuParentId().equals(SysConstants.TOP_LEVEL_ID)) {
            SMenu parent = baseMapper.selectById(menu.getMenuParentId());
            if (ObjectUtils.isEmpty(parent)) {
                throw new BusinessException("父级菜单数据错误，请重试");
            } else {
                menu.setMenuParentPath(splitParentPath(parent.getMenuParentPath(), parent.getMenuId()));
            }
        }
        if (baseMapper.sameCheck(menu.getMenuId(), menu.getMenuParentId(), menu.getMenuName(), menu.getMenuKey())) {
            throw new BusinessException("当前父级下菜单名称或菜单标识已存在");
        }
        LoginCacheBO bo = (LoginCacheBO) CurrentUserContextHolder.get();
        menu.setUpdateUid(bo.getUserLoginCacheBO().getUserId());
        menu.setUpdateTime(new Date());
        if (add) {
            menu.setCreateUid(bo.getUserLoginCacheBO().getUserId());
            menu.setCreateTime(menu.getUpdateTime());
            baseMapper.insert(menu);
        } else {
            SMenu old = baseMapper.selectById(menu.getMenuId());
            if (ObjectUtils.isEmpty(old)) {
                throw new BusinessException("菜单编号数据错误，请重试");
            }
            baseMapper.updateById(menu);
            if (!old.getMenuParentPath().equals(menu.getMenuParentPath())) {
                baseMapper.changeParentPath(splitParentPath(old.getMenuParentPath(), old.getMenuId()), splitParentPath(menu.getMenuParentPath(), menu.getMenuId()));
            }
        }
    }

    @Override
    public void changeDisable(ChangeDisableReq req) {
        baseMapper.update(new LambdaUpdateWrapper<SMenu>().set(SMenu::getDisabled, req.getDisabled()).eq(SMenu::getMenuId, req.getMenuId()));
    }

    private String splitParentPath(String parentPath, Long menuId) {
        return ObjectUtils.isEmpty(parentPath)
                ? menuId.toString()
                : parentPath + StringConstant.STRIKETHROUGH + menuId;
    }

    @Override
    public void batchDel(List<Long> ids) {
        baseMapper.update(new LambdaUpdateWrapper<SMenu>()
                .set(SMenu::getDelStatus, TrueFalseEnum.TRUE.getCode())
                .set(SMenu::getUpdateTime, new Date())
                .eq(SMenu::getDelStatus, TrueFalseEnum.FALSE.getCode())
                .in(SMenu::getMenuId, ids)
        );
    }
}
