<template>
  <div class="site m-animation-welcome" :style="styleSetting">
    <MyNav :category-list="categoryList" :tag-list="tagList" :login-url="site.loginUrl"
           :web-name="site.webName"></MyNav>
    <!--首页大图 -->
    <div class="m-mobile-hide">
      <HeaderIndex v-show="checkRoute('Index')"></HeaderIndex>
    </div>
    <!--页面设置按钮-->
    <PageSetting />
    <!--主体内容-->
    <div class="main">
      <div class="m-padded-tb-big">
        <div class="ui container">
          <div class="ui stackable grid">
            <!-- 左边侧边栏移动端不显示-->
            <div class="three wide column m-mobile-hide">
              <transition-group enter-active-class="animate__animated animate__fadeInLeft"
                                leave-active-class="animate__animated animate__fadeOutLeft">
                <div v-show="settingState.focusMode" :key="1">
                </div>
              </transition-group>
            </div>
            <div class="ten wide computer sixteen wide mobile column">
              <transition enter-active-class="animate__animated animate__fadeIn">
                <keep-alive>
                  <router-view />
                </keep-alive>
              </transition>
            </div>
            <div class="three wide column m-mobile-hide m-position-absolute">
              <transition-group enter-active-class="animate__animated animate__fadeInRight "
                                leave-active-class="animate__animated animate__fadeOutRight">
                <div v-show="settingState.focusMode" :key="1" class="slide right">
                  <RandomBlog :random-blog-list="randomBlogList" />
                  <TagSidebar :tag-list="tagList" id="tags" />
                  <!--如果文章有密码,不加载目录-->
                  <BlogContents v-show="checkRoute('Blog')" class="contents" />
                </div>
              </transition-group>
            </div>
          </div>

        </div>
      </div>
    </div>
    <!--音乐插件-->
    <MyPlayer />
    <!-- 回到顶部按钮-->
    <BackTop />
    <!--底部-->
    <FooterIndex
        :blog-list="newBlogList"
        :label-list="footerLabel"
        :site="site"
    />
  </div>
</template>
<script>


import MyNav from "@/layout/components/Nav";
import {getAdminUserInfo, getRandomBlog, webSite} from "@/api/home.js";
import FooterIndex from "@/layout/components/Footer";
import HeaderIndex from "@/layout/components/Header";
import TagSidebar from "@/layout/components/sidebar/Tag";
import BlogContents from "@/layout/components/sidebar/blog-content";
import PageSetting from "@/layout/components/sidebar/PageSetting";
import RandomBlog from "@/layout/components/sidebar/RandomBlog";

import {mapActions, mapGetters} from "vuex";
import {settingBackgroundColor, settingBackgroundImageUrl} from "@/utils/setting";
import Blogger from "@/layout/components/sidebar/Blogger";
import MyPlayer from "@/layout/components/sidebar/MyPlayer";
import resizeHandle from './mixin/resizeHandle'
import BackTop from "@/layout/components/BackTop";

