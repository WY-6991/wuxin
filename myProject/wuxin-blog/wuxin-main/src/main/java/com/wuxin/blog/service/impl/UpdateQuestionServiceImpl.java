package com.wuxin.blog.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuxin.blog.mapper.UpdateQuestionMapper;
import com.wuxin.blog.pojo.blog.UpdateQuestion;
import com.wuxin.blog.redis.RedisKey;
import com.wuxin.blog.redis.RedisService;
import com.wuxin.blog.service.UpdateQuestionService;
import com.wuxin.blog.utils.MapperUtils;
import com.wuxin.blog.utils.ThrowUtils;
import com.wuxin.blog.utils.string.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;



/**
 * @Author: wuxin001
 * @Date: 2021/10/01/11:08
 * @Description:
 */
@Service
@Transactional(rollbackFor = {Exception.class})
public class UpdateQuestionServiceImpl implements UpdateQuestionService {

    @Autowired
    private UpdateQuestionMapper updateQuestionMapper;

    @Autowired
    private RedisService redisService;


    @Override
    public void add(UpdateQuestion updateQuestion) {
        ThrowUtils.ops(updateQuestionMapper.insert(updateQuestion),"系统问题发布失败！");
        List<UpdateQuestion> list = list();
        list.add(updateQuestion);
        redisService.set(RedisKey.UPDATE_INFO, list);
    }

    @Override
    public void update(UpdateQuestion updateQuestion) {
        ThrowUtils.ops(updateQuestionMapper.updateById(updateQuestion),"修改失败,内容不存在！");
        // 删除redis中内容
        redisService.del(RedisKey.UPDATE_INFO);

    }

    @Override
    public void delete(Long id) {

        ThrowUtils.ops(updateQuestionMapper.deleteById(id),"删除失败！,内容不存在！");
        redisService.del(RedisKey.UPDATE_INFO);
        // 删除redis中内容
        List<UpdateQuestion> list = list();
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).getId().equals(id)){
                list.remove(i);
                redisService.set(RedisKey.UPDATE_INFO,list);
            }
        }
    }

    @Override
    public List<UpdateQuestion> list() {
        // 从redis中获取list
        boolean b = redisService.hasKey(RedisKey.UPDATE_INFO);
        if(b){
            List<UpdateQuestion> list = (List<UpdateQuestion>) redisService.get(RedisKey.UPDATE_INFO);
            if(list.size()!=0){
                return list;
            }
        }
        // 如果redis中没有内容就从mysql数据库中获取 并且存入
        List<UpdateQuestion> list = MapperUtils.lambdaQueryWrapper(updateQuestionMapper).orderByDesc(UpdateQuestion::getCreateTime).list();
        redisService.set(RedisKey.UPDATE_INFO,list);
        return list;
    }

    @Override
    public IPage<UpdateQuestion> selectListByPage(Integer current, Integer limit) {
        return MapperUtils.lambdaQueryWrapper(updateQuestionMapper).orderByDesc(UpdateQuestion::getCreateTime).page(new Page<>(current,limit));
    }

    @Override
    public UpdateQuestion find(Long id) {
        ThrowUtils.ops(0, "该功能还未实现哦");
        return null;
    }


    @Override
    public IPage<UpdateQuestion> selectListByPage(Integer current, Integer limit, String keywords) {
        ThrowUtils.ops(0, "该功能还未实现哦");
        return null;
    }

    @Override
    public IPage<UpdateQuestion> selectListByPage(Integer current, Integer limit,String keywords,String start, String end) {
        return MapperUtils.lambdaQueryWrapper(updateQuestionMapper).orderByDesc(UpdateQuestion::getCreateTime)
                .like(StringUtils.isNotNull(keywords),UpdateQuestion::getContent,keywords)
                .le(StringUtils.isNotNull(end), UpdateQuestion::getCreateTime, end)
                .ge(StringUtils.isNotNull(start), UpdateQuestion::getCreateTime, start).page(new Page<>(current,limit));
    }
}
