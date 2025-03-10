package com.haohaodayouxi.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.haohaodayouxi.manage.model.db.SMenu;
import com.haohaodayouxi.manage.model.req.menu.SMenuListReq;
import com.haohaodayouxi.manage.model.res.menu.SMenuListRes;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * SMenuMapper
 *
 * @author TONE
 * @date 2024/12/8
 */
public interface SMenuMapper extends BaseMapper<SMenu> {

    int updateBatch(List<SMenu> list);

    int updateBatchSelective(List<SMenu> list);

    int batchInsert(@Param("list") List<SMenu> list);

    List<SMenuListRes> listByParent(SMenuListReq req);
}
