package com.haohaodayouxi.manage.model.bo.login;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * LoginCacheBO
 *
 * @author TONE
 * @date 2024/12/9
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginCacheBO implements Serializable {
    @Serial
    private static final long serialVersionUID = 4130641200545377831L;
    /**
     * token
     */
    private String hToken;
    /**
     * 用户登录缓存
     */
    private UserLoginCacheBO userLoginCacheBO;
    /**
     * 用户关联信息登录缓存
     */
    private UserLinkLoginCacheBO userLinkLoginCacheBO;
}
