package com.haohaodayouxi.manage.model.bo.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * UserRoleBO
 *
 * @author TONE
 * @date 2024/12/11
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleBO implements Serializable {
    @Serial
    private static final long serialVersionUID = 4630517535048159908L;

    /**
     * ID
     */
    private Long id;
    /**
     * 角色ID
     */
    private Long roleId;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 角色分类标识(取配置表type=2)
     */
    private String roleCode;
    /**
     * 当前使用状态 0-非使用 1-使用 默认0
     */
    private Integer useStatus;
}
