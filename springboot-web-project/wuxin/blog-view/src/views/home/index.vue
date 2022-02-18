<template>
  <div style="background: rgba(0,0,0,0.1)">
    <BlogList :getList="getBlogList" :blogList="blogList" :totalPage="totalPage" />
  </div>
</template>

<script>
import BlogList from "@/components/blog/BlogList";
import {getBlogList} from "@/api/blog";
import {formatLink} from "@/utils/link";

export default {
  data() {
    return {
      blogList: [],
      totalPage: 0,
      loading: false,
      pageNum: 1
    }
  },
  components: {
    BlogList,
  },


  methods: {

    getBlogList(pageNum) {
      getBlogList(pageNum).then(res => {
        if (res.code === 200) {
          const {records, pages} = res.result
          records.forEach(blog => {
            blog.description = formatLink(blog.description)
          })
          this.blogList = records
          this.totalPage = pages
          this.$nextTick(() => {
            //  加载代码高亮插件
            this.$primsjs.highlightAll()
          })
          this.loading = true
        } else {
          this.$message.error('请求失败，未知错误！')
        }
      }).catch(() => {
        this.$message.error("请求失败")
      })
    },


  },
  created() {

    // 获取首页数据请求
    this.getBlogList(this.pageNum)
  },

}
</script>

<style scoped>
</style>
