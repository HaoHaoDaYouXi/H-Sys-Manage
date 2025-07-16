package com.haohaodayouxi.manage.model.req.api;

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
 * SApi
 *
 * @author TONE
 * @date 2025/3/1
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SApiAddOrUpdReq implements Serializable {
    @Serial
    private static final long serialVersionUID = -5213550095500871755L;
    /**
     * 接口id
     */
    @NotNull(groups = UpdValid.class, message = "id不能为空")
    private Long apiId;

    /**
     * 模块名称
     */
    @NotBlank(groups = {AddValid.class, UpdValid.class}, message = "模块名称不能为空")
    private String moduleName;

    /**
     * 接口名称
     */
    @NotBlank(groups = {AddValid.class, UpdValid.class}, message = "接口名称不能为空")
    private String apiName;

    /**
     * 接口类型 1-增 2-删 3-改 4-查
     */
    @NotNull(groups = {AddValid.class, UpdValid.class}, message = "接口类型不能为空")
    private Integer apiType;

    /**
     * 接口标识（可通过注解设置，也可以直接使用接口地址）
     */
    @NotBlank(groups = {AddValid.class, UpdValid.class}, message = "接口标识不能为空")
    private String apiKey;

    /**
     * Url类型,0 普通 1 路径中有参数
     */
    @NotNull(groups = {AddValid.class, UpdValid.class}, message = "Url类型不能为空")
    private Integer urlType;

    /**
     * 请求类型
     */
    @NotBlank(groups = {AddValid.class, UpdValid.class}, message = "请求类型不能为空")
    private String requestMethod;

    /**
     * 开放状态 0 未开放,需要菜单权限,1开放
     */
    @NotNull(groups = {AddValid.class, UpdValid.class}, message = "开放状态不能为空")
    private Integer openStatus;
}
