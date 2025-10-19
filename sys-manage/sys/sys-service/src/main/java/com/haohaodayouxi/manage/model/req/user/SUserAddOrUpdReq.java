package com.haohaodayouxi.manage.model.req.user;

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
import java.util.List;

/**
 * SUserAddOrUpdReq
 *
 * @author TONE
 * @date 2025/3/31
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SUserAddOrUpdReq implements Serializable {
    @Serial
    private static final long serialVersionUID = 8155414672606955795L;

    /**
     * ID
     */
    @NotNull(groups = {UpdValid.class}, message = "ID不能为空")
    private Long userId;

    /**
     * 账号
     */
    @NotBlank(groups = {AddValid.class, UpdValid.class}, message = "账号不能为空")
    private String account;

    /**
     * 密码
     */
    @NotBlank(groups = {AddValid.class}, message = "密码不能为空")
    private String pwd;

    /**
     * 用户名称
     */
    @NotBlank(groups = {AddValid.class, UpdValid.class}, message = "用户名称不能为空")
    private String userName;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 用户联系方式
     */
    private String userContact;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 多人使用状态 0-否 1-是
     */
    @NotNull(groups = {AddValid.class, UpdValid.class}, message = "多人使用状态不能为空")
    private Integer multipleStatus;

    /**
     * 角色ID
     */
    private List<Long> roleIds;

}
