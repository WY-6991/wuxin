<template>
  <div>
    <BlogList :getList="getList" :blogList="blogList" :totalPage="totalPage" />
  </div>
</template>

<script>
import BlogList from "@/components/blog/BlogList";
import { getBlogListByTagName } from "@/api/tag";

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
      getBlogListByTagName(this.query).then((res) => {
        if (res.code == 200) {
          const { records, pages } = res.result;
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
      vm.getList(vm.query.current);
    });
  },

  beforeRouteUpdate(to, from, next) {
    this.getList(this.query.current);
    next();
  },
};
</script>

