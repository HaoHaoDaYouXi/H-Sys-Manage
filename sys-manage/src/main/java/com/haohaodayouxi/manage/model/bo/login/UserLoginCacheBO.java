package com.haohaodayouxi.manage.model.bo.login;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

/**
 * UserRedisBO
 *
 * @author TONE
 * @date 2024/12/9
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginCacheBO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1078644220372351993L;
    /**
     * ID
     */
    private Long userId;

    /**
     * 账号
     */
    private String account;

    /**
     * 用户编码
     */
    private String userCode;

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
     * 补充数据 key=字段名，value=字段对应的值或对象
     */
    private Map<String, Object> supplementDataMap;
}
