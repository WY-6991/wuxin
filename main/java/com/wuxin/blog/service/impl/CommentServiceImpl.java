package com.wuxin.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuxin.blog.mapper.BlogMapper;
import com.wuxin.blog.mapper.UserMapper;
import com.wuxin.blog.mapper.CommentMapper;
import com.wuxin.blog.mapper.CommentReplyMapper;
import com.wuxin.blog.pojo.blog.*;
import com.wuxin.blog.redis.RedisKey;
import com.wuxin.blog.redis.RedisService;
import com.wuxin.blog.service.CommentService;
import com.wuxin.blog.utils.ThrowUtils;
import com.wuxin.blog.utils.mapper.MapperUtils;
import com.wuxin.blog.utils.string.StringUtils;
import com.wuxin.blog.utils.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: wuxin001
 * @Date: 2021/10/01/11:08
 * @Description:
 */
@Service
@Transactional(rollbackFor = {Exception.class})
public class CommentServiceImpl implements CommentService {


    private final static String COMMENT_LIST = RedisKey.COMMENT_LIST;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private CommentReplyMapper blogCommentReplyMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private RedisService redisService;


    @Value("${comment.expireTime}")
    private Long expire;

    @Override
    public void addComment(Comment comment) {
        comment.setStatus(true);
        ThrowUtils.ops(commentMapper.insert(comment), "评论添加失败");
        deleteCommentCache(comment.getBlogId(), comment.getType());
    }

    @Override
    public Comment findCommentByCommentId(Long commentId) {
        return commentMapper.selectById(commentId);
    }

    @Override
    public void delComment(Long commentId) {
        Comment comment = getComment(commentId);
        deleteCommentCache(comment.getBlogId(), comment.getType());
        ThrowUtils.ops(commentMapper.deleteById(commentId), "评论添加失败");
    }

