package com.haohaodayouxi.manage.model.req.login;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

/**
 * AccountLoginReq
 *
 * @author TONE
 * @date 2024/12/9
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class AccountLoginReq implements Serializable {
    @Serial
    private static final long serialVersionUID = -4511624287467497112L;
    /**
     * 账号
     */
    @NotBlank(message = "账号不能为空！")
    private String account;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空！")
    private String pwd;
}
