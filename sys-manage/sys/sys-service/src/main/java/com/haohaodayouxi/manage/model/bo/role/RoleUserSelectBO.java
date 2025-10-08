package com.haohaodayouxi.manage.model.bo.role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * RoleUserSelectBO
 *
 * @author TONE
 * @date 2025/10/8
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleUserSelectBO implements Serializable {
    @Serial
    private static final long serialVersionUID = 6645968934132348865L;

    private Long roleId;
    private String roleName;
    private String roleType;
    private Long userId;


}
