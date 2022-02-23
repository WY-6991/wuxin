<template>
  <CommentList
    :total="total"
    :query="query"
    :list-loading="listLoading"
    :list="list"
    @getList="getList"
    @handleSearch="handleFilter"
  />
</template>

<script>
import { minix } from '@/views/pages/comment/minix'
import { validNumber } from '@/utils/validate'

export default {
  name: 'BlogComment',
  mixins: [minix],
  mounted() {
    const blogId = this.$route.params.blogId
    if (!validNumber(blogId)) {
      this.$message.error('id格式不合法')
      return
    } else {
      this.query.id = blogId
      this.getList()
    }
  }
}
</script>
