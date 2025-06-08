package com.haohaodayouxi.manage.event;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.haohaodayouxi.manage.model.db.SUser;
import com.haohaodayouxi.manage.service.SUserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.haohaodayouxi.manage.config.ManageThreadPoolConfig.MANAGE_THREAD_POOL_TASK_EXECUTOR;

/**
 * 事件监听器
 *
 * @author TONE
 * @date 2024/12/31
 */
@EnableAsync
@Slf4j
@Component
public class ManageEventListener {

    @Resource
    private SUserService userService;

    /**
     * 登录成功事件监听
     *
     * @param event 事件
     */
    @Async(MANAGE_THREAD_POOL_TASK_EXECUTOR)
    @EventListener
    public void loginSuccessEventListener(LoginSuccessEvent event) {
        log.info("登录成功事件监听：userId: {}", event.getUserId());
        // 更新登录时间
        userService.update(new LambdaUpdateWrapper<SUser>().eq(SUser::getUserId, event.getUserId()).set(SUser::getLastLoginTime, new Date()));
    }
}
