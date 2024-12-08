package com.haohaodayouxi.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.haohaodayouxi.manage.model.db.SParam;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * SParamMapper
 *
 * @author TONE
 * @date 2024/12/8
 */
public interface SParamMapper extends BaseMapper<SParam> {
    int updateBatch(List<SParam> list);

    int updateBatchSelective(List<SParam> list);

    int batchInsert(@Param("list") List<SParam> list);
}