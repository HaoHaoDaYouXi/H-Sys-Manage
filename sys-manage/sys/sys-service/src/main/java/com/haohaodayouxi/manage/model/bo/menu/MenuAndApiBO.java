package com.haohaodayouxi.manage.model.bo.menu;

import com.haohaodayouxi.manage.model.db.SApi;
import com.haohaodayouxi.manage.model.db.SMenu;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 菜单和接口BO
 *
 * @author TONE
 * @date 2025/7/7
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuAndApiBO implements Serializable {
    @Serial
    private static final long serialVersionUID = -2793068601864863028L;
    /**
     * 菜单
     */
    private SMenu menu;
    /**
     * 接口
     */
    private List<SApi> apiList;
}
