<template>
  <div>
    <BlogList :getList="getList" :blogList="blogList" :totalPage="totalPage" />
  </div>
</template>

<script>
import BlogList from "@/components/blog/BlogList";
import Title from "@/components/common/Title";

import {
  getBlogByCategoryName
} from "@/api/tag";
import {
  setTotalPage
} from '@/utils/validate'

export default {
  data() {
    return {
      blogList: [],
      getBlogListFinish: false,
      // 页面参数
      current: 1,
      totalPage: 2,
      categoryName: '随笔'
    }
  },
  components: {
    BlogList,
    Title
  },

  methods: {
    // 获取基本blog参数
    getList(current) {
      getBlogByCategoryName({
        'current': current,
        'limit': 5,
        'keywords': this.categoryName
      }).then(res => {
        this.blogList = res.result.records
        this.totalPage = setTotalPage(res.result.total, res.result.size)
        // this.totalPage = setTotalPage(res.)
        this.$nextTick(() => {
          this.$primsjs.highlightAll()
        })
      })
    },

  },

  beforeRouteEnter(to, from, next) {
    next(vm => {
      vm.categoryName = to.params.name
      vm.getList(vm.current)
    })
  },

  beforeRouteUpdate(to, from, next) {
    // this.getData(this.$route.params.blogId
    this.categoryName = to.params.name
    this.getList(this.current)
    next()
  }
}
</script>

