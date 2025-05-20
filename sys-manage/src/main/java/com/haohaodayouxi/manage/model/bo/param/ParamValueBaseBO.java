package com.haohaodayouxi.manage.model.bo.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * ParamValueBaseBO
 *
 * @author TONE
 * @date 2025/5/18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParamValueBaseBO implements Serializable {
    @Serial
    private static final long serialVersionUID = -4026041908545609729L;

    private Long code;

    private String name;

    private String value;

    private String remark;

    public ParamValueBaseBO(Long code, String name, String value) {
        this.code = code;
        this.name = name;
        this.value = value;
    }
}
