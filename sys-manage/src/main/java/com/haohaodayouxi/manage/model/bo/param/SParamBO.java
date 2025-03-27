package com.haohaodayouxi.manage.model.bo.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * ParamBO
 *
 * @author TONE
 * @date 2024/12/29
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SParamBO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1363111550768463465L;

    /**
     * 编码
     */
    private Long paramCode;

    /**
     * 参数分类(上级编码，0-第一级)
     */
    private Long paramParentCode;

    /**
     * 参数名称
     */
    private String paramName;

    /**
     * 参数值
     */
    private String paramValue;

    /**
     * 排序code
     */
    private Integer paramSortCode;

    /**
     * 修改时间
     */
    private Date updateTime;
}
