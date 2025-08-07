package com.haohaodayouxi.manage.service;

import com.haohaodayouxi.common.core.model.vo.keyValue.LabelValueVO;
import com.haohaodayouxi.manage.model.db.MMenuApi;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * MMenuApiService
 *
 * @author TONE
 * @date 2025/3/1
 */
public interface MMenuApiService extends IService<MMenuApi>{

    int updateBatchSelective(List<MMenuApi> list);

    int batchInsert(List<MMenuApi> list);

    int batchInsert(Long menuId, List<Long> apiIds);

    void deleteByMenuId(Long menuId);

    List<LabelValueVO<String, Long>> getApiByMenuId(Long menuId);

    List<Long> getMenuApiIdsByMenuId(Long menuId);
}
