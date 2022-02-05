<template>
  <!--随机文章-->
  <div class="ui segments m-box" :style="$MyBg()"  v-if="randomBlogList&&randomBlogList.length!==0">
    <div class="ui secondary segment" :style="$MyBg()"><i class="bookmark icon"></i>随机文章</div>
    <div class="ui yellow segment" :style="$MyBg()">
      <div class="ui divided items">
        <div class="item" v-for="(blog,index) in randomBlogList" :key="index">
          <div class="content">
            <a href="javascript:;" :style="$MyBg()" @click.prevent="toBlog(blog)" class="header m-text-500">{{ blog.title }}</a>
            <div class="meta" >
              <router-link :to="`/category/${blog.category.name}`" >
                <i class="folder open icon" :style="$MyBg()"></i><span :style="$MyBg()"> {{blog.category.name}}</span>
              </router-link>
            </div>
            <div class="extra">
              <router-link :to="`/tag/${tag.name}`" class="ui label m-text-500" :class="tag.color"
                           v-for="(tag,i) in blog.tags" :key="i">{{ tag.name }}
              </router-link>
              <a href="javascript:;" @click.prevent="toBlog(blog)" class="ui right floated">
                阅读全文<i class="angle double right icon"></i>
              </a>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>

</template>

<script>
export default {
  name: "RandomBlog",
  props: {
    randomBlogList: {
      type: Array,
      required: true,
    },
  },
  methods: {
    toBlog(blog) {
      // this.$store.dispatch("goBlogPage", blog);
      this.$router.push(`/blog/${blog.blogId}`)
    },
  },
};
</script>

<style scoped>
.secondary.segment {
  padding: 10px;
}

.header {
  font-size: 16px !important;
}

.meta {
  margin: 10px 0 !important;
}

.meta a {
  color: #000 !important;
}
</style>
