<template>
  <div class="ui attached segment" :style="$MyBg()" >
    <div class="typo content">
      <Title :title="title" />
      <div class="ui middle aligned mobile reversed stackable">
        <div class=" m-padded-tb-small line-numbers match-braces rainbow-braces "
             v-viewer
             v-html="about.content"
        ></div>
      </div>
      <!--关于我的信息-->
      <div>
        <AboutComment v-if="about.commentEnabled" :commentEnabled="about.commentEnabled" />
        <h3 v-else>
          <span :style="$MyBg()">评论已关闭</span>
        </h3>
      </div>
    </div>
  </div>
</template>

<script>
import Title from "@/components/common/Title";
import AboutComment from "@/components/about/about-comment.vue";
import {getAbout,} from '@/api/about'

export default {
  name: "Blog",
  components: {
    Title,
    AboutComment
  },
  data() {
    return {
      title: "关于我",
      about:{}
    };
  },

  methods: {

    init() {
      getAbout().then(res => {
        this.about = res.result
        this.$nextTick(() => {
          this.$primsjs.highlightAll();
        });
      });
    },
  },

  mounted() {
    this.init();
  },
};
</script>

<style scoped>


.el-divider {
  margin: 1rem 0 !important;
}

h1::before,
h2::before,
h3::before,
h4::before,
h5::before,
h6::before {
  display: block;
  content: " ";
  height: 55px;
  margin-top: -55px;
  visibility: hidden;
}

</style>
