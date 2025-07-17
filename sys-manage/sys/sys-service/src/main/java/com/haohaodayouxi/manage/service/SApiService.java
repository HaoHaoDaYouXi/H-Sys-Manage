package com.haohaodayouxi.manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.haohaodayouxi.common.core.model.req.page.PageBaseReq;
import com.haohaodayouxi.common.core.model.vo.page.PageBaseVO;
import com.haohaodayouxi.manage.model.db.SApi;
import com.haohaodayouxi.manage.model.req.api.SApiAddOrUpdReq;
import com.haohaodayouxi.manage.model.req.api.SApiPageListReq;

import java.util.List;

/**
 * SApiService
 *
 * @author TONE
 * @date 2025/3/1
 */
public interface SApiService extends IService<SApi> {

    int updateBatchSelective(List<SApi> list);

    int batchInsert(List<SApi> list);

    boolean checkRoleApi(Long roleId, String apiKey);

    PageBaseVO<String> getModuleList(PageBaseReq req);

    PageBaseVO<SApi> pageList(SApiPageListReq req);

    void addOrUpd(SApiAddOrUpdReq req);

    void batchDel(List<Long> ids);
}
