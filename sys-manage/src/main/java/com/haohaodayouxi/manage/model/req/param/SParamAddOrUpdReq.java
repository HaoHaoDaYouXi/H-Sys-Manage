package com.haohaodayouxi.manage.model.req.param;

import com.haohaodayouxi.common.core.interfaces.AddValid;
import com.haohaodayouxi.common.core.interfaces.UpdValid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * SParamAddOrUpdReq
 *
 * @author TONE
 * @date 2025/3/28
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SParamAddOrUpdReq implements Serializable {

    @Serial
    private static final long serialVersionUID = -8570795252420974905L;
    /**
     * 编码
     */
    @NotNull(groups = UpdValid.class, message = "编码不能为空")
    private Long paramCode;

    /**
     * 参数名称
     */
    @NotBlank(groups = {AddValid.class, UpdValid.class}, message = "参数名称不能为空")
    private String paramName;

    /**
     * 参数值
     */
    @NotBlank(groups = {AddValid.class, UpdValid.class}, message = "参数值不能为空")
    private String paramValue;

    /**
     * 参数备注
     */
    private String paramRemark;

    /**
     * 排序code
     */
    @NotNull(groups = {AddValid.class, UpdValid.class}, message = "排序不能为空")
    private Integer paramSortCode;
}
