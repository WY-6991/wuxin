<template>
  <div class="site m-animation-welcome" :style="styleSetting">
    <MyNav :category-list="categoryList" :tag-list="tagList" :login-url="site.loginUrl"
           :web-name="site.webName"></MyNav>
    <!--首页大图 只在首页且pc端时显示-->
    <div class="m-mobile-hide">
      <HeaderIndex v-show="checkRoute('Index')"></HeaderIndex>
    </div>
    <!--    页面设置按钮-->
    <PageSetting></PageSetting>

    <!--    中间主体部分-->
    <div class="main">
      <div class="m-padded-tb-big">
        <div class="ui container">
          <div class="ui stackable grid">
            <!-- 左边侧边栏移动端不显示-->
            <div class="three wide column m-mobile-hide">
              <transition-group enter-active-class="animate__animated animate__fadeInLeft"
                                leave-active-class="animate__animated animate__fadeOutLeft">
                <div v-show="settingState.focusMode" :key="1">
                  <!--                  <TagSidebar :tag-list="tagList"></TagSidebar>-->
                  <Blogger :user="adminUserInfo"></Blogger>
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
                  <RandomBlog :random-blog-list="randomBlogList"></RandomBlog>
                  <TagSidebar :tag-list="tagList" class=" tagSidebar"></TagSidebar>
                  <!--如果文章有密码,不加载目录-->
                  <BlogContents ref="contents" v-show="checkRoute('Blog')"></BlogContents>
                </div>
              </transition-group>
            </div>
          </div>

        </div>
      </div>
    </div>

    <MyPlayer></MyPlayer>
    <!--    回到顶部按钮-->
    <el-backtop style="box-shadow: none; background: none">
      <svg t="1638758260552" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg"
           p-id="4084" width="48" height="48">
        <path
            d="M1009.19461 5.118447a32.054274 32.054274 0 0 0-35.125341 0.255922l-959.708789 639.805859a31.830341 31.830341 0 0 0-14.043738 29.942914 31.830341 31.830341 0 0 0 19.929952 26.360002l250.292052 100.161607 117.692288 205.953506a31.990293 31.990293 0 0 0 27.415681 16.123108H415.998608c11.228593 0 21.657428-5.950194 27.415681-15.547283l66.443839-110.782384 310.14589 124.026365a31.734371 31.734371 0 0 0 27.543642-1.855437c8.445437-4.734563 14.23568-13.05204 15.867185-22.617137l159.951465-959.708788A32.054274 32.054274 0 0 0 1009.19461 5.118447zM100.446359 664.662317L841.821398 170.3803 302.784962 747.389214c-2.847136-1.695486-5.374369-3.934806-8.509418-5.182427l-193.829185-77.54447z m225.627536 105.216073l-0.223932-0.319903L931.842082 120.955298 415.230841 925.895049l-89.156946-156.016659z m480.750122 177.322194l-273.229092-109.278841a63.564712 63.564712 0 0 0-19.929952-3.806845L934.401305 181.896806l-127.577288 765.303778z"
            fill="#1296db" p-id="4085"></path>
      </svg>
    </el-backtop>

    <!--    底部内容-->
    <FooterIndex :blog-list="newBlogList" :label-list="footerLabel" :site="site"></FooterIndex>

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

export default {
  name: 'LayoutIndex',
  mixins: [resizeHandle],
  components: {
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

    const blog = this.$refs.contents
    console.log(blog)
    console.log('==================>elementui', this.adminUserInfo)
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
          console.log('admin user result============>'+res)
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
