package com.haohaodayouxi.manage.model.bo.login;

import com.haohaodayouxi.manage.model.bo.user.UserRoleBO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * UserLinkCacheBO
 *
 * @author TONE
 * @date 2024/12/11
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLinkLoginCacheBO implements Serializable {
    @Serial
    private static final long serialVersionUID = 7811350907994000452L;

    /**
     * 用户角色关联信息
     */
    private List<UserRoleBO> userRoles;

}
