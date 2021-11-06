package com.wuxin.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuxin.blog.mapper.vo.AboutCommentMapper;
import com.wuxin.blog.mapper.vo.AboutCommentReplyMapper;
import com.wuxin.blog.mapper.vo.CommentUserMapper;
import com.wuxin.blog.pojo.CommentUser;
import com.wuxin.blog.pojo.vo.AboutComment;
import com.wuxin.blog.pojo.vo.AboutCommentReply;
import com.wuxin.blog.service.AboutCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AboutCommentServiceImpl implements AboutCommentService {


    @Autowired
    private AboutCommentMapper aboutCommentMapper;

    @Autowired
    private AboutCommentReplyMapper aboutCommentReplyMapper;

    @Autowired
    private CommentUserMapper commentUserMapper;

    /*-------------------------------comment------------------------------------*/

    @Override
    public int addAboutComment(AboutComment aboutComment) {
        return aboutCommentMapper.insert(aboutComment);
    }

    @Override
    public int updateAboutComment(AboutComment aboutComment) {
        return aboutCommentMapper.updateById(aboutComment);
    }

    @Override
    public int delAboutComment(Long id) {
        return aboutCommentMapper.deleteById(id);
    }

    @Override
    public IPage<AboutComment> findAboutComment(int current, int limit) {
        LambdaQueryChainWrapper<AboutComment> aboutCommentLambdaQueryChainWrapper = new LambdaQueryChainWrapper<>(aboutCommentMapper);
        Page<AboutComment> commentPage = new Page<>(current, limit);
        Page<AboutComment> page = aboutCommentLambdaQueryChainWrapper.orderByDesc(AboutComment::getCreateTime).page(commentPage);

        page.getRecords().forEach(comment->{
            // 获取用户
            CommentUser commentUser = commentUserMapper.selectById(comment.getCommentUserId());
            comment.setUsername(commentUser.getUsername());
            comment.setAvatar(commentUser.getAvatar());

            // 获取该条评论下的回复内容
            LambdaQueryChainWrapper<AboutCommentReply> queryReply = new LambdaQueryChainWrapper<AboutCommentReply>(aboutCommentReplyMapper);
            List<AboutCommentReply> replyList = queryReply.eq(AboutCommentReply::getCommentId, comment.getCommentId()).list();

            // 是否含有回复,没有回复就不查询
            if(replyList.size()!=0){
                replyList.forEach(reply -> {
                    // 获取回复人
                    CommentUser replyUser = commentUserMapper.selectById(reply.getReplyUserId());
                    reply.setReplyUsername(replyUser.getUsername());
                    reply.setReplyAvatar(replyUser.getAvatar());

                    //获取被回复人
                    CommentUser replyCommentUser = commentUserMapper.selectById(reply.getCommentUserId());
                    reply.setCommentUsername(replyCommentUser.getUsername());
                    reply.setCommentAvatar(replyCommentUser.getAvatar());

                });
                comment.setReplyList(replyList);
            }


        });

        return page;
    }

    /*-------------------------------reply------------------------------------*/


    @Override
    public int addAboutCommentReply(AboutCommentReply reply) {
        return aboutCommentReplyMapper.insert(reply);
    }

    @Override
    public int updateAboutCommentReply(AboutCommentReply reply) {
        return aboutCommentReplyMapper.updateById(reply);
    }

    @Override
    public int delAboutCommentReply(Long replyId) {
        return aboutCommentReplyMapper.deleteById(replyId);
    }

    @Override
    public int delAboutCommentReplyByCommentId(Long commentId) {
        LambdaQueryWrapper<AboutCommentReply> replyQuery = new LambdaQueryWrapper<>();
        replyQuery.eq(AboutCommentReply::getCommentId,commentId);
        return aboutCommentReplyMapper.delete(replyQuery);
    }
}
