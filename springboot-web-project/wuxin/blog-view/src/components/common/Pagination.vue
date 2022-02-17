<template>
  <div class="ui bottom" style="text-align: center;">
    <el-pagination
        @current-change="handleCurrentChange"
        :current-page="pageNum"
        :page-count="totalPage"
        layout="prev, pager, next"
        background hide-on-single-page
    />
  </div>
</template>

<script>


const cubic = value => Math.pow(value, 4);
const easeInOutCubic = value => value < 0.4
    ? cubic(value * 2) / 2
    : 1 - cubic((1 - value) * 2) / 2;

export default {
  name: "MyPagination",
  props: {
    getList: {
      type: Function,
      default: () => {
      }
    },
    current: {
      type: Number,
      default: 1
    },
    totalPage: {
      type: Number,
      default: 0,
    },
    isScrollTop: {
      type: Boolean,
      default: true
    }
  },
  activated() {
    this.$nextTick(() => {
      if (this.$route.name !== "Index") {
        //从其它页面路由到首页时，让首页的分页组件页码重置到第一页
        this.pageNum = 1;
      }
    });
  },

  data() {
    return {
      pageNum: 1,
      // 容器 对象
      el: null,
      // 容器
      container: null,
      // 默认可视化距离
      clientHeight: 0,
      // 设置到顶部时间和加载数据时间一致
      delay: 1000
    };
  },
  mounted() {
    this.init();
  },
  methods: {
    //监听页码改变的事件
    handleCurrentChange(newPage) {
      if (this.isScrollTop) {
        // this.MyScrollTop()
        this.scrollToTop();
      }
      // 内容延时加载
      setTimeout(() => {
        this.pageNum = newPage;
        this.getList(newPage);
        this.$emit('currentPage', newPage)
      }, this.delay / 2)
    },

    // 该部分引用了element 回到顶部按钮事件
    init() {
      // 获取容器事件对象
      this.container = document;
      // 获取html主体
      this.el = document.documentElement;
      // 是不是首页 首页有壁纸，因此滑动距离可视化距离不用滑到顶部同时还有判断是不是手机端打开的
      this.check()

    },
    scrollToTop() {
      console.log(document.documentElement.scrollHeight)
      const el = this.el;
      // 获取初始点击时间
      const beginTime = Date.now();
      // 获取距离浏览器顶部距离
      const beginValue = el.scrollTop;
      const clientHeight = this.clientHeight
      const delay = this.delay
      const rAF = window.requestAnimationFrame || (func => setTimeout(func, 16));
      // 自动执行该函数
      const frameFunc = () => {
        // 滑动到顶部距离需要的时间
        const progress = (Date.now() - beginTime) / delay;
        if (progress < 1) {
          el.scrollTop = beginValue * (1 - easeInOutCubic(progress)) + clientHeight;
          rAF(frameFunc);
        } else {
          el.scrollTop = clientHeight;
        }
      };
      rAF(frameFunc);
    },
    //返回true表示为pc端打开，返回false表示为手机端打开
    check() {
      let userAgentInfo = navigator.userAgent;
      console.log(userAgentInfo)
      let Agents = ["Android", "iPhone", "SymbianOS", "Windows Phone", "iPad", "iPod"];
      let flag = true;
      for (let v = 0; v < Agents.length; v++) {
        if (userAgentInfo.indexOf(Agents[v]) > 0) {
          flag = false;
          break;
        }
      }
      // console.log(document.documentElement.clientHeight)
      // 如果是pc端打开打开的同时是首页
      if (this.$route.name === 'Index' && flag) {
        // 获取浏览器可视化距离
        this.clientHeight = document.documentElement.clientHeight
      } else {
        // 其他页面需要滑动到顶部
        this.clientHeight = 0
      }
      console.log(this.clientHeight)

      return flag;
    }

  }
};
</script>
