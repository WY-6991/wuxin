<template>
  <div class="ui small comments" style="max-width: 100%;">
    <div v-if="commentEnabled">
      <div v-if="commentList&&commentList.length!==0">
        <h3 class="ui dividing header" :style="bgColor">共 {{ commentCount }} 条评论 </h3>
        <div class="comment" v-for="(comment,index) in commentList">
          <a class="avatar" :style="bgColor">
            <!--        <img :src="comment.avatar" v-if="comment.avatar">-->
            <svg t="1644976238253" class="icon" viewBox="0 0 1024 1024"
                 xmlns="http://www.w3.org/2000/svg"
                 width="44" height="44">
              <path
                  d="M510.238 4.399C233.571 4.399 7.21 230.761 7.21 507.427c0 276.661 226.362 503.023 503.028 503.023 276.661 0 503.023-226.362 503.023-503.023 0-276.668-226.362-503.028-503.023-503.028z m12.572 150.907c191.779 0 191.779 144.623 191.779 226.362 0 81.745-75.455 232.652-191.779 235.793-113.182 0-191.779-150.907-191.779-235.793 0.001-81.74 0.001-226.362 191.779-226.362z m-12.572 826.85c-144.623 0-276.667-66.026-364.695-169.769 12.574-31.441 28.293-66.026 50.303-84.886 47.156-37.73 188.631-100.608 188.631-100.608l88.03 169.774 15.721-40.873-25.152-50.303 50.303-50.299 50.303 50.299-22.011 53.446 12.578 40.874 91.172-166.627s141.475 62.877 188.636 100.608c22.004 15.716 37.725 44.015 47.157 69.161-84.884 110.039-220.071 179.204-370.977 179.204z m0 0z"
                  fill="#cdcdcd" p-id="4574"></path>
            </svg>
          </a>
          <div class="content m-padding-left-small" :style="bgColor">
            <a class="author" :style="bgColor">{{ comment.username }}</a>
            <a class="ui label orange left pointing  mini m-margin-left-small">作者</a>
            <div class="text" :style="bgColor">
              <a class="ui label basic pink mini" v-if="comment.top!==0">置顶</a>
              {{ comment.content }}
            </div>
            <div class="actions">
              <span class="metadata date" :style="bgColor">{{ comment.createTime | formatDateTime }}</span>
              <a class="reply " :style="bgColor" @click.prevent="showReply(comment)"
                 v-if="parentCommentId===-1||parentCommentId!==comment.commentId">回复</a>
              <a class="reply " :style="bgColor" @click.prevent="closeReply(-1)"
                 v-if="parentCommentId===comment.commentId">取消</a>
              <CommentForm
                  v-if="parentCommentId===comment.commentId"
                  :comment-type="commentType"
                  :comment-user-id="comment.commentUserId"
                  :comment-id="comment.commentId"
                  :loading="loading"
                  :id="id"
                  :type="type"
                  :placeholder="placeholder"
                  @addReply="addReply"
              />

            </div>

            <div class="comments" v-for="(reply,replyIndex) in comment.replyList" :key="reply.replyId">
              <div class="comment" v-if="replyIndex<3||loadingList[comment.commentId]">
                <a class="avatar">
                  <!--              <img :src="reply.replyAvatar">-->
                  <svg t="1644976238253" class="icon" viewBox="0 0 1024 1024"
                       xmlns="http://www.w3.org/2000/svg"
                       width="32" height="32">
                    <path
                        d="M510.238 4.399C233.571 4.399 7.21 230.761 7.21 507.427c0 276.661 226.362 503.023 503.028 503.023 276.661 0 503.023-226.362 503.023-503.023 0-276.668-226.362-503.028-503.023-503.028z m12.572 150.907c191.779 0 191.779 144.623 191.779 226.362 0 81.745-75.455 232.652-191.779 235.793-113.182 0-191.779-150.907-191.779-235.793 0.001-81.74 0.001-226.362 191.779-226.362z m-12.572 826.85c-144.623 0-276.667-66.026-364.695-169.769 12.574-31.441 28.293-66.026 50.303-84.886 47.156-37.73 188.631-100.608 188.631-100.608l88.03 169.774 15.721-40.873-25.152-50.303 50.303-50.299 50.303 50.299-22.011 53.446 12.578 40.874 91.172-166.627s141.475 62.877 188.636 100.608c22.004 15.716 37.725 44.015 47.157 69.161-84.884 110.039-220.071 179.204-370.977 179.204z m0 0z"
                        fill="#cdcdcd" p-id="4574"></path>
                  </svg>
                </a>
                <div class="content">
                  <a class="author" :style="bgColor">{{ reply.replyUsername }}</a>
                  <a class="ui label orange left pointing mini m-margin-left-small">作者</a>
                  <div class="text" :style="bgColor">
                    <a class="author m-padding-right-small" :style="bgColor"
                       v-if="reply.replyUsername!==reply.commentUsername"> @ {{
                        reply.commentUsername
                      }}</a>{{ reply.replyContent }}
                  </div>
                  <div class="actions">
                    <span class="metadata date" :style="bgColor">{{ reply.createTime | formatDateTime }}</span>
                    <a class="reply " :style="bgColor" @click.prevent="showReply1(reply)"
                       v-if="parentCommentId===-1||parentCommentId!==reply.replyId">回复</a>
                    <a class="reply" :style="bgColor" @click.prevent="closeReply(-1)"
                       v-if="parentCommentId===reply.replyId">取消</a>

                    <CommentForm
                        v-if="reply.replyId===parentCommentId"
                        :comment-type="commentType"
                        :loading="loading"
                        :comment-id="comment.commentId"
                        :id="id"
                        :comment-user-id="reply.commentUserId"
                        :type="type"
                        @addReply="addReply"
                    />
                  </div>
                </div>
              </div>
            </div>
            <div class="comments m-more" :style="bgColor"
                 v-if="comment.replyList&&comment.replyList.length>3&&!loadingList[comment.commentId]">
              <span class="metadata" :style="bgColor">共计{{ comment.replyList.length }}条回复</span>
              <a class="author m-loading-more-link" :style="bgColor"
                 @click.prevent="loadingMore(comment.commentId)">点击加载更多</a>
            </div>
          </div>
        </div>
      </div>
      <h3 class="ui dividing header" v-else>~暂无评论~ </h3>
    </div>
    <h3 class="ui dividing header" v-else>~评论已关闭~ </h3>
  </div>
