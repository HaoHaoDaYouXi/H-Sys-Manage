package com.haohaodayouxi.manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.haohaodayouxi.manage.model.bo.param.SParamBO;
import com.haohaodayouxi.manage.model.db.SParam;
import com.haohaodayouxi.manage.model.req.param.SParamAddOrUpdReq;
import com.haohaodayouxi.manage.model.req.param.SParamReq;

import java.util.List;

/**
 * SParamService
 *
 * @author TONE
 * @date 2024/12/8
 */
public interface SParamService extends IService<SParam> {

    int updateBatch(List<SParam> list);

    int updateBatchSelective(List<SParam> list);

    int batchInsert(List<SParam> list);

    /**
     * 根据数据查询
     *
     * @param req
     * @return
     */
    List<SParamBO> getByReq(SParamReq req);

    /**
     * 根据缓存查询
     *
     * @param req
     * @return
     */
    List<SParamBO> getByCache(SParamReq req);

    void addOrUpd(SParamAddOrUpdReq req);

}
