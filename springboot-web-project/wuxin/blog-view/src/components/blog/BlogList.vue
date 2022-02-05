<template>
  <div>
    <!--content-->
    <BlogItem :blogList="blogList" v-viewer />
    <!--分页-->
    <MyPagination :getList="getList" :totalPage="totalPage" :isScrollTop="true" />
  </div>
</template>

<script>
import BlogItem from "./BlogItem";


export default {
  name: "BlogList",
  components: {BlogItem},
  props: {
    getList: {
      type: Function,
      default: () => {
      }
    },
    blogList: {
      type: Array,
      default: []

    },
    totalPage: {
      type: Number,
      default: 0
    },
  },
};
</script>


