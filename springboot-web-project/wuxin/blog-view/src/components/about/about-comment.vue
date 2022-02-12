<template lang="html">
  <div>
    <CommentForm :comment-type="commentType" @addComment="addMyComment" :type="query.type" :loading="loading" />
    <CommentList @addReply="addMyReply"
                 @setParentId="setParentId" :type="query.type" :id="query.blogId" :loading="loading"
                 :comment-list="commentList"
                 :commentCount="commentCount"
                 :parentCommentId="parentCommentId" ref="reply" />
    <MyPagination :current="current"  :is-scroll-top="false" :totalPage="totalPage" @current-page="changePage" />
  </div>
</template>

<script>
import CommentForm from "@/components/comment/CommentForm";
import CommentList from "@/components/comment/CommentList";
import {
  mapActions,
  mapState
} from "vuex";

export default {
  name: "AboutComment",
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
        type: 2,
        blogId: null
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

    getData() {
      if (this.commentEnabled) {
        this.getCommentList(this.query)
      }
    },


    setParentId(id) {
      this.setParentCommentId(id)
    },

    changePage(page) {
      // 设置页码 设置blogId
      this.setCurrent(page);
      // 加载commentList
      this.getData()
    },
  },

  mounted() {
    this.getData()
    this.setParentCommentId(-1)
  },
};
</script>
