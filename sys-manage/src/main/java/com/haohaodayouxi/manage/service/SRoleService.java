package com.haohaodayouxi.manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.haohaodayouxi.common.core.model.vo.page.PageBaseVO;
import com.haohaodayouxi.manage.model.db.SRole;
import com.haohaodayouxi.manage.model.req.role.SRoleAddOrUpdReq;
import com.haohaodayouxi.manage.model.req.role.SRolePageListReq;
import com.haohaodayouxi.manage.model.res.role.SRolePageListRes;

import java.util.List;

/**
 * SRoleService
 *
 * @author TONE
 * @date 2024/12/8
 */
public interface SRoleService extends IService<SRole> {

    int updateBatch(List<SRole> list);

    int updateBatchSelective(List<SRole> list);

    int batchInsert(List<SRole> list);

    PageBaseVO<SRolePageListRes> pageList(SRolePageListReq req);

    void addOrUpd(SRoleAddOrUpdReq req);

    void batchDel(List<Long> ids);

}
