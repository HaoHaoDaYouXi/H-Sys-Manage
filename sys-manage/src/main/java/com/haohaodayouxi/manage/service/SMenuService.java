package com.haohaodayouxi.manage.service;

import java.util.List;
import com.haohaodayouxi.manage.model.db.SMenu;
import com.baomidou.mybatisplus.extension.service.IService;
    /**
 * SMenuService
 *
 * @author TONE
 * @date 2024/12/8
 */
public interface SMenuService extends IService<SMenu>{


    int updateBatch(List<SMenu> list);

    int updateBatchSelective(List<SMenu> list);

    int batchInsert(List<SMenu> list);

}
