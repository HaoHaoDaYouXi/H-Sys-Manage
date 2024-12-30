package com.haohaodayouxi.manage.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import static com.haohaodayouxi.manage.config.ManageThreadPoolConfig.MANAGE_THREAD_POOL_TASK_EXECUTOR;

/**
 * 事件监听器
 *
 * @author TONE
 * @date 2024/12/31
 */
@Slf4j
@Component
public class ManageEventListener {

    /**
     * 登录成功事件监听
     *
     * @param event 事件
     */
    @Async(MANAGE_THREAD_POOL_TASK_EXECUTOR)
    @EventListener
    public void loginSuccessEventListener(LoginSuccessEvent event) {
        log.info("登录成功事件监听：userId: {}", event.getUserId());
    }
}
