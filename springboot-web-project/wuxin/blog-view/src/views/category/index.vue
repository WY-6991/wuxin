<template>
  <div>
    <BlogList :getList="getList" :blogList="blogList" :totalPage="totalPage" />
  </div>
</template>

<script>
import BlogList from "@/components/blog/BlogList";
import { getBlogByCategoryName } from "@/api/tag";

export default {
  data() {
    return {
      blogList: [],
      totalPage: 0,
      query: {
        current: 1,
        limit: 5,
        keywords: "",
      },
    };
  },
  components: {
    BlogList,
  },

  methods: {

    getList(current) {
      this.query.current = current;
      this.query.keywords = this.$router.params.name;
      getBlogByCategoryName(this.query).then((res) => {
        if (res.code === 200) {
          const { pages, records } = res.result;
          this.blogList = records;
          this.totalPage = pages;
        }
        this.$nextTick(() => {
          this.$primsjs.highlightAll();
        });
      });
    },
  },

  beforeRouteEnter(to, from, next) {
    next((vm) => {
      vm.getList(vm.current);
    });
  },

  beforeRouteUpdate(to, from, next) {
    this.getList(this.current);
    next();
  },
};
</script>

