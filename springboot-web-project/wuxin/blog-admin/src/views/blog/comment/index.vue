<template>
  <CommentList
    :total="total"
    :query="query"
    :list-loading="listLoading"
    :list="list"
    @getList="getList"
    @handleSearch="handleFilter"
  >
    <el-select
      slot="pre"
      v-model="query.type"
      v-if="!isBlogComment"
      size="mini"
      style="width: 150px;"
      class="m-margin-left-small"
      @change="getList">
      <el-option :value="null" label="全部"></el-option>
      <el-option :value="1" label="文章"></el-option>
      <el-option :value="2" label="关于"></el-option>
      <el-option :value="3" label="友情链接"></el-option>
    </el-select>
  </CommentList>
</template>

<script>
import { minix } from '@/views/blog/comment/minix'
import {validNumber} from '@/utils/validate'
export default {
  name: 'Comment',
  mixins: [minix],
  props: {
    isBlogComment: {
      type: Boolean,
      default: false
    },
  },
  mounted() {
    if (this.isBlogComment){
      const blogId = this.$route.params.blogId
      if (!validNumber(blogId)) {
        this.$message.error('id格式不合法')
      } else {
        this.query.id = blogId
        this.getList()
      }
    } else {
      this.getList()
    }
  }
}
</script>
