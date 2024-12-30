package com.haohaodayouxi.manage.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.io.Serial;

/**
 * 事件对象
 *
 * @author TONE
 * @date 2024/12/31
 */
@Getter
public class LoginSuccessEvent extends ApplicationEvent {
    @Serial
    private static final long serialVersionUID = 2556718453214335391L;
    /**
     * 事件内容 具体参数可以自行定义
     */
    private final Long userId;

    public LoginSuccessEvent(Long userId) {
        super(userId);
        this.userId = userId;
    }
}
