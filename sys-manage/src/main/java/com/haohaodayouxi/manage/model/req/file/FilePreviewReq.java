package com.haohaodayouxi.manage.model.req.file;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilePreviewReq implements Serializable {
    @Serial
    private static final long serialVersionUID = 4532075799080998491L;
    /**
     * 签名
     */
    private String s;
    /**
     * token
     */
    private String t;
    /**
     * 随机码
     */
    private String c;

    /**
     * 用户token
     */
    private String token;

    /**
     * 文件名称
     */
    private String fileName;

}
