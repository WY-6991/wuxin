package com.wuxin.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wuxin.blog.pojo.blog.UpdateQuestion;
import com.wuxin.blog.base.PageService;

/**
 * @Author: wuxin001
 * @Date: 2021/10/01/9:24
 * @Description:
 */
public interface UpdateQuestionService extends PageService<UpdateQuestion> {

    IPage<UpdateQuestion> selectListByPage(Integer current, Integer limit,String keywords,String start,String end);

}
