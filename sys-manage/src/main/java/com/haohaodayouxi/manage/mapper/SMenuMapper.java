package com.haohaodayouxi.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.haohaodayouxi.manage.model.db.SMenu;
import com.haohaodayouxi.manage.model.req.menu.SMenuListReq;
import com.haohaodayouxi.manage.model.res.menu.MenuTreeRes;
import com.haohaodayouxi.manage.model.res.menu.SMenuListRes;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * SMenuMapper
 *
 * @author TONE
 * @date 2025/3/20
 */
public interface SMenuMapper extends BaseMapper<SMenu> {

    List<SMenuListRes> listByParent(SMenuListReq req);

    List<MenuTreeRes> labelValueByParent(Long parentId);

    void changeParentPath(@Param("oldPath") String oldPath, @Param("newPath") String newPath);

    boolean sameCheck(@Param("menuId") Long menuId, @Param("menuParentId") Long menuParentId, @Param("menuName") String menuName, @Param("menuKey") String menuKey);
}
