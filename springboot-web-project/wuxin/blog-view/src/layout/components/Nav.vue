<template>
  <div class="ui container">
    <div
      ref="nav"
      class="ui stackable menu small fixed"
      :class="{
        transparent: $route.name === 'Index' && clientSize.clientWidth > 768,
        inverted: inverted,
      }"
    >
      <div class="ui container">
        <router-link to="/welcome">
          <h3 class="ui header item m-blue">
            {{ webName !== "" ? webName : "wuxin001" }}`blog
          </h3>
        </router-link>
        <router-link
          to="/index"
          class="item"
          :class="` ${showMobile()}  ${isActive('Index')} ${color}`"
        >
          <i class="home icon"></i>首页
        </router-link>

        <el-dropdown trigger="click" @command="categoryRoute">
          <span
            class="el-dropdown-link item"
            :class="` ${showMobile()} ${isActive('Category')} ${color}`"
          >
            <i class="idea icon"></i>分类<i class="caret down icon"></i>
          </span>
          <el-dropdown-menu slot="dropdown" :class="{ inverted: inverted }">
            <el-dropdown-item
              :command="category.name"
              v-for="(category, index) in categoryList"
              :key="`categoryName${index}`"
            >
              {{ category.name }}
            </el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
        <router-link
          to="/archive"
          class="item"
          :class="` ${showMobile()}  ${isActive('Archive')} ${color}`"
        >
          <i class="archive icon"></i>收藏
        </router-link>

        <router-link
          to="/moments"
          class="item"
          :class="`  ${showMobile()} ${isActive('Moment')} ${color}`"
        >
          <i class="hotjar icon"></i>动态
        </router-link>
        <router-link
          to="/about"
          class="item"
          :class="`  ${showMobile()} ${isActive('About')} ${color}`"
        >
          <i class="info icon"></i>关于
        </router-link>
        <router-link
          to="/friends"
          class="item"
          :class="` ${showMobile()}  ${isActive('Friend')} ${color}`"
        >
          <i class="users icon"></i>友接
        </router-link>

        <router-link
          to="/update"
          class="item"
          :class="` ${showMobile()}  ${isActive('Time')} ${color}`"
        >
          <i class="leaf circle icon"></i>更新
        </router-link>

        <!--右侧登录注册-->
        <div class="right menu">
          <a
            class="item m-mobile-hide"
            href="javaScript:void(0)"
            :class="` ${showMobile()}  ${color}`"
            @click.prevent="search"
          >
            <i class="ui search icon"></i>搜索
          </a>

          <a
            :href="loginUrl"
            class="item m-mobile-hide"
            target="_blank"
            :class="` ${showMobile()}  ${isActive('Login')} ${color}`"
          >
            <i class="user icon"></i>登录
          </a>
        </div>

        <!--      当屏幕缩小时候显示菜单显示按钮-->
        <div
          class="ui menu black icon button m-right-top m-mobile-show"
          :class="{ inverted: $store.state.setting.inverted }"
          @click="toggle"
        >
          <i class="sidebar icon"></i>
        </div>
      </div>
    </div>
    <Search
      :open="open"
      @changeSearch="changeSearch"
      :tagList="tagList"
      :category-list="categoryList"
    ></Search>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import Search from "@/layout/components/Search";

export default {
  name: "MyNav",
  components: { Search },
  props: {
    webName: {
      type: String,
      default: "wuxin001`blog",
    },
    categoryList: {
      type: Array,
      default: [],
    },
    tagList: {
      type: Array,
      default: [],
    },
    loginUrl: {
      type: String,
      default: "http://localhost:2768/login",
    },
  },
  data() {
    return {
      mobileHide: true,
      queryString: "",
      queryResult: [],
      timer: null,
      open: false,
    };
  },
  computed: {
    ...mapGetters(["clientSize", "settingState"]),

    color() {
      return this.settingState.menuColor;
    },
    inverted() {
      return this.settingState.inverted;
    },
  },
  watch: {
    "clientSize.clientWidth"() {
      if (this.$route.name === "Home") {
        if (this.clientSize.clientWidth > 768) {
          this.$refs.nav.classList.add("transparent");
        } else {
          this.$refs.nav.classList.remove("transparent");
        }
      }
    },
    //路由改变时，收起导航栏
    "$route.path"() {
      this.mobileHide = true;
    },
  },
  mounted() {
    //监听页面滚动位置，改变导航栏的显示
    document.addEventListener("scroll", () => {
      //首页且不是移动端
      if (this.$route.name === "home" && this.clientSize.clientWidth > 768) {
        if (window.scrollY > this.clientSize.clientHeight / 2) {
          this.$refs.nav.classList.remove("transparent");
        } else {
          this.$refs.nav.classList.add("transparent");
        }
      }
    });
    //监听点击事件，收起导航菜单
    document.addEventListener("click", (e) => {
      let flag = e.path.some((item) => {
        if (item === this.$refs.nav) return true;
      });
      //如果导航栏是打开状态，且点击的元素不是Nav的子元素，则收起菜单
      if (!this.mobileHide && !flag) {
        this.mobileHide = true;
      }
      // 终止冒泡事件
      e.stopPropagation();
    });
  },
  methods: {
    toggle() {
      this.mobileHide = !this.mobileHide;
    },
    showMobile() {
      return this.mobileHide ? "m-mobile-hide" : "m-mobile-show";
    },
    isActive(name) {
      return this.$route.name === name ? "active" : "";
    },
    categoryRoute(name) {
      this.$router.push(`/category/${name}`);
    },

    search() {
      // todo 这里有个bug，打开搜索窗口之后如果点击其他地方关闭，再次点击打开，会发现 搜索窗口打不开，因为直接关闭窗口，没有触发changeSearch 事件
      // todo 因此此处现赋值为false，然后再赋值为ture 虽然这种方案可行，但是一点都美观，......
      this.open = false;
      this.open = true;
    },
    changeSearch() {
      this.open = !this.open;
    },
  },
};
</script>