</template>

<script>
import CommentForm from "@/components/comment/CommentForm";
import {getDateDiff} from "@/utils/validate.js";

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
      placeholder: '评论千万条，文明第一条',
      loadingList: {}
    };
  },

  filters: {
    formatDateTime(value) {
      return getDateDiff(value);
    },
  },
  props: {
    commentEnabled: {
      type: Boolean,
      default: true
    },
    commentList: {
      type: Array,
      default: () => {
        return []
      }
    },
    commentCount: {
      type: Number,
      default: 0
    },
    parentCommentId: {
      type: Number,
      default: -1
    },
    type: {
      type: Number | String,
      default: -1,
    },

    loading: {
      type: Boolean,
      default: false
    },
    id: {
      type: Number | String,
      default: null,
    }
  },

  methods: {


    showReply(comment) {
      console.log(comment);
      this.placeholder = `回复 @${comment.username}`
      this.$emit("setParentId", comment.commentId);
    },

    showReply1(reply) {
      console.log(reply);
      this.placeholder = `回复 @${reply.commentUsername}`
      this.$emit("setParentId", reply.replyId);
    },

    closeReply() {
      this.$emit("setParentId", -1);
    },

    addReply(reply) {
      this.$emit("addReply", reply);
    },

    loadingMore(commentId) {
      if (commentId === null || commentId === '') {
        return
      }
      if (this.loadingList[commentId]) {
        return;
      }
      // 使用set方法给loading对象添加属性 this.laodingList 形式为:{1:1,1}
      this.$set(this.loadingList, commentId, commentId)

    }

  }

};
</script>

<style scoped>


.typo a {
  border-bottom: 1px solid #fff;
}

.reply {
  padding-left: 20px !important;
}

.m-padding-right-small {
  padding-right: 10px !important;
}

.m-padding-left-small {
  padding-left: 10px !important;
}

.ui.comments .comment .comments {
  margin: 1em 0 .5em .5em !important;
  padding: 0 0.2em !important;
}

.m-more {
  margin-top: 20px;
}

.m-loading-more-link {
  margin-left: 20px !important;
}

.m-loading-more-link:hover {
  color: rgba(0, 51, 255, 0.93) !important;
}


</style>
