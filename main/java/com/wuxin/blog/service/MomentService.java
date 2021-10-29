package com.wuxin.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wuxin.blog.pojo.Moment;

/**
 * @Author: wuxin001
 * @Date: 2021/10/03/1:35
 * @Description:
 */
public interface MomentService {

    int addMoment(Moment moment);
    int updateMoment(Moment moment);
    int delMoment(Long momentId);
    IPage<Moment> findMoment(int current,int limit);
    Moment momentDetail(Long momentId);
}
