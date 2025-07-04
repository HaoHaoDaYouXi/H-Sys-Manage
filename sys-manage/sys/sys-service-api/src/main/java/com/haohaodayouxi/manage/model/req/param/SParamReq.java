package com.haohaodayouxi.manage.model.req.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * SParamReq
 *
 * @author TONE
 * @date 2025/1/4
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SParamReq implements Serializable {
    @Serial
    private static final long serialVersionUID = 3991628100029612011L;

    /**
     * 编码
     */
    private Long paramCode;
    /**
     * 批量查询的编码
     */
    private String paramCodes;

    /**
     * 参数分类(上级编码，0-第一级)
     */
    private Long paramParentCode;

    /**
     * 需要设置paramParentCode，并且无paramCode和paramCodes才有用
     * 子集深度 null|0-不限制 1-一级子集 2-二级子集 3-三级子集
     * 根据深度 控制子集ID查询长度
     */
    private Integer childDeep;
}