<style lang="css" scoped>
.ui.fixed.menu .container {
  width: 1400px !important;
  margin-left: auto !important;
  margin-right: auto !important;
}

.ui.fixed.menu {
  transition: 0.3s ease-out;
}

.ui.inverted.pointing.menu.transparent {
  background: transparent !important;
}

.ui.inverted.pointing.menu.transparent .active.item:after {
  background: transparent !important;
  transition: 0.3s ease-out;
}

.ui.inverted.pointing.menu.transparent .active.item:hover:after {
  background: transparent !important;
}

.el-dropdown-link {
  outline-style: none !important;
  outline-color: unset !important;
  height: 100%;
  cursor: pointer;
}

.el-dropdown-menu {
  margin: 7px 0 0 0 !important;
  padding: 0 !important;
  border: 0 !important;
  background: #1b1c1d !important;
}

.el-dropdown-menu__item {
  padding: 0 15px !important;
  color: rgba(255, 255, 255, 0.9) !important;
}

.el-dropdown-menu__item:hover {
  background: rgba(255, 255, 255, 0.08) !important;
}

.el-popper .popper__arrow::after {
  content: none !important;
}

.popper__arrow {
  display: none !important;
}

.m-search {
  min-width: 220px;
  padding: 0 !important;
}

.m-search input {
  color: rgba(255, 255, 255, 0.9);
  border: 0px !important;
  background-color: inherit;
  padding: 0.67857143em 2.1em 0.67857143em 1em;
}

.m-search i {
  color: rgba(255, 255, 255, 0.9) !important;
}

.m-search-item {
  min-width: 350px !important;
}

.m-search-item li {
  line-height: normal !important;
  padding: 8px 10px !important;
}

.m-search-item li .title {
  text-overflow: ellipsis;
  overflow: hidden;
  color: rgba(0, 0, 0, 0.87);
}

.m-search-item li .content {
  text-overflow: ellipsis;
  font-size: 12px;
  color: rgba(0, 0, 0, 0.7);
}
</style>
