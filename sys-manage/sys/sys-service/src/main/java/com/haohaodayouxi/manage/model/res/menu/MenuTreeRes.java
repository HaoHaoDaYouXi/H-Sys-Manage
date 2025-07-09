package com.haohaodayouxi.manage.model.res.menu;

import com.haohaodayouxi.common.core.model.vo.keyValue.LabelValueVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;

/**
 * MenuTreeRes
 *
 * @author TONE
 * @date 2025/3/25
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuTreeRes extends LabelValueVO<String, Long> {
    @Serial
    private static final long serialVersionUID = 8491371361912887051L;

    private boolean hasChildren;
}
