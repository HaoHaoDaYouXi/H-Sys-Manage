package com.haohaodayouxi.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.haohaodayouxi.manage.model.db.SUser;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * SUserMapper
 *
 * @author TONE
 * @date 2024/12/8
 */
public interface SUserMapper extends BaseMapper<SUser> {
    int updateBatch(List<SUser> list);

    int updateBatchSelective(List<SUser> list);

    int batchInsert(@Param("list") List<SUser> list);
}