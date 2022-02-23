package com.wuxin.blog.task;

import com.wuxin.blog.redis.RedisKey;
import com.wuxin.blog.redis.RedisService;
import com.wuxin.blog.service.AccessLogService;
import com.wuxin.blog.service.LoginLogService;
import com.wuxin.blog.utils.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: wuxin001
 * @Date: 2022/02/13/12:50
 * @Description: 使用redis缓存七天以内的访问量
 */
@Component
public class ScheduleTask {


    private static final Logger logger = LoggerFactory.getLogger(ScheduleTask.class);


    /**
     * 过期时间 14天
     */
    private static final long EXPIRE_TIME = 1209600L;

    @Autowired
    private RedisService redisService;


    @Autowired
    private AccessLogService accessLogService;

    @Autowired
    private LoginLogService loginLogService;


    /**
     * 将今日访问量缓存
     */
    public void saveCountTask() {
        Map<String, Object> map = new HashMap<>();
        map.put("date", DateUtils.simpleDate());
        map.put("access", accessLogService.selectTodayAccessLog());
        map.put("login", loginLogService.selectTodayLoginLog());
        logger.info("今日访问量统计中 visit:{}", map);
        final List<Map<String, Object>> list = getAccessLoginCount();
        list.add(map);
        boolean set = redisService.set(RedisKey.ACCESS_LOGIN_COUNT, list, EXPIRE_TIME);
        if (set) {
            logger.info("今日访问量统计成功:{}", list);
        } else {
            logger.error("今日访问量统计失败！{}", list);
        }
    }


    /**
     * 获取访问量统计
     */
    public List<Map<String, Object>> getAccessLoginCount() {
        List<Map<String, Object>> list;
        boolean b = redisService.hasKey(RedisKey.ACCESS_LOGIN_COUNT);
        if (b) {
            list = (List<Map<String, Object>>) redisService.get(RedisKey.ACCESS_LOGIN_COUNT);
        } else {
            // 首次执行需要创建对象
            list = new ArrayList<>();
        }
        return list;
    }


}
