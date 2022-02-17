<template>
  <div>
    <div class="ui attached segment typo content blog" v-if="blog" :style="bgColor">
      <!--文章是否加密-->
      <div class="m-padded-tb-large placeholder"
           v-if="!result.secrecy || result.password === password">
        <transition-group enter-active-class="animate__animated animate__fadeIn">
          <div :key="1">
            <div class="ui large red right corner label" v-if="blog.top">
              <i class="arrow alternate circle up icon"></i>
            </div>
            <div class="ui middle aligned mobile reversed">
              <div class="ui grid m-margin-lr">
                <!--标题-->
                <div class="row m-padded-tb-small">
                  <h2 class="ui header m-center" :style="bgColor">{{ blog.title }}</h2>
                </div>
                <!--文章简要信息-->
                <div class="row m-padded-tb-small">
                  <div class="ui horizontal link list m-center">
                    <div class="item m-datetime">
                      <i class="small calendar icon"></i><span>{{ blog.createTime | formatDateTime }}</span>
                    </div>
                    <div class="item m-views">
                      <i class="small eye icon"></i><span>{{ blog.views }}</span>
                    </div>

                    <a class="item m-common-black" @click.prevent=" $store.state.setting.focusMode = !$store.state.setting.focusMode
                    ">
                      <div class="item m-common-black" :data-inverted="['inverted']" data-tooltip="纯净模式"
                           data-position="top center">
                        <i class="laptop icon" :style="bgColor"></i>
                      </div>
                    </a>

                    <a class="item m-common-black" @click.prevent="bigFontSize = !bigFontSize">
                      <div data-inverted="inverted" data-tooltip="点击切换字体大小" data-position="top center">
                        <i class="font icon" :style="bgColor"></i>
                      </div>
                    </a>
                  </div>
                </div>
                <!--分类-->


                <router-link :to="`/category/${blog.category.name}`"
                             :class=" `ui label  ${blog.category.color?blog.category.color:'teal'} ribbon `"
                             style="color: white;text-decoration: none;">
                  {{ blog.category.name }}
                </router-link>

                <!--文章Markdown正文-->
                <div class="
                  typo
                  m-padded-tb-small
                  line-numbers
                  match-braces
                  rainbow-braces
                  blog-js-toc-content
                " :class="{ 'm-big-fontsize': bigFontSize }" v-html="blog.content" v-viewer></div>

                <!--赞赏-->
                <div class="ui center aligned container m-padded-tb-small" v-if="blog.appreciation">
                  <Appreciate :alipay="chatUrl.alipay" :wechatPayment="chatUrl.wetchatPayment" />
                </div>

                <el-divider />
                <div class="row m-padded-tb-no">
                  <div class="column m-padding-left-no">
                    <router-link :to="`/tag/${tag.name}`" class="ui tag label m-text-500 m-margin-small"
                                 :class="tag.color?tag.color:'teal'" v-for="(tag, index) in blog.tags" :key="index">
                      {{ tag.name }}
                    </router-link>
                  </div>
                </div>

                <el-divider />
              </div>
            </div>

            <div class="ui positive message" :style="bgColor">
              <div class="ui grid">
                <div class="thirteen wide computer sixteen wide mobile column">
                  <div class="float left">
                    <ul class="list">
                      <li>作者: {{ blog.username }}</li>
                      <li>发表时间 : {{ blog.createTime }}</li>
                      <li v-if="blog.updateTime">
                        最后修改：{{ blog.updateTime }}
                      </li>
                      <li>
                        本站点采用<a href="https://creativecommons.org/licenses/by/4.0/" target="_blank">
                        署名 4.0 国际 (CC BY 4.0) </a>创作共享协议。可自由转载、引用，并且允许商业性使用。但需署名作者且注明文章出处。
                      </li>
                    </ul>
                  </div>
                </div>
                <div class="three wide computer sixteen wide mobile column ">
                  <div class="ui center aligned" style="text-align: center">
                    <BlogCode :text="blogUrl" />
                  </div>
                </div>
              </div>
            </div>

            <el-divider />

            <div class="ui grid m-pre-next-blog-container">
              <div class="eight wide column">
                <div class="ui left floated basic button teal animated" @click="beforeBlog">
                  <div class="visible content">上一篇</div>
                  <div class="hidden content">
                    <i class="left arrow icon"></i>
                  </div>
                </div>
              </div>
              <div class="eight wide column">
                <div class="ui right floated basic button teal animated" @click="nextBlog">
                  <div class="visible content">下一篇</div>
                  <div class="hidden content">
                    <i class="right arrow icon"></i>
                  </div>
                </div>
              </div>
            </div>
            <!-- 评论信息-->
            <Comment :id="blog.blogId" :type="1" :comment-enabled="blog.commentEnabled"></Comment>
          </div>
        </transition-group>
      </div>

      <div class="ui center aligned container blog_password m-padded-lr-big" v-else>
        <div class="ui m-padded-tb-mini">
          <el-tooltip content="点击输入密码解锁" placement="bottom" effect="light">
            <div class="ui inverted button" tabindex="0" @click="getData($route.params.blogId)">
              <div class="content">
                <svg t="1638715240373" class="icon" viewBox="0 0 1024 1024" version="1.1"
                     xmlns="http://www.w3.org/2000/svg" p-id="2330" width="150" height="150">
                  <path
                      d="M153.6 469.312v469.376h716.8V469.312H153.6zM64 384h896v640H64V384z m403.2 329.92c-26.752-14.72-44.8-42.304-44.8-73.92 0-47.104 40.128-85.312 89.6-85.312 49.472 0 89.6 38.208 89.6 85.312 0 31.616-18.048 59.136-44.8 73.92v115.968a44.8 44.8 0 0 1-89.6 0v-115.968zM332.8 384h358.4V256c0-94.272-80.256-170.688-179.2-170.688-98.944 0-179.2 76.416-179.2 170.688v128zM512 0c148.48 0 268.8 114.56 268.8 256v128H243.2V256c0-141.44 120.32-256 268.8-256z"
                      fill="#262626" p-id="2331"></path>
                </svg>
              </div>
            </div>
          </el-tooltip>
        </div>

        <div class="ui grid m-padded-tb-mini">
          <div class="eight wide column">
            <div class="ui left floated basic button teal animated" @click="getBeforeBlog">
              <div class="visible content">上一篇</div>
              <div class="hidden content">
                <i class="left arrow icon"></i>
              </div>
            </div>
          </div>

          <div class="eight wide column">
            <div class="ui right floated basic button teal animated" @click="getNextBlog">
              <div class="visible content">下一篇</div>
              <div class="hidden content">
                <i class="right arrow icon"></i>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>


  </div>


