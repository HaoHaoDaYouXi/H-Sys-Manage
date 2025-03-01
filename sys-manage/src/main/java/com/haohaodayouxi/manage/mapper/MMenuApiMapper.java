package com.haohaodayouxi.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.haohaodayouxi.manage.model.db.MMenuApi;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * MMenuApiMapper
 *
 * @author TONE
 * @date 2025/3/1
 */
public interface MMenuApiMapper extends BaseMapper<MMenuApi> {
    int updateBatchSelective(@Param("list") List<MMenuApi> list);

    int batchInsert(@Param("list") List<MMenuApi> list);
}