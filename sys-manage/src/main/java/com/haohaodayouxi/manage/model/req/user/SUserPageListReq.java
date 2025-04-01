package com.haohaodayouxi.manage.model.req.user;

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
public class SUserPageListReq extends PageBaseReq {

    @Serial
    private static final long serialVersionUID = -5904386844689777391L;
    /**
     * 账号
     */
    private String account;

    /**
     * 用户名
     */
    private String userName;
}
