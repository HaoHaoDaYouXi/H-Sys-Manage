package com.haohaodayouxi.manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.haohaodayouxi.common.core.model.vo.keyValue.LabelValueVO;
import com.haohaodayouxi.manage.model.db.SMenu;
import com.haohaodayouxi.manage.model.req.menu.SMenuAddOrUpdReq;
import com.haohaodayouxi.manage.model.req.menu.SMenuListReq;
import com.haohaodayouxi.manage.model.res.menu.SMenuListRes;

import java.util.List;

/**
 * SMenuService
 *
 * @author TONE
 * @date 2024/12/8
 */
public interface SMenuService extends IService<SMenu> {

    List<SMenuListRes> listByParent(SMenuListReq req);

    List<LabelValueVO<String, Long>> labelValueByParent(Long parentId);

    void addOrUpd(SMenuAddOrUpdReq req);

    void batchDel(List<Long> ids);
}
