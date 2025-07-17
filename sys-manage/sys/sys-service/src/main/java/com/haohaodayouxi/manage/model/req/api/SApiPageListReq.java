package com.haohaodayouxi.manage.model.req.api;

import com.haohaodayouxi.common.core.model.req.page.PageBaseReq;
import lombok.*;

import java.io.Serial;

/**
 * SApiPageListReq
 *
 * @author TONE
 * @date 2025/7/17
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SApiPageListReq extends PageBaseReq {

    @Serial
    private static final long serialVersionUID = 1449559486199562657L;
    /**
     * 模块名称
     */
    private String moduleName;
}
