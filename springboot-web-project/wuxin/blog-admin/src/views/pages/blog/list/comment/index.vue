<template>
  <CommentList
    :total="total"
    :query="query"
    :list-loading="listLoading"
    :list="list"
    @getList="getList"
    @delComment="delComment"
    @delReply="delReply"

  />
</template>

<script>

import {minix} from "@/views/pages/comment/minix";


export default {
  name: "BlogComment",
  mixins: [minix],
  mounted() {
    this.query.id = this.$route.params.blogId
  }

};
</script>
