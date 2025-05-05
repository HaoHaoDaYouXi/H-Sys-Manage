package com.haohaodayouxi.manage.model.req.file;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serial;
import java.util.Map;

/**
 * 文件上传对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class FileUploadReq extends FileUploadBaseReq {
    @Serial
    private static final long serialVersionUID = 3684750705505187724L;

    @NotNull(message = "文件不能为空")
    private MultipartFile file;
    /**
     * 加密Key
     */
    private String encryptKey;
    /**
     * 自定义信息
     */
    private Map<String, String> userMetadata;
}
