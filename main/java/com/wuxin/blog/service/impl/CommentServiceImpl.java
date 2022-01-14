package com.wuxin.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuxin.blog.mapper.UserMapper;
import com.wuxin.blog.mapper.vo.CommentMapper;
import com.wuxin.blog.mapper.vo.CommentReplyMapper;
import com.wuxin.blog.pojo.blog.Comment;
import com.wuxin.blog.pojo.blog.CommentReply;
import com.wuxin.blog.pojo.blog.User;
import com.wuxin.blog.redis.RedisKey;
import com.wuxin.blog.redis.RedisService;
import com.wuxin.blog.service.CommentService;
import com.wuxin.blog.utils.ThrowUtils;
import com.wuxin.blog.utils.mapper.MapperUtils;
import com.wuxin.blog.utils.string.StringUtils;
import com.wuxin.blog.utils.time.DateUtils;
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
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commenMapper;

    @Autowired
    private CommentReplyMapper blogCommentReplyMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisService redisService;

    @Override
    public void addComment(Comment blogComment) {
        ThrowUtils.ops(commenMapper.insert(blogComment), "评论添加失败");
        redisService.hdel(RedisKey.COMMENT_LSIT);
    }

    @Override
    public void delComment(Long commentId) {
        ThrowUtils.ops(commenMapper.deleteById(commentId), "评论添加失败");
        redisService.hdel(RedisKey.COMMENT_LSIT);
    }

    @Override
    public void delCommentByUserId(Long userId) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getCommentUserId, userId);
        ThrowUtils.ops(commenMapper.delete(queryWrapper), "删除失败！用户不存在！");
        redisService.hdel(RedisKey.COMMENT_LSIT);
    }

    @Override
    public void delCommentByBlogId(Long blogId) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getBlogId, blogId);
        ThrowUtils.ops(commenMapper.delete(queryWrapper), "删除失败！文章不存在！");
        redisService.hdel(RedisKey.COMMENT_LSIT);
    }


    @Override
    public void updateComment(Comment comment) {
        ThrowUtils.ops(commenMapper.updateById(comment), "修改失败！评论不存在！");
        redisService.hdel(RedisKey.COMMENT_LSIT);
    }

    @Override
    public IPage<Comment> findBlogCommentByBlogId(Integer current, Integer size, Long blogId) {
        // 根据blogId获取获取对应commentId list
        String key = RedisKey.COMMENT_LSIT;
        boolean b = redisService.hHasKey(key, current+blogId);
        if(b){
            IPage<Comment> page = (IPage<Comment>) redisService.hget(key, current+blogId);
            if(StringUtils.isNotNull(page) && page.getRecords().size()!=0){
                return page;
            }
        }
        Page<Comment> page = new LambdaQueryChainWrapper<Comment>(commenMapper)
                .eq(Comment::getBlogId, blogId)
                .orderByDesc(Comment::getCreateTime)
                .page(new Page<>(current, size));

        // 获取评论用户Id名头像等基本信息
        page.getRecords().forEach(comment -> {
            User commentUser = userMapper.selectById(comment.getCommentUserId());
            ThrowUtils.userNull(commentUser);
            // 获取用户昵称
            comment.setUsername(commentUser.getNickname());
            // 获取用户头像
            comment.setAvatar(commentUser.getAvatar());
            getReplyList(comment);
        });
        // 存入到redis
        redisService.hset(key,current+blogId,page);

        // 获取blog
        return page;
    }


    @Override
    public IPage<Comment> findCommentList(Integer current, Integer limit, Long blogId, Integer type) {
        // 根据blogId获取获取对应commentId list

        // 判断blog是否为空

        Page<Comment> page = new LambdaQueryChainWrapper<Comment>(commenMapper)
                .eq(StringUtils.isNotNull(blogId), Comment::getBlogId, blogId)
                .eq(StringUtils.isNotNull(type), Comment::getType, type)
                .orderByDesc(Comment::getCreateTime)
                .page(new Page<>(current, limit));

        // 获取评论用户Id名头像等基本信息
        page.getRecords().forEach(comment -> {
            ThrowUtils.isNull(comment, "该评论不存在！");
            User commentUser = userMapper.selectById(comment.getCommentUserId());
            ThrowUtils.userNull(commentUser);
            // 获取用户昵称
            comment.setUsername(commentUser.getNickname());
            // 获取用户头像
            comment.setAvatar(commentUser.getAvatar());
            getReplyList(comment);
        });

        // 获取blog
        return page;
    }

    @Override
    public void addReply(CommentReply blogCommentReply) {
        ThrowUtils.ops(blogCommentReplyMapper.insert(blogCommentReply), "回复添加失败！");
    }

    @Override
    public void delReply(Long replyId) {
        ThrowUtils.ops(blogCommentReplyMapper.deleteById(replyId), "删除失败,该回复不存在");
    }


    @Override
    public void delReplyByUserId(Long userId) {
        LambdaQueryWrapper<CommentReply> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CommentReply::getReplyUserId, userId);
        ThrowUtils.ops(blogCommentReplyMapper.delete(queryWrapper), "删除失败,该回复不存在");
    }

    @Override
    public void updateComment(CommentReply reply) {
        ThrowUtils.ops(blogCommentReplyMapper.updateById(reply), "删除失败,该回复不存在");
    }

    @Override
    public void delCommentReplyByBlogId(Long blogId) {
        LambdaQueryWrapper<CommentReply> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CommentReply::getBlogId, blogId);
        ThrowUtils.ops(blogCommentReplyMapper.delete(queryWrapper), "删除失败,该回复不存在");

    }


    @Override
    public void delCommentReplyByCommentId(Long commentId) {
        LambdaQueryWrapper<CommentReply> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CommentReply::getCommentId, commentId);
        ThrowUtils.ops(blogCommentReplyMapper.delete(queryWrapper), "删除失败,该回复不存在");

    }

    @Override
    public Integer findCommentCount() {
        int integer = commenMapper.selectCount(null);
        int integer1 = blogCommentReplyMapper.selectCount(null);
        return integer + integer1;
    }

    @Override
    public IPage<Comment> findBlogCommentByPage(Integer current, Integer limit, Integer type, String keywords) {
        LambdaQueryChainWrapper<Comment> queryChainWrapper = new LambdaQueryChainWrapper<>(commenMapper);
        queryChainWrapper.orderByDesc(Comment::getCreateTime);
        Page<Comment> commentPage = new Page<>(current, limit);
        Page<Comment> page = queryChainWrapper.like(!keywords.isEmpty(), Comment::getContent, keywords).page(commentPage);
        page.getRecords().forEach(comment -> {
            // 获取评论用户名
            User commentUser = userMapper.selectById(comment.getCommentUserId());
            ThrowUtils.userNull(commentUser);

            comment.setUsername(commentUser.getUsername());
            comment.setAvatar(commentUser.getAvatar());

            getReplyList(comment);

        });
        return page;

    }

    @Override
    public IPage<CommentReply> findBlogCommentReplyPage(Integer current, Integer limit, String keywords) {
        LambdaQueryChainWrapper<CommentReply> chainWrapper = new LambdaQueryChainWrapper<CommentReply>(blogCommentReplyMapper);
        Page<CommentReply> commentPage = new Page<>(current, limit);
        Page<CommentReply> page = new LambdaQueryChainWrapper<CommentReply>(blogCommentReplyMapper).like(!keywords.isEmpty(), CommentReply::getReplyContent, keywords).page(commentPage);
        page.getRecords().forEach(commentReply -> {
            // 获取评论用户名
            String username = userMapper.selectById(commentReply.getReplyUserId()).getNickname();
            ThrowUtils.isNull(username, "用户名获取失败");
            commentReply.setReplyUsername(username);
        });
        return page;
    }

    @Override
    public List<Comment> allBlogComment(Long blogId) {
        LambdaQueryChainWrapper<Comment> queryChainWrapper = new LambdaQueryChainWrapper<>(commenMapper);
        queryChainWrapper.orderByDesc(Comment::getCreateTime);
        List<Comment> blogCommentList = queryChainWrapper.eq(Comment::getBlogId, blogId).list();
        blogCommentList.forEach(comment -> {
            // 获取评论用户名
            User commentUser = userMapper.selectById(comment.getCommentUserId());
            ThrowUtils.userNull(commentUser);
            comment.setUsername(commentUser.getNickname());
            comment.setAvatar(commentUser.getAvatar());
            getReplyList(comment);

        });
        return blogCommentList;
    }

    @Override
    public Integer commentCount() {
        // 当前时间
        String localTime = DateUtils.localTime();
        // 凌晨时间
        String todayStartTime = DateUtils.todayStartTime();

        Integer commentCount = MapperUtils.lambdaQueryWrapper(commenMapper)
                .le(Comment::getCreateTime, localTime)
                .ge(Comment::getCreateTime, todayStartTime)
                .count();

        Integer replyCount = MapperUtils.lambdaQueryWrapper(blogCommentReplyMapper)
                .le(CommentReply::getCreateTime, localTime)
                .ge(CommentReply::getCreateTime, todayStartTime)
                .count();
        return
                commentCount + replyCount;

    }

    public void getReplyList(Comment blogComment) {
        List<CommentReply> replyList = new LambdaQueryChainWrapper<CommentReply>(blogCommentReplyMapper)
                .eq(CommentReply::getBlogId, blogComment.getBlogId())
                .eq(CommentReply::getCommentId, blogComment.getCommentId())
                .list();

        // 判断是否含有评论信息
        if (replyList.size() != 0) {
            replyList.forEach(commentReply -> {
                // 获取回复人基本信息 用户名 头像等
                User replyUser = userMapper.selectById(commentReply.getReplyUserId());
                ThrowUtils.userNull(replyUser);
                commentReply.setReplyUsername(replyUser.getNickname());
                commentReply.setReplyAvatar(replyUser.getAvatar());

                // 被评论人信息
                User replyCommentUser = userMapper.selectById(commentReply.getCommentUserId());
                ThrowUtils.userNull(replyCommentUser);
                commentReply.setCommentUsername(replyCommentUser.getNickname());
            });
        }

        // 将reply list信息储存到comment中
        blogComment.setReplyList(replyList);
    }


}
