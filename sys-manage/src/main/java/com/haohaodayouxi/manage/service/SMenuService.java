package com.haohaodayouxi.manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
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

    int updateBatch(List<SMenu> list);

    int updateBatchSelective(List<SMenu> list);

    int batchInsert(List<SMenu> list);

    List<SMenuListRes> listByParent(SMenuListReq req);

    void addOrUpd(SMenuAddOrUpdReq req);

    void batchDel(List<Long> ids);
}
