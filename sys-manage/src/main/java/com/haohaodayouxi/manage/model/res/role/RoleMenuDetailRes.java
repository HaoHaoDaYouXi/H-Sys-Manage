package com.haohaodayouxi.manage.model.res.role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * RoleMenuDetailRes
 *
 * @author TONE
 * @date 2025/3/25
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleMenuDetailRes implements Serializable {
    @Serial
    private static final long serialVersionUID = 5180249239442837038L;


    private Long roleId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色类型(取配置表code=11)
     */
    private String roleType;

    /**
     * 菜单权限
     */
    private List<Long> menuIds;
}
