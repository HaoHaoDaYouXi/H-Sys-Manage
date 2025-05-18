package com.haohaodayouxi.manage.model.bo.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * ParamValueBO
 *
 * @author TONE
 * @date 2025/5/18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParamValueBO implements Serializable {
    @Serial
    private static final long serialVersionUID = -4026041908545609729L;

    private Long code;

    private String label;

    private String value;

    private String remark;

    public ParamValueBO(Long code, String label, String value) {
        this.code = code;
        this.label = label;
        this.value = value;
    }
}
