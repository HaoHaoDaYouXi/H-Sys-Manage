package com.haohaodayouxi.manage.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池管理
 *
 * @author TONE
 * @date 2024/12/31
 */
@Configuration(proxyBeanMethods = false)
public class ManageThreadPoolConfig {

    public static final String MANAGE_THREAD_POOL_TASK_EXECUTOR = "MANAGE_THREAD_POOL_TASK_EXECUTOR";

    /**
     * @return 线程池
     */
    @Bean(MANAGE_THREAD_POOL_TASK_EXECUTOR)
    public ThreadPoolTaskExecutor fileDownloadTaskThreadPoolExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(2);
        threadPoolTaskExecutor.setMaxPoolSize(Runtime.getRuntime().availableProcessors() - 2);
        threadPoolTaskExecutor.setQueueCapacity(1000);
        threadPoolTaskExecutor.setKeepAliveSeconds(60);
        threadPoolTaskExecutor.setThreadNamePrefix("manage-task-");
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }


}
