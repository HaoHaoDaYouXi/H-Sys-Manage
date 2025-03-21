package com.haohaodayouxi.manage.model.req.menu;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * ChangeDisableReq
 *
 * @author TONE
 * @date 2025/3/21
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChangeDisableReq implements Serializable {
    @Serial
    private static final long serialVersionUID = 978292210678124128L;

    @NotNull(message = "菜单ID不能为空")
    private Long menuId;

    @NotNull(message = "禁用状态不能为空")
    private Integer disabled;
}
