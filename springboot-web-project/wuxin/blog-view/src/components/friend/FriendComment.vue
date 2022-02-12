<template lang="html">
  <div>
    <CommentForm :comment-type="commentType" @addComment="addMyComment" :loading="loading" :type="query.type" />
    <CommentList @addReply="addMyReply" @setParentId="setParentId" :loading="loading" :type="query.type"
                 :comment-list="commentList"
                 :commentCount="commentCount" :parentCommentId="parentCommentId"
    />
    <MyPagination :current="current" :total-page="totalPage" @current-page="changePage" :is-scroll-top="false" />
  </div>
</template>

<script>
import CommentForm from "@/components/comment/CommentForm";
import CommentList from "@/components/comment/CommentList";
import {mapActions, mapState} from "vuex";

export default {
  name: "FriendComment",
  components: {
    CommentList,
    CommentForm,
  },
  props: {
    commentEnabled: {
      type: Boolean,
      default: true
    },
  },
  data() {
    return {
      commentType: "comment",
      query: {
        type: 3,
        blogId: ''
      }
    };
  },

  computed: {
    ...mapState("comment", [
      "current",
      "commentList",
      "totalPage",
      "parentCommentId",
      "commentCount",
      "loading"
    ]),
  },
  methods: {
    ...mapActions("comment", [
      "addComment",
      "addReply",
      "getCommentList",
      "setCurrent",
      "setParentCommentId",
    ]),

    addMyComment(comment) {
      this.addComment(comment).then((res) => {
        if (res.code === 200) {
          this.$notify.success("发表成功！");
          this.getData()
        }
      });
    },

    addMyReply(reply) {
      this.addReply(reply).then((res) => {
        if (res.code === 200) {
          this.$notify.success("发表成功！");
          this.getData()
        }
      });
    },

    setParentId(id) {
      this.setParentCommentId(id);
    },

    getData() {
      if (this.commentEnabled) {
        this.getCommentList(this.query)
      }
    },

    changePage(page) {
      // 设置页码 设置
      this.setCurrent(page);
      // 加载commentList
      this.getData()
    },
  },

  mounted() {
    this.getData()
    this.setParentCommentId(-1);
  },
};
</script>
