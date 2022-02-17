<template lang="html">
  <div>
    <CommentForm
        @addComment="addMyComment"
        :comment-type="commentType"
        :loading="loading"
        :type="type" />
    <CommentList
        @addReply="addMyReply"
        @setParentId="setParentId"
        :loading="loading"
        :type="type"
        :comment-list="commentList"
        :commentCount="commentCount"
        :comment-enabled="commentEnabled"
        :parentCommentId="parentCommentId"
    />
    <MyPagination
        :current="query.current"
        :is-scroll-top="false"
        :total-page="totalPage"
        :get-list="changePage"
        @current-page="changePage"
    />


  </div>
</template>

<script>
import CommentForm from "@/components/comment/CommentForm";
import CommentList from "@/components/comment/CommentList";
import {mapActions} from "vuex";
import {addComment, addReply, getCommentList} from "@/api/comment";
import {SET_CLEAN_CONTENT} from "@/store/mutations-type";


export default {
  name: "Comment",
  components: {
    CommentList,
    CommentForm,
  },
  props: {
    commentEnabled: {
      type: Boolean,
      default: true
    },
    type: {
      type: Number | String,
      default: -1
    },
    id: {
      type: Number,
      default: null
    }
  },
  data() {
    return {
      commentType: 'comment',
      query: {
        current: 1,
        limit: 10
      },
      loading: false,
      totalPage: 0,
      parentCommentId: -1,
      commentCount: 0,
      commentList: [],
    };
  },


  methods: {

    ...mapActions('comment', ['setCommentUser']),

    addMyComment(comment) {
      this.setLoading(true)
      addComment(comment).then((res) => {
        if (res.code === 200) {
          this.setLoading(false)
          this.$notify.success("发表成功！");
          // 清空发布内容
          this.$store.commit('comment/' + SET_CLEAN_CONTENT, null)
          this.getList()
        }
      }).then(() => {
        this.setCommentUser({
          "username": comment.username,
          "email": comment.email,
          "subscription": comment.subscription
        })

      }).catch(() => {
        this.errorLoading()
      });
    },

    addMyReply(reply) {
      this.setLoading(true)
      addReply(reply).then((res) => {
        if (res.code === 200) {
          // 清空发布内容
          this.$store.commit('comment/' + SET_CLEAN_CONTENT, null)
          this.setLoading(false)
          this.$notify.success("发表成功！");
          this.getList()
        }
      }).then(() => {
        this.setCommentUser({
          "username": reply.replyUsername,
          "email": reply.replyEmail,
          "subscription": reply.subscription
        })
      }).catch(() => {
        this.errorLoading()
      })
    },


    getList() {
      if (this.commentEnabled) {
        getCommentList(this.query.current, this.query.limit, this.type, this.id).then(res => {
          if (res.code === 200) {
            const {page, commentCount} = res
            const {records, pages} = page
            this.commentList = records
            this.totalPage = pages
            this.commentCount = commentCount
          }
        })
      }
    },

    setParentId(id) {
      this.parentCommentId = id
    },
    setCurrent(current) {
      this.query.current = current
    },
    setLoading(loading) {
      this.loading = loading
    },
    errorLoading() {
      setTimeout(() => {
        this.setLoading(false)
      }, 5000)
    },

    changePage(page) {
      this.setCurrent(page)
      this.getList()
    },
  },

  created() {
    this.getList()
  },
};
</script>
