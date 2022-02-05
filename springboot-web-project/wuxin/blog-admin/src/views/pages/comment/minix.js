
import {delReply, delComment, updateComment, getCommentList, updateReply} from "@/api/comment";
import CommentList from "@/views/pages/comment/CommentList";
import {query} from "@/mixin/query";

export const minix =  {
  components: {CommentList},
  mixins:[query],
  data() {
    return {
      list: [],
      listLoading: true,
      total:0,
      query: {
        current: 1,
        limit: 10,
        type: 1,
        keywords: null,
        id: null
      },
    };
  },
  methods: {

    getList() {
      getCommentList(this.query).then(res => {
        this.list = res.result.records
        this.total = res.result.total
        this.listLoading = false
      })
    },

    delComment(cid) {
      delComment(cid).then(res => {
        if (res.code === 200) {
          this.$notify.success("删除成功！")
        }
      })
    },

    delReply(id) {
      delReply(id).then(res => {
        if (res.code === 200) {
          this.$notify.success("删除成功！")
        }
      })
    },
    updateComment(comment) {
      comment.status = !comment.status
      updateComment(comment).then(res => {
        if (res.code === 200) {
          this.$notify.success('操作成功')
          this.getData()
        }
      })
    },

    updateReply(repy) {
      repy.status = !repy.status
      updateReply(repy).then(res => {
        if (res.code === 200) {
          this.$notify.success('操作成功')
        }
      })
    }

  }


};
