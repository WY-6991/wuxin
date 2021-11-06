package com.wuxin.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuxin.blog.mapper.vo.CommentUserMapper;
import com.wuxin.blog.mapper.vo.FriendCommentMapper;
import com.wuxin.blog.mapper.vo.FriendCommentReplyMapper;
import com.wuxin.blog.pojo.CommentUser;
import com.wuxin.blog.pojo.vo.FriendComment;
import com.wuxin.blog.pojo.vo.FriendCommentReply;
import com.wuxin.blog.service.FriendCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class FriendCommentServiceImpl implements FriendCommentService {


    @Autowired
    private FriendCommentMapper friendCommentMapper;

    @Autowired
    private FriendCommentReplyMapper friendCommentReplyMapper;

    @Autowired
    private CommentUserMapper commentUserMapper;

    @Override
    public void addFriendComment(FriendComment comment) {
        friendCommentMapper.insert(comment);
    }

    @Override
    public void updateFriendComment(FriendComment comment) {
        friendCommentMapper.updateById(comment);
    }

    @Override
    public void delFriendComment(Long commentId) {
        friendCommentMapper.deleteById(commentId);
    }

    @Override
    public IPage<FriendComment> findFriendComment(int current, int limit,String keywords) {

        // 获取是所有评论
        LambdaQueryChainWrapper<FriendComment> chainWrapper = new LambdaQueryChainWrapper<>(friendCommentMapper);
        Page<FriendComment> friendCommentPage = new Page<>(current, limit);

        Page<FriendComment> page = chainWrapper.orderByDesc(FriendComment::getCreateTime).like(!keywords.isEmpty(), FriendComment::getUsername, keywords)
                .page(friendCommentPage);
        //  遍历所有评论
        // if(page.getRecords().size()==0){
        //     return page;
        // }

        page.getRecords().forEach(comment->{
            // 获取用户
            CommentUser commentUser = commentUserMapper.selectById(comment.getCommentUserId());
            comment.setUsername(commentUser.getUsername());
            comment.setAvatar(commentUser.getAvatar());

            // 获取该条评论下的回复内容
            LambdaQueryChainWrapper<FriendCommentReply> queryReply = new LambdaQueryChainWrapper<>(friendCommentReplyMapper);
            List<FriendCommentReply> replyList = queryReply.eq(FriendCommentReply::getCommentId, comment.getCommentId()).list();

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

                // 获取到commentReplyList
                comment.setReplyList(replyList);
            }


        });



        return page;
    }

    @Override
    public void addFriendCommentReply(FriendCommentReply reply) {
        friendCommentReplyMapper.insert(reply);
    }

    @Override
    public void updateFriendCommentReply(FriendCommentReply reply) {
        friendCommentReplyMapper.updateById(reply);
    }

    @Override
    public void delFriendCommentReply(Long replyId) {
        friendCommentReplyMapper.deleteById(replyId);
    }

    @Override
    public void delFriendCommentReplyByCommentId(Long id) {
        LambdaQueryWrapper<FriendCommentReply> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FriendCommentReply::getCommentId,id);
        friendCommentReplyMapper.delete(queryWrapper);
    }
}
