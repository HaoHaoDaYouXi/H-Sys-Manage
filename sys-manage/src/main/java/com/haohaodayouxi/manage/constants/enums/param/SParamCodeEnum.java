package com.haohaodayouxi.manage.constants.enums.param;

import com.alibaba.fastjson2.JSON;
import com.haohaodayouxi.manage.model.bo.param.ParamValueBO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 配置类 根编码 枚举
 *
 * @author TONE
 * @date 2024/12/23
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum SParamCodeEnum {

    MENU_TYPE(1L, "菜单类型", MENU_TYPE_VALUE()),
    MENU_BUTTON_TYPE(2L, "菜单按钮类型"),
    ROLE_TYPE(11L, "角色类型", ROLE_TYPE_VALUE()),
    LOGIN_LIMIT(21L, "登录限制控制", LOGIN_LIMIT_VALUE()),
    ;
    private Long code;

    private String name;

    private List<ParamValueBO> value;

    SParamCodeEnum(Long code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getValueString() {
        return JSON.toJSONString(this.value);
    }

    public static void main(String[] args) {
        System.out.println(SParamCodeEnum.MENU_TYPE.value);
        System.out.println(SParamCodeEnum.MENU_TYPE.getValue());
        System.out.println(SParamCodeEnum.MENU_TYPE.getValueString());
    }

    public static List<ParamValueBO> LOGIN_LIMIT_VALUE() {
        List<ParamValueBO> valueList = new ArrayList<>();
        valueList.add(new ParamValueBO(1L, "登录错误次数", "6", "登录错误次数到了就锁定"));
        valueList.add(new ParamValueBO(2L, "登录错误显示验证码", "3", "登录错误次数到了就需要验证码"));
        valueList.add(new ParamValueBO(3L, "登录锁定时间", "30", "登录锁定时间(单位：分钟)"));
        return valueList;
    }

    public static List<ParamValueBO> ROLE_TYPE_VALUE() {
        List<ParamValueBO> valueList = new ArrayList<>();
        valueList.add(new ParamValueBO(1L, "系统管理员", "1"));
        valueList.add(new ParamValueBO(2L, "普通用户", "2"));
        return valueList;
    }

    public static List<ParamValueBO> MENU_TYPE_VALUE() {
        List<ParamValueBO> valueList = new ArrayList<>();
        valueList.add(new ParamValueBO(1L, "目录", "1"));
        valueList.add(new ParamValueBO(2L, "页面", "2"));
        valueList.add(new ParamValueBO(3L, "按钮", "3"));
        return valueList;
    }
}
