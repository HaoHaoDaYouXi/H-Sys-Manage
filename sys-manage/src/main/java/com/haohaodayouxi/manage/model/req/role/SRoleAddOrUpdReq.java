package com.haohaodayouxi.manage.model.req.role;

import com.haohaodayouxi.common.core.interfaces.AddValid;
import com.haohaodayouxi.common.core.interfaces.UpdValid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 角色表
 *
 * @author TONE
 * @date 2024/12/8
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SRoleAddOrUpdReq implements Serializable {
    @Serial
    private static final long serialVersionUID = -6170824740245677473L;
    /**
     * ID
     */
    @NotNull(groups = UpdValid.class)
    private Long roleId;

    /**
     * 角色名称
     */
    @NotBlank(groups = AddValid.class)
    private String roleName;

    /**
     * 角色类型(取配置表code=11)
     */
    @NotBlank(groups = AddValid.class)
    private String roleType;

    /**
     * 菜单权限
     */
    private List<Long> menuIds;
}
