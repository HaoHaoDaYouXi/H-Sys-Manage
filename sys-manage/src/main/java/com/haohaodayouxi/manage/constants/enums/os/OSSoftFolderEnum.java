package com.haohaodayouxi.manage.constants.enums.os;

import com.haohaodayouxi.common.util.constants.OSTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * OSSoftFolderEnum
 *
 * @author TONE
 * @date 2025/4/1
 */
@Slf4j
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum OSSoftFolderEnum {

    //    WIN_32(OSTypeEnum.WIN_32, "soft/win_32/"),
    WIN_64(OSTypeEnum.WIN_64, "soft/win_64/"),
    //    LINUX_32(OSTypeEnum.LINUX_32, "soft/linux_32/"),
    LINUX_64(OSTypeEnum.LINUX_64, "soft/linux_64/"),
    MAC_64(OSTypeEnum.MAC_64, "soft/linux_64/"),
    ;
    /**
     * 系统类型
     */
    private OSTypeEnum osType;
    /**
     * 软件目录
     */
    private String softFolder;


    public static OSSoftFolderEnum getByOS() {
        OSTypeEnum osTypeEnum = OSTypeEnum.getByOS();
        for (OSSoftFolderEnum value : values()) {
            if (value.osType.equals(osTypeEnum)) {
                return value;
            }
        }
        throw new RuntimeException("Unsupported operating system ");
    }
}
