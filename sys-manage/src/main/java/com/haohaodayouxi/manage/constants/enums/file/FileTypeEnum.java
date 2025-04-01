package com.haohaodayouxi.manage.constants.enums.file;

import com.haohaodayouxi.common.core.model.vo.keyValue.LabelValueVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 文件类型枚举
 *
 * @author TONE
 * @date 2025/4/1
 */
@Slf4j
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum FileTypeEnum {
    /**
     * 前端不可以控制此参数
     * 0-其他 1-图片 2-附件 3-图片和附件 4-语音 5-视频
     * 接口控制，限制文件类型
     * service基于文件类型做不同的文件处理
     */
    OTHER(0, "other",
            "其他",
            new String[]{}
    ),
    IMAGE(1, "img",
            "图片",
            new String[]{"jpg", "png", "jpeg", "gif", "dng"}
    ),
    DOC(2, "doc",
            "附件",
            new String[]{"doc", "docx", "xls", "xlsx", "ppt", "pptx", "pdf", "txt", "zip"}
    ),
    IMAGE_OR_DOC(3, "imgOrDoc",
            "图片和附件",
            new String[]{"jpg", "png", "jpeg", "gif", "doc", "docx", "xls", "xlsx", "ppt", "pptx", "pdf", "txt", "zip"}
    ),
    AUDIO(4, "audio",
            "音频",
            new String[]{"mp3", "m4a", "wav", "wma", "aac"}
    ),
    VIDEO(5, "video",
            "视频",
            new String[]{"mp4", "avi", "mpeg", "mov", "mkv"}
    ),
    ;
    /**
     * 类型编号
     */
    private Integer type;

    /**
     * 类型
     */
    private String code;
    /**
     * 类型名称
     */
    private String name;
    /**
     * 文件格式
     */
    private String[] fileFormat;

    public static List<LabelValueVO<String, Integer>> getAppFileType() {
        return Arrays.stream(FileTypeEnum.values()).filter(fileTypeEnum -> !fileTypeEnum.equals(FileTypeEnum.OTHER) && !fileTypeEnum.equals(FileTypeEnum.IMAGE_OR_DOC)).map(fileTypeEnum -> new LabelValueVO<String, Integer>(fileTypeEnum.getName(), fileTypeEnum.getType())).collect(Collectors.toList());
    }

    /**
     * 根据类型获取文件后缀
     *
     * @param type type
     * @return 文件后缀
     */
    public static List<String> getFileFormat(Integer type) {
        for (FileTypeEnum value : values()) {
            if (value.getType().equals(type)) {
                return Arrays.asList(value.getFileFormat());
            }
        }
        return new ArrayList<>();
    }

    /**
     * 根据类型获取文件后缀
     *
     * @param type type
     * @return 文件后缀
     */
    public static List<LabelValueVO<String, String>> getFileFormatByType(Integer type) {
        List<LabelValueVO<String, String>> list = new ArrayList<>();
        list.add(new LabelValueVO<>("全部", ""));
        for (FileTypeEnum value : values()) {
            if (value.getType().equals(type)) {
                list.addAll(Arrays.stream(value.getFileFormat()).map(m -> new LabelValueVO<>(m, m)).toList());
                return list;
            }
        }
        return list;
    }

    public static FileTypeEnum getByFileFormat(String fileFormat) {
        if (StringUtils.isEmpty(fileFormat)) {
            return OTHER;
        }
        for (FileTypeEnum fileTypeEnum : FileTypeEnum.values()) {
            if (Arrays.asList(fileTypeEnum.getFileFormat()).contains(fileFormat.toLowerCase())) {
                return fileTypeEnum;
            }
        }
        return OTHER;
    }

}
