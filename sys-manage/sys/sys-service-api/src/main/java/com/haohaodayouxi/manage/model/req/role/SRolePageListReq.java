package com.haohaodayouxi.manage.model.req.role;

import com.haohaodayouxi.common.core.model.req.page.PageBaseReq;
import lombok.*;

import java.io.Serial;

/**
 * SRolePageListReq
 *
 * @author TONE
 * @date 2025/3/5
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SRolePageListReq extends PageBaseReq {

    @Serial
    private static final long serialVersionUID = 7608986468487054179L;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色类型(取配置表code=11)
     */
    private String roleType;
}