    @Override
    public void delCommentByUserId(Long userId) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getCommentUserId, userId);

        // 删除缓存
        List<Comment> comments = commentMapper.selectList(queryWrapper);
        comments.forEach(comment -> {
            deleteCommentCache(comment.getBlogId(), comment.getType());
        });

        ThrowUtils.ops(commentMapper.delete(queryWrapper), "删除失败！用户不存在！");
        // 添加评论删除原来缓存内容
    }

    @Override
    public void delCommentByBlogId(Long blogId) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getBlogId, blogId);
        ThrowUtils.ops(commentMapper.delete(queryWrapper), "删除失败！文章不存在！");
    }


    @Override
    public void updateComment(Comment comment) {
        ThrowUtils.ops(commentMapper.updateById(comment), "修改失败！评论不存在！");
        deleteCommentCache(comment.getBlogId(), comment.getType());
    }


    @Override
    public IPage<Comment> findCommentList(Integer current, Integer limit, Long blogId, Integer type) {
        String hk = RedisKey.getKey(blogId, COMMENT_LIST, current, type);
        boolean b = redisService.hHasKey(COMMENT_LIST, hk);
        if (b) {
            IPage<Comment> commentIPage = (IPage<Comment>) redisService.hget(COMMENT_LIST, hk);
            if (commentIPage.getRecords().size() != 0) {
                return commentIPage;
            }

        }
        Page<Comment> page = new LambdaQueryChainWrapper<Comment>(commentMapper)
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
            // 获取回复
            getReplyList(comment);
        });
        // 将评论缓存 同时设置 过期时间
        redisService.hset(COMMENT_LIST, hk, page, expire);

        // 获取blog
        return page;
    }

    @Override
    public void addReply(CommentReply reply) {
        reply.setStatus(true);
        ThrowUtils.ops(blogCommentReplyMapper.insert(reply), "回复添加失败！");
        deleteCommentCache(reply.getBlogId(), reply.getType());
    }

    @Override
    public void delReply(Long replyId) {
        CommentReply reply = getCommentReply(replyId);
        deleteCommentCache(reply.getBlogId(), reply.getType());
        ThrowUtils.ops(blogCommentReplyMapper.deleteById(replyId), "删除失败,该回复不存在");
    }


    @Override
    public void delReplyByUserId(Long userId) {
        LambdaQueryWrapper<CommentReply> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CommentReply::getReplyUserId, userId);
        // 删除缓存
        List<CommentReply> commentReplies = blogCommentReplyMapper.selectList(queryWrapper);
        commentReplies.forEach(reply -> {
            deleteCommentCache(reply.getBlogId(), reply.getType());
        });
        ThrowUtils.ops(blogCommentReplyMapper.delete(queryWrapper), "删除失败,该回复不存在");
    }

    @Override
    public void updateComment(CommentReply reply) {
        ThrowUtils.ops(blogCommentReplyMapper.updateById(reply), "删除失败,该回复不存在");
        // 删除缓存
        deleteCommentCache(reply.getBlogId(), reply.getType());
    }

    @Override
    public void delCommentReplyByBlogId(Long blogId) {
        LambdaQueryWrapper<CommentReply> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CommentReply::getBlogId, blogId);
        ThrowUtils.ops(blogCommentReplyMapper.delete(queryWrapper), "删除失败,该回复不存在");
    }


    @Override
    public void delCommentReplyByCommentId(Long commentId) {
        Comment comment = getComment(commentId);
        deleteCommentCache(comment.getBlogId(), comment.getType());
        LambdaQueryWrapper<CommentReply> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CommentReply::getCommentId, commentId);
        ThrowUtils.ops(blogCommentReplyMapper.delete(queryWrapper), "删除失败,该回复不存在");

    }


    @Override
    public Integer findCommentCount(Long blogId, Integer type) {
        Integer commentCount = onlyCommentCount(blogId, type);
        LambdaQueryWrapper<CommentReply> replyLambdaQueryWrapper = new LambdaQueryWrapper<>();
        replyLambdaQueryWrapper.eq(StringUtils.isNotNull(blogId), CommentReply::getBlogId, blogId);
        replyLambdaQueryWrapper.eq(CommentReply::getType, type);
        replyLambdaQueryWrapper.eq(CommentReply::isStatus, 1);
        Integer replyCount = blogCommentReplyMapper.selectCount(replyLambdaQueryWrapper);
        return commentCount + replyCount;
    }

    @Override
    public IPage<Comment> findBlogCommentByPage(Integer current, Integer limit, Integer type, String keywords, Long blogId, String start, String end) {
        LambdaQueryChainWrapper<Comment> queryChainWrapper = new LambdaQueryChainWrapper<>(commentMapper);
        queryChainWrapper.orderByDesc(Comment::getCreateTime);
        Page<Comment> commentPage = new Page<>(current, limit);
        Page<Comment> page = queryChainWrapper
                .like(StringUtils.isNotNull(keywords), Comment::getContent, keywords)
                .eq(Comment::getType, type)
                .eq(StringUtils.isNotNull(blogId), Comment::getBlogId, blogId)
                .le(StringUtils.isNotNull(end), Comment::getCreateTime, end)
                .ge(StringUtils.isNotNull(start), Comment::getCreateTime, start)
                .page(commentPage);

        page.getRecords().forEach(comment -> {
            if (type.equals(Comment.BLOG_COMMENT)) {
                String title = MapperUtils.lambdaQueryWrapper(blogMapper).select(Blog::getBlogId, Blog::getTitle).eq(Blog::getBlogId, comment.getBlogId()).one().getTitle();
                comment.setTitle(title);
            }
            if (type.equals(Comment.ABOUT_COMMENT)) {
                comment.setTitle("关于我");
            }
            if (type.equals(Comment.FRIEND_COMMENT)) {
                comment.setTitle("友情链接");
            }
            // 获取评论用户名
            User commentUser = userMapper.selectById(comment.getCommentUserId());
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
        LambdaQueryChainWrapper<Comment> queryChainWrapper = new LambdaQueryChainWrapper<>(commentMapper);
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
        return commentMapper.todayCommentCount(DateUtils.todayStartTime(), DateUtils.localTime());
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


    private Integer onlyCommentCount(Long blogId, Integer type) {
        // 获取评论
        LambdaQueryWrapper<Comment> commentQueryChainWrapper = new LambdaQueryWrapper<>();
        commentQueryChainWrapper.eq(StringUtils.isNotNull(blogId), Comment::getBlogId, blogId);
        commentQueryChainWrapper.eq(Comment::getType, type);
        commentQueryChainWrapper.eq(Comment::isStatus, 1);
        return commentMapper.selectCount(commentQueryChainWrapper);
    }

    private Comment getComment(Long commentId) {
        return MapperUtils.lambdaQueryWrapper(commentMapper).eq(Comment::getCommentId, commentId).select(
                Comment::getCommentId, Comment::getBlogId, Comment::getType
        ).one();
    }

    private CommentReply getCommentReply(Long replyId) {
        return MapperUtils.lambdaQueryWrapper(blogCommentReplyMapper).eq(CommentReply::getReplyId, replyId).select(
                CommentReply::getReplyId, CommentReply::getBlogId, CommentReply::getType
        ).one();
    }

    /**
     * 删除缓存内容
     */
    void deleteCommentCache(Long blogId, Integer type) {

        int commentCount = onlyCommentCount(blogId, type);
        int size = 5;
        int totalPage = commentCount / size + 1;
        for (int i = 1; i < totalPage; i++) {
            String hk = RedisKey.getKey(blogId, COMMENT_LIST, i, type);
            boolean b = redisService.hHasKey(COMMENT_LIST, hk);
            if (b) {
                redisService.hdel(COMMENT_LIST, hk);
            }
        }
    }


}
