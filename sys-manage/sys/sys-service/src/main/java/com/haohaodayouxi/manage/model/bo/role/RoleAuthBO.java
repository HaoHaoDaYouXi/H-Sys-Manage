package com.haohaodayouxi.manage.model.bo.role;

import com.haohaodayouxi.manage.model.db.SMenu;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 角色权限
 *
 * @author TONE
 * @date 2025/7/7
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleAuthBO implements Serializable {
    @Serial
    private static final long serialVersionUID = -6868911393874958885L;
    /**
     * 菜单权限
     */
    private List<SMenu> menuList;

}
