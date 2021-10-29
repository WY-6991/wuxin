package com.wuxin.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuxin.blog.mapper.BlogMapper;
import com.wuxin.blog.mapper.CommentMapper;
import com.wuxin.blog.mapper.UserMapper;
import com.wuxin.blog.mapper.vo.CommentReplyMapper;
import com.wuxin.blog.mapper.vo.CommentUserMapper;
import com.wuxin.blog.pojo.Comment;
import com.wuxin.blog.pojo.CommentUser;
import com.wuxin.blog.pojo.User;
import com.wuxin.blog.pojo.vo.BlogTag;
import com.wuxin.blog.pojo.vo.CommentReply;
import com.wuxin.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private CommentReplyMapper commentReplyMapper;

    @Autowired
    private UserMapper userMapper;


    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private CommentUserMapper commentUserMapper;

    @Override
    public int addComment(Comment comment) {
        return commentMapper.insert(comment);
    }

    @Override
    public int delComment(Long commentId) {
        return commentMapper.deleteById(commentId);
    }

    @Override
    public int delCommentByUserId(Long userId) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getCommentUserId,userId);
        return commentMapper.delete(queryWrapper);
    }

    @Override
    public int delCommentByBlogId(Long blogId) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getBlogId,blogId);
        return commentMapper.delete(queryWrapper);
    }


    @Override
    public boolean updateComment(Comment comment) {
        return new LambdaUpdateChainWrapper<Comment>(commentMapper)
                .eq(Comment::getCommentId, comment.getCommentId())
                .update(comment);
    }

    @Override
    public IPage<Comment> findBlogCommentByBlogId(Integer current, Integer size, Long blogId) {
        // 根据blogId获取获取对应commentId list
        Page<Comment> page = new LambdaQueryChainWrapper<Comment>(commentMapper)
                .eq(Comment::getBlogId, blogId)
                .orderByDesc(Comment::getCreateTime)
                .page(new Page<>(current, size));

        // 获取评论用户Id名头像等基本信息
        page.getRecords().forEach(comment -> {

            // 用户基本信息 用户名 用户头像

            CommentUser commentUser = commentUserMapper.selectById(comment.getCommentUserId());
            comment.setUsername(commentUser.getUsername());
            comment.setAvatar(commentUser.getAvatar());


            // 对应comment 的reply list
            // List<CommentReply> replyList = new ArrayList<>();
            // List<CommentReply> list = new LambdaQueryChainWrapper<CommentReply>(commentReplyMapper)
            //         .eq(CommentReply::getBlogId, comment.getBlogId())
            //         .eq(CommentReply::getCommentId, comment.getCommentId())
            //         // .orderByDesc(CommentReply::getCreateTime)
            //         .list();
            //
            // if (list.size() != 0) { // 判断是否含有评论信息
            //     list.forEach(commentReply -> {
            //         // 获取回复人基本信息 用户名 头像等
            //         CommentUser replyUser = commentUserMapper.selectById(commentReply.getReplyUserId());
            //         commentReply.setReplyNickName(replyUser.getUsername());
            //         commentReply.setReplyAvatar(replyUser.getAvatar());
            //
            //         CommentUser replyCommentUser = commentUserMapper.selectById(commentReply.getCommentUserId());
            //         commentReply.setCommentUsername(replyCommentUser.getUsername());
            //         commentReply.setCommentAvatar(replyCommentUser.getAvatar());
            //     });
            // }
            //
            // // 将reply list信息储存到comment中
            // comment.setCommentReplyList(list);

            getReplyList(comment);
        });

        // 获取blog
        return page;
    }

    @Override
    public Integer addReply(CommentReply commentReply) {
        return commentReplyMapper.insert(commentReply);
    }

    @Override
    public Integer delReply(Long replyId) {
       return commentReplyMapper.deleteById(replyId);
    }


    @Override
    public Integer delReplyByUserId(Long userId) {
        LambdaQueryWrapper<CommentReply> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CommentReply::getReplyUserId,userId);
        return commentReplyMapper.delete(queryWrapper);

    }

    @Override
    public int delCommentReplyByBlogId(Long blogId) {
        LambdaQueryWrapper<CommentReply> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CommentReply::getBlogId,blogId);
        return commentReplyMapper.delete(queryWrapper);
    }


    @Override
    public int delCommentReplyByCommentId(Long commentId) {
        LambdaQueryWrapper<CommentReply> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CommentReply::getCommentId,commentId);
        return commentReplyMapper.delete(queryWrapper);
    }

    @Override
    public Integer findCommentCount() {
        int integer = commentMapper.selectCount(null);
        int integer1 = commentReplyMapper.selectCount(null);
        return integer+integer1;
    }

    @Override
    public IPage<Comment> findBlogCommentByPage(Integer current, Integer limit, String keywords) {
        LambdaQueryChainWrapper<Comment> queryChainWrapper = new LambdaQueryChainWrapper<>(commentMapper);
        queryChainWrapper.orderByDesc(Comment::getCreateTime);
        Page<Comment> commentPage = new Page<>(current,limit);
        Page<Comment> page = queryChainWrapper.like(!keywords.isEmpty(), Comment::getContent, keywords).page(commentPage);
        page.getRecords().forEach(comment->{
            // 获取评论用户名
            CommentUser commentUser = commentUserMapper.selectById(comment.getCommentUserId());
            comment.setUsername(commentUser.getUsername());
            comment.setAvatar(commentUser.getAvatar());

            getReplyList(comment);

        });
        return page;

    }

    @Override
    public IPage<CommentReply> findBlogCommentReplyPage(Integer current, Integer limit, String keywords) {
        LambdaQueryChainWrapper<CommentReply> chainWrapper = new LambdaQueryChainWrapper<CommentReply>(commentReplyMapper);
        Page<CommentReply> commentPage = new Page<>(current,limit);
        Page<CommentReply> page = new LambdaQueryChainWrapper<CommentReply>(commentReplyMapper).like(!keywords.isEmpty(), CommentReply::getReplyContent, keywords).page(commentPage);
        page.getRecords().forEach(commentReply->{
            // 获取评论用户名
            commentReply.setReplyNickName(commentUserMapper.selectById(commentReply.getReplyUserId()).getUsername());
        });
        return page;
    }


    public void getReplyList(Comment comment){
        List<CommentReply> replyList = new LambdaQueryChainWrapper<CommentReply>(commentReplyMapper)
                .eq(CommentReply::getBlogId, comment.getBlogId())
                .eq(CommentReply::getCommentId, comment.getCommentId())
                // .orderByDesc(CommentReply::getCreateTime)
                .list();

        if (replyList.size() != 0) { // 判断是否含有评论信息
            replyList.forEach(commentReply -> {
                // 获取回复人基本信息 用户名 头像等
                CommentUser replyUser = commentUserMapper.selectById(commentReply.getReplyUserId());
                commentReply.setReplyNickName(replyUser.getUsername());
                commentReply.setReplyAvatar(replyUser.getAvatar());

                CommentUser replyCommentUser = commentUserMapper.selectById(commentReply.getCommentUserId());
                commentReply.setCommentUsername(replyCommentUser.getUsername());
                commentReply.setCommentAvatar(replyCommentUser.getAvatar());
            });
        }

        // 将reply list信息储存到comment中
        comment.setCommentReplyList(replyList);
    }
}
