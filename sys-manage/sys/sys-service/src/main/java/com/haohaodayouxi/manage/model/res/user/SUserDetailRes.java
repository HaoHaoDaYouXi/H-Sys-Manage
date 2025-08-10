package com.haohaodayouxi.manage.model.res.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * SUserDetail
 *
 * @author TONE
 * @date 2025/3/31
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SUserDetailRes implements Serializable {
    @Serial
    private static final long serialVersionUID = -8179656792399748841L;
    /**
     * ID
     */
    private Long userId;

    /**
     * 账号
     */
    private String account;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 用户联系方式
     */
    private String userContact;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 多人使用状态 0-否 1-是
     */
    private Integer multipleStatus;

    /**
     * 角色ID
     */
    private List<Long> roleIds;

}
