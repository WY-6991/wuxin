package com.wuxin.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wuxin.blog.pojo.log.LoginLog;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;


/**
 * @author Administrator
 */
@Repository
public interface LoginLogMapper extends BaseMapper<LoginLog> {


    /**
     * 统计今日登录用户次数
     *
     * @param start 今日凌晨时间
     * @param end   当前时间
     * @return 次数
     */
    @Select("select count(distinct by_create) from wx_login_log where create_time>= #{start} and create_time<= #{end}")
    Integer todayLoginCount(@Param("start") String start, @Param("end") String end);

}
