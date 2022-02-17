package com.wuxin.blog.config;

import com.wuxin.blog.task.ScheduleTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

/**
 * @Author: wuxin001
 * @Date: 2022/02/13/11:44
 * @Description: 定时任务 缓存 用户访问和登录量
 */
@EnableScheduling
@Configuration
public class ScheduleTaskConfig {

    @Autowired
    private ScheduleTask scheduleTask;


    private static final Logger logger = LoggerFactory.getLogger(ScheduleTaskConfig.class);


    /**
     * 定时任务每天晚上11点钟执行
     */
    @Scheduled(cron = "0 0 23 * * ? ")
    private void saveAccessLogin() {
        logger.info("----------------当天访问量和浏览量统计中--------------");
        scheduleTask.saveCountTask();
    }
}
