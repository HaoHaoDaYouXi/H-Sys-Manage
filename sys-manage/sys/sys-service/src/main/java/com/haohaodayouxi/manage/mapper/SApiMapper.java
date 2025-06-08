package com.haohaodayouxi.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.haohaodayouxi.manage.model.db.SApi;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * SApiMapper
 *
 * @author TONE
 * @date 2025/3/1
 */
public interface SApiMapper extends BaseMapper<SApi> {
    int updateBatchSelective(@Param("list") List<SApi> list);

    int batchInsert(@Param("list") List<SApi> list);
}
