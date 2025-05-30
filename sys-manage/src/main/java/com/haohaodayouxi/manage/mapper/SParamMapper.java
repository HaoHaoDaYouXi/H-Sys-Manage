package com.haohaodayouxi.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.haohaodayouxi.manage.model.bo.param.SParamBO;
import com.haohaodayouxi.manage.model.db.SParam;
import com.haohaodayouxi.manage.model.req.param.SParamReq;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * SParamMapper
 *
 * @author TONE
 * @date 2025/5/13
 */
public interface SParamMapper extends BaseMapper<SParam> {

    int updateBatch(@Param("list") List<SParam> list);

    int updateBatchSelective(@Param("list") List<SParam> list);

    int batchInsert(@Param("list") List<SParam> list);

    List<SParamBO> getByReq(SParamReq req);

    Long getMaxParamCode(@Param("paramParentCode") Long paramParentCode);

    boolean sameCheck(@Param("paramCode") Long paramCode, @Param("paramParentCode") Long paramParentCode, @Param("paramName") String paramName);
}