export default {
  name: 'LayoutIndex',
  mixins: [resizeHandle],
  components: {
    BackTop,
    MyPlayer,
    Blogger,
    RandomBlog,
    PageSetting,
    BlogContents,
    TagSidebar,
    HeaderIndex,
    FooterIndex,
    MyNav
  },
  data() {
    return {
      blogName: "wx.blog",
      randomBlogList: [],
      tagList: [],
      categoryList: [],
      adminUserInfo: {},
      footerLabel: [],
      newBlogList: [],
      site: {},

    };
  },

  watch: {
    'settingState': {
      immediate: true,
      deep: true,
      handler() {
        // console.log('对象深度监听中....', JSON.stringify(this.styleSetting))
        // this.saveSetting()
      },

    },


  },
  computed: {

    ...mapGetters(['settingState']),
    // 背景设置
    styleSetting() {
      const {nightMode} = this.settingState
      const {image, isShowImage, color} = this.settingState.background
      return {
        'backgroundColor': settingBackgroundColor(color, nightMode),
        'backgroundImage': settingBackgroundImageUrl(isShowImage, image),
      }

    }
  },


  mounted() {
    this.init()
    this.initData()
    this.randomBlog()

    const tags = document.getElementById('tags')
    const content = document.querySelector('.contents')
    console.log('contents===>', content, 'tags=>', tags)

    const contents_offsetTop = content.offsetTop
    const contents_offsetHeight = content.offsetHeight
    console.log(contents_offsetTop, contents_offsetHeight);
    // const tag_offsetTop = tag.offsetTop
    // const tag_offsetHeight = tag.offsetHeight
    // console.log(tag_offsetTop, tag_offsetHeight)

    // window.document.addEventListener('scroll', function (e) {
    //   // console.log(e);
    // })

  },

  methods: {
    ...mapActions('setting', ['saveSetting', 'getSetting']),
    init() {
      if (sessionStorage.getItem("store")) {
        this.$store.replaceState(
            Object.assign({},
                this.$store.state,
                JSON.parse(sessionStorage.getItem("store"))
            )
        );

      }

      // 在页面刷新时将vuex里的信息保存到localStorage里
      window.addEventListener("beforeunload", () => {
        sessionStorage.setItem("store", JSON.stringify(this.$store.state));
      });

    },


    initData() {


      // 加载基本数据
      webSite().then(res => {
        if (res.code === 200) {
          const {tagList, site, categoryList, newBlogList, footerLabelList} = res
          this.tagList = tagList
          this.categoryList = categoryList
          this.newBlogList = newBlogList
          this.footerLabel = footerLabelList
          this.site = site
        }
      })


      getAdminUserInfo().then((res) => {
        if (res.code === 200) {
          this.adminUserInfo = res;
          console.log('admin user result============>' + res)
        }
      })


    },


    checkRoute(name) {
      return this.$route.name === name
    },

    /**
     * 随机文章
     */
    randomBlog() {
      getRandomBlog().then((res) => {
        if (res.code === 200) {
          this.randomBlogList = res.result;
        }
      })

    }

  }

}
</script>
<style scoped>
.site {
  display: flex;
  min-height: 100vh; /* 没有元素时，也把页面撑开至100% */
  flex-direction: column;
  background-repeat: no-repeat;
  background-attachment: fixed;
  background-position: center;
  background-size: cover;
  background-color: #e86161;
  /*-webkit-filter: blur(5px);*/
  /*-moz-filter: blur(5px);*/
  /*-o-filter: blur(5px);*/
  /*-ms-filter: blur(5px);*/
  /*filter: blur(5px);*/


}


.main {
  margin-top: 40px;
  min-height: 100vh !important;
  flex: 1;
}

.main .ui.container {
  width: 1400px !important;
  margin-left: auto !important;
  margin-right: auto !important;
}

.ui.grid .three.column {
  padding: 0;
}

.ui.grid .ten.column {
  padding-top: 0;
}

.animate__animated.animate__fadeIn {
  animation-duration: 600ms !important;
}

.animate__animated.animate__fadeOut {
  animation-duration: 10s !important;
}


@-webkit-keyframes fadeIn {
  from {
    opacity: 0;
  }

  to {
    opacity: 1;
  }
}

@keyframes fadeIn {
  from {
    opacity: 0;
  }

  to {
    opacity: 1;
  }
}

.animate__fadeIn {
  -webkit-animation-name: fadeIn;
  animation-name: fadeIn;
}


@keyframes welcomeAnimation {
  0% {
    opacity: 0;
    filter: alpha(opacity=0);
    transform: translateY(-30px);
  }
  100% {
    opacity: 1;
    -webkit-filter: none;
    filter: none;
    transform: translateY(0);
  }
}

@-webkit-keyframes welcomeAnimation {
  0% {
    opacity: 0;
    filter: alpha(opacity=0);
    transform: translateY(-50px);
  }
  100% {
    opacity: 1;
    -webkit-filter: none;
    filter: none;
    transform: translateY(0);
  }
}

.m-animation-welcome {
  animation-duration: 1s;
  animation-timing-function: ease;
  animation-iteration-count: 1;
  animation-direction: normal;
  animation-fill-mode: none;
  animation-play-state: running;
  animation-name: welcomeAnimation;
}


</style>
