package com.haohaodayouxi.manage.model.res.role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * SMenuListRes
 *
 * @author TONE
 * @date 2025/3/5
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SRolePageListRes implements Serializable {
    @Serial
    private static final long serialVersionUID = 2265239520245564207L;


    private Long roleId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色类型(取配置表code=11)
     */
    private String roleType;
    private String roleTypeStr;

    /**
     * 修改时间
     */
    private Date updateTime;

}
