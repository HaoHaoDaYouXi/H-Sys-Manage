package com.haohaodayouxi.manage.model.req.role;

import com.haohaodayouxi.common.core.model.req.page.PageBaseReq;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serial;

/**
 * RoleUserSelectPageListReq
 *
 * @author TONE
 * @date 2025/10/8
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleUserSelectPageListReq extends PageBaseReq {
    @Serial
    private static final long serialVersionUID = 7799334838196850399L;

    @NotNull(message = "用户ID不能为空")
    private Long userId;
}
