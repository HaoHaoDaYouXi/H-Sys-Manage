package com.haohaodayouxi.manage.constants.enums.file;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;

/**
 * 视频总帧数长度
 *
 * @author TONE
 * @date 2025/4/1
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum FrameLengthTypeEnum {
    /**
     * 少于24帧
     */
    LESS_TWENTY_FOUR(0, 24, 2),
    /**
     * 少于1140帧  (一分钟 24帧)
     */
    LESS_1140_FRAME(24, 24 * 60, 30),
    /**
     * 少于7200帧 （五分钟 24帧）
     */
    LESS_7200_FRAME(24 * 60, 24 * 60 * 5, 50),
    /**
     * 多于7200帧 （十分钟 24帧）
     */
    MORE_7200_FRAME(24 * 60 * 5, null, 100),
    ;
    /**
     * 最小帧长度
     */
    private Integer minLength;
    /**
     * 最大帧长度
     */
    private Integer maxLength;

    /**
     * 帧 份数
     */
    private Integer levelType;


    /**
     * 根据长度获取枚举
     */
    public static FrameLengthTypeEnum getEnumByLength(Integer length) {
        for (FrameLengthTypeEnum frameLengthTypeEnum : FrameLengthTypeEnum.values()) {
            if (length >= frameLengthTypeEnum.getMinLength() && (ObjectUtils.isEmpty(frameLengthTypeEnum.getMaxLength()) || length < frameLengthTypeEnum.getMaxLength())) {
                return frameLengthTypeEnum;
            }
        }
        return MORE_7200_FRAME;
    }
}
