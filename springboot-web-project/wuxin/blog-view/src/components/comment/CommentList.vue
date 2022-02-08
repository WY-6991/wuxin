<template>
  <div class="ui container m-comment-list">
    <sui-comment-group threaded ref="reference" style=" padding: 0 20px" >
      <h3 is="sui-header" style="padding-top: 10px;" :style="bgColor">{{ commentCount }}条评论</h3>
      <sui-divider style="width:100%;"  />

      <!----------------------------------------------commentList--------------------------------------->
      <sui-comment v-for="(comment,$index) in commentList" :key="comment.commentId">

        <sui-comment-avatar :src="comment.avatar" style="border-radius: 50%" circular />
        <div class="ui left pointing label orange mini" v-if="comment.commentUserId===1">作者</div>
        <sui-comment-content>
          <!--username-->
          <a is="sui-comment-author" :style="bgColor">{{ comment.username }}</a>

          <sui-comment-metadata>
            <div :style="bgColor">{{ comment.createTime | formatDateTime }}</div>
            <!--button-->
            <label class="ui label blue mini m-comment-button" @click="showReply(comment.commentId)"
                   v-if="parentCommentId===-1||parentCommentId!==comment.commentId">回复</label>
            <label class="ui label info mini m-comment-button" @click="showReply(-1)"
                   v-if="parentCommentId===comment.commentId">取消</label>
          </sui-comment-metadata>
          <sui-comment-text :style="bgColor">
            {{ comment.content }}
          </sui-comment-text>

          <CommentForm v-if="parentCommentId===comment.commentId" :comment-type="commentType"
                       :comment-id="comment.commentId" :id="id" :comment-user-id="comment.commentUserId"
                       @addReply="addReply" :ref="comment.commentId" :type="type" />

        </sui-comment-content>

        <!-- ----------------------------------------------replyList -------------------------------------------------------------------------->
        <sui-comment-group v-for="reply in comment.replyList" :key="reply.replyId">
          <sui-comment>
            <sui-comment-avatar :src="reply.replyAvatar" />
            <div class="ui left pointing label orange mini" v-if="comment.commentUserId===1">作者</div>
            <sui-comment-content>
              <a is="sui-comment-author" :style="bgColor">{{ reply.replyUsername }}</a>
              <sui-comment-metadata>
                <div :style="bgColor">{{ reply.createTime | formatDateTime }}</div>
              </sui-comment-metadata>
              <sui-comment-text>
                <div class="reply_content" :style="bgColor">
                  @<a is="sui-comment-author" class="no-decoration" :style="bgColor"> {{ reply.commentUsername }} : </a>
                  <span :style="bgColor"> {{ reply.replyContent }} </span>
                </div>

                <!-- button -->
                <div class="m-padded-tb-mini">
                  <label class="ui label blue mini m-comment-button" @click="showReply(reply.replyId)"
                         v-if="parentCommentId===-1||parentCommentId!==reply.replyId">回复</label>
                  <label class="ui label info mini m-comment-button" @click="showReply(-1)"
                         v-if="parentCommentId===reply.replyId">取消</label>
                </div>

              </sui-comment-text>

            </sui-comment-content>
            <CommentForm v-if="reply.replyId===parentCommentId" :comment-type="commentType"
                         :comment-id="comment.commentId" :id="id" :comment-user-id="reply.commentUserId" :type="type"
                         @addReply="addReply" />
          </sui-comment>
        </sui-comment-group>

      </sui-comment>

      <h3 is="sui-header" :style="bgColor" v-if="commentCount===0" style="padding-bottom: 20px">~暂无评论,快来占楼吧~</h3>

    </sui-comment-group>

  </div>
</template>

<script>
import CommentForm from "@/components/comment/CommentForm";
import {
  getDateDiff
} from "@/utils/validate.js";
import {
  mapActions
} from "vuex";

export default {
  name: "CommentList",
  components: {
    CommentForm,
  },
  data() {
    return {
      commentType: "reply",
      commentId: 0,
      replyId: 0,
    };
  },

  filters: {
    formatDateTime(value) {
      return getDateDiff(value);
    },
  },

  props: [
    "id",
    "commentList",
    "commentCount",
    "parentCommentId",
    "userId",
    "type",
  ],

  watch: {
    userId() {
      this.setUserId();
    },
  },

  methods: {
    //  展示评论回复内容框
    showReply(commentId) {
      this.$emit("setParentId", commentId);
    },
    addReply(reply) {
      this.$emit("addReply", reply);
    },

    deleteReply(replyId) {
      this.$emit("deleteReply", replyId);
    },

    deleteComment(commentId) {
      this.$emit("deleteComment", commentId);
    },

    setUserId() {
      sessionStorage.setItem("userId", this.userId);
    },

    cleanContent() {
      console.log(this.$refs.reference);
    },

    commentUserId() {
      console.log("set comment user id");
    },
  },

  created() {
    this.setUserId();
  },
};
</script>

<style scoped>

.m-comment-list {
  width: 100% !important;
  height: auto !important;
}

.m-comment-button {
  cursor: pointer !important;
}

div.avatar > img {
  border-radius: 50% !important;
}

.ui.threaded.comments .comment .comments {
  margin: -1em 0 -1em 1.25em;
  padding: 1em 0 1em 2.25em;
}

.ui.comments .comment .avatar img,
.ui.comments .comment img.avatar {
  display: block;
  margin: 0 auto;
  width: 100%;
  height: 100%;
  border-radius: 50% !important;
}

.comment_list {
  color: orangered !important;
  cursor: pointer !important;
  display: none;
}

.comment_list:hover {
  display: block;
}

.typo a {
  border-bottom: 1px solid #fff;
}
</style>
