package com.haohaodayouxi.manage;

import com.haohaodayouxi.common.core.interfaces.InitService;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Map;

/**
 * ManageBootstrap
 *
 * @author TONE
 * @date 2024/8/31
 */
@Slf4j
@SpringBootApplication(scanBasePackages = {"com.haohaodayouxi"})
@MapperScan(basePackages = {"com.haohaodayouxi.manage.mapper"})
public class ManageBootstrap {
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(ManageBootstrap.class, args);
        init(applicationContext);
    }

    private static void init(ApplicationContext applicationContext) {
        log.info("----------- 启动成功，开始初始化 -----------");
        // 调用初始化方法
        Map<String, InitService> beansOfType = applicationContext.getBeansOfType(InitService.class);
        beansOfType.forEach((name, value) -> {
            try {
                value.init();
                log.info("----------- [{}] 初始化成功 -----------", name);
            } catch (Exception e) {
                log.error("----------- [{}] 初始化异常 -----------", name);
                // 自行判断是否要结束项目
                // System.exit(1);
            }
        });
        log.info("----------- 启动结束 -----------");
    }
}
