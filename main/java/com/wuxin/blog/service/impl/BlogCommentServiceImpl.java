package com.wuxin.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuxin.blog.mapper.BlogMapper;
import com.wuxin.blog.mapper.UserMapper;
import com.wuxin.blog.mapper.vo.BlogCommentMapper;
import com.wuxin.blog.mapper.vo.BlogCommentReplyMapper;
import com.wuxin.blog.mapper.vo.CommentUserMapper;
import com.wuxin.blog.pojo.CommentUser;
import com.wuxin.blog.pojo.vo.BlogComment;
import com.wuxin.blog.pojo.vo.BlogCommentReply;
import com.wuxin.blog.service.BlogCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


@Service
public class BlogCommentServiceImpl implements BlogCommentService {

    @Autowired
    private BlogCommentMapper blogCommentMapper;

    @Autowired
    private BlogCommentReplyMapper blogCommentReplyMapper;

    // @Autowired
    // private UserMapper userMapper;
    //
    // @Autowired
    // private BlogMapper blogMapper;

    @Autowired
    private CommentUserMapper commentUserMapper;

    @Override
    public int addComment(BlogComment blogComment) {
        return blogCommentMapper.insert(blogComment);
    }

    @Override
    public int delComment(Long commentId) {
        return blogCommentMapper.deleteById(commentId);
    }

    @Override
    public int delCommentByUserId(Long userId) {
        LambdaQueryWrapper<BlogComment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BlogComment::getCommentUserId,userId);
        return blogCommentMapper.delete(queryWrapper);
    }

    @Override
    public int delCommentByBlogId(Long blogId) {
        LambdaQueryWrapper<BlogComment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BlogComment::getBlogId,blogId);
        return blogCommentMapper.delete(queryWrapper);
    }


    @Override
    public boolean updateComment(BlogComment blogComment) {
        return new LambdaUpdateChainWrapper<BlogComment>(blogCommentMapper)
                .eq(BlogComment::getCommentId, blogComment.getCommentId())
                .update(blogComment);
    }

    @Override
    public IPage<BlogComment> findBlogCommentByBlogId(Integer current, Integer size, Long blogId) {
        // 根据blogId获取获取对应commentId list
        Page<BlogComment> page = new LambdaQueryChainWrapper<BlogComment>(blogCommentMapper)
                .eq(BlogComment::getBlogId, blogId)
                .orderByDesc(BlogComment::getCreateTime)
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
    public Integer addReply(BlogCommentReply blogCommentReply) {
        return blogCommentReplyMapper.insert(blogCommentReply);
    }

    @Override
    public Integer delReply(Long replyId) {
       return blogCommentReplyMapper.deleteById(replyId);
    }


    @Override
    public Integer delReplyByUserId(Long userId) {
        LambdaQueryWrapper<BlogCommentReply> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BlogCommentReply::getReplyUserId,userId);
        return blogCommentReplyMapper.delete(queryWrapper);

    }

    @Override
    public int delCommentReplyByBlogId(Long blogId) {
        LambdaQueryWrapper<BlogCommentReply> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BlogCommentReply::getBlogId,blogId);
        return blogCommentReplyMapper.delete(queryWrapper);
    }


    @Override
    public int delCommentReplyByCommentId(Long commentId) {
        LambdaQueryWrapper<BlogCommentReply> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BlogCommentReply::getCommentId,commentId);
        return blogCommentReplyMapper.delete(queryWrapper);
    }

    @Override
    public Integer findCommentCount() {
        int integer = blogCommentMapper.selectCount(null);
        int integer1 = blogCommentReplyMapper.selectCount(null);
        return integer+integer1;
    }

    @Override
    public IPage<BlogComment> findBlogCommentByPage(Integer current, Integer limit, String keywords) {
        LambdaQueryChainWrapper<BlogComment> queryChainWrapper = new LambdaQueryChainWrapper<>(blogCommentMapper);
        queryChainWrapper.orderByDesc(BlogComment::getCreateTime);
        Page<BlogComment> commentPage = new Page<>(current,limit);
        Page<BlogComment> page = queryChainWrapper.like(!keywords.isEmpty(), BlogComment::getContent, keywords).page(commentPage);
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
    public IPage<BlogCommentReply> findBlogCommentReplyPage(Integer current, Integer limit, String keywords) {
        LambdaQueryChainWrapper<BlogCommentReply> chainWrapper = new LambdaQueryChainWrapper<BlogCommentReply>(blogCommentReplyMapper);
        Page<BlogCommentReply> commentPage = new Page<>(current,limit);
        Page<BlogCommentReply> page = new LambdaQueryChainWrapper<BlogCommentReply>(blogCommentReplyMapper).like(!keywords.isEmpty(), BlogCommentReply::getReplyContent, keywords).page(commentPage);
        page.getRecords().forEach(commentReply->{
            // 获取评论用户名
            commentReply.setReplyUsername(commentUserMapper.selectById(commentReply.getReplyUserId()).getUsername());
        });
        return page;
    }


    public void getReplyList(BlogComment blogComment){
        List<BlogCommentReply> replyList = new LambdaQueryChainWrapper<BlogCommentReply>(blogCommentReplyMapper)
                .eq(BlogCommentReply::getBlogId, blogComment.getBlogId())
                .eq(BlogCommentReply::getCommentId, blogComment.getCommentId())
                // .orderByDesc(CommentReply::getCreateTime)
                .list();

        System.out.println("replyList-------------------->"+replyList);
        if (replyList.size() != 0) { // 判断是否含有评论信息
            replyList.forEach(commentReply -> {
                // 获取回复人基本信息 用户名 头像等
                CommentUser replyUser = commentUserMapper.selectById(commentReply.getReplyUserId());
                commentReply.setReplyUsername(replyUser.getUsername());
                commentReply.setReplyAvatar(replyUser.getAvatar());

                CommentUser replyCommentUser = commentUserMapper.selectById(commentReply.getCommentUserId());
                commentReply.setCommentUsername(replyCommentUser.getUsername());
                // commentReply.setCommentAvatar(replyCommentUser.getAvatar());
            });
        }

        // 将reply list信息储存到comment中
        blogComment.setReplyList(replyList);
    }
}