</template>

<script>
import dateFormat from "@/utils/date.js";
import {
  setBlogPassword,
  getBlogPassword
} from "@/network/auth.js";
import {
  mapActions
} from "vuex";
import BlogCode from "@/components/blog/BlogCode.vue";
import Appreciate from "@/components/blog/Appreciate.vue";
import Comment from "@/components/comment/Comment";

export default {
  name: "Blog",
  components: {
    Comment,
    BlogCode,
    Appreciate,
  },
  data() {
    return {
      blog: {},
      blogId: "",
      content: "",
      bigFontSize: "16px",
      password: "",
      blogUrl: "",
      userId: "",
      result: {
        secrecy: false,
        password: "",
      },
      chatUrl: {},
      blogkey: "wuxin_blog_key",
    };
  },
  filters: {
    formatDateTime(value) {
      let date = new Date(value);
      return dateFormat.formatDate(date, "yyyy-MM-dd hh:mm");
    },
  },
  computed: {
    blogPasswordKey() {
      return this.blogkey + this.$route.params.blogId;
    },
  },

  methods: {
    ...mapActions("blog", [
      "setBlogId",
      "getDetailBlog",
      "getChatUrl",
      "beforeBlog",
      "nextBlog",
    ]),

    getData(blogId) {
      this.setBlogId(blogId);
      this.getDetailBlog(blogId)
          .then((res) => {
            this.setPass(res);
            //加密了，同时判断密码是否输入
            if (res.result.secrecy) {
              this.password = getBlogPassword(this.blogPasswordKey);
              if (res.result.password !== this.password) {
                // 请输入密码
                this.open(res);
              } else {
                // 加载数据
                this.loadData(res);
              }
            } else {
              // 没有设置密码直接加载数据
              this.loadData(res);
            }
          })
          .catch(() => {
          });
    },
    backTop() {
      window.scrollTo(0, 0);
    },

    loadData(res) {
      this.blog = res.result;
      this.userId = res.result.userId;
      // 回到顶部
      this.backTop();
      this.$nextTick(() => {
        // 获取地址栏完整地址 设置本文二维码
        this.blogUrl = window.location.href;
        // 加载高亮插件
        this.$primsjs.highlightAll();
        // 加载目录
        this.loadingContents(true)
        // 是否开启了赞赏功能
        if (res.result.appreciation) {
          this.getChat(this.userId);
        }
      });
    },

    setPass(res) {
      this.result.secrecy = res.result.secrecy;
      this.result.password = res.result.password;
    },

    loadingContents(loading) {
      this.$store.state.blog.loadingComplete = loading;
      console.log('文章目录加载状态 :>> ', this.$store.state.blog.loadingComplete);
    },

    // 添加密码
    open(res) {
      this.$prompt("该篇文章设置了密码,需输入密码才能查看哦", "提示", {
        confirmButtonText: "确定",
      })
          .then(({
                   value
                 }) => {
            this.password = value;
            if (res.result.password !== this.password) {
              this.open(res);
              this.$message.error(" 密码输入错误! 请重新输入!");
            } else {
              setBlogPassword(this.blogPasswordKey, value);
              this.loadData(res);
            }
          })
          .catch(() => {
            this.$message({
              type: "info",
              message: "操作取消",
            });
          });
    },

    getChat(userId) {
      this.getChatUrl(userId).then((res) => {
        this.chatUrl = res.result;
      });
    },

    getBeforeBlog() {
      this.beforeBlog(this.blogId).then((res) => {
        if (res.code === 200) {
          this.$router.push(`/blog/${res.result.blogId}`);
        } else {
          this.$message.error("没有更多了！");
        }
      });
    },

    getNextBlog() {
      this.nextBlog(this.blogId).then((res) => {
        if (res.code === 200) {
          this.$router.push(`/blog/${res.result.blogId}`);
        } else {
          this.$message.error("没有更多了！");
        }
      });
    },
  },

  beforeRouteEnter(to, _, next) {
    next((vm) => {
      vm.blogId = to.params.blogId;
      vm.getData(vm.blogId);
      vm.$store.state.blog.loadingComplete = false
    });
  },

  beforeRouteUpdate(to, from, next) {
    if (to.path !== from.path) {
      this.blogId = to.params.blogId;
      this.loadingContents(true)
      // this.$tocbot.refresh();
      this.getData(this.blogId);
      next();
    }
  },

  // beforeRouteLeave(to, _, next) {
  //   // 离开销毁目录组件加载状态
  //   this.$tocbot.destroy()
  //   this.loadingContents(false);
  //   next();
  // },
};
</script>

<style scoped>
.blog_password {
  padding-top: 10% !important;
}

.blog {
  min-height: 100vh !important;
}


a {
  text-decoration: none !important;
  color: #ffffff;
}

</style>
