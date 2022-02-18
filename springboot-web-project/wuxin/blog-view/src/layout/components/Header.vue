<template>
  <div class="header-container m-animation-welcome" :style="headerBackgroundImage" ref="header">
<!--    <MyTv v-for="i in clickCount" :key="i" @click.native.prevent="createTV"></MyTv>-->
  </div>
</template>

<script>

import MyTv from "@/layout/components/Tv";
import {tvList} from '@/utils/setting'

const changeUrl = (url) => {
  return 'url(' + `${url}` + ')'
}


export default {
  name: "HeaderIndex",
  components: {MyTv},
  props: {
    webName: {
      type: String,
      default: 'wuxin`blog',
    },
  },
  data() {
    return {
      loaded: false,
      headerBackgroundImage: {
        'backgroundImage': changeUrl(require('@/assets/img/bg/bg1.jpg'))
      },
      container: null,
      clickCount: 1,

    }
  },
  mounted() {
    this.init()
  },

  methods: {

    init() {
      this.container = this.$refs.header
    },
    createTV() {
      if (this.clickCount<=tvList.length) {
        this.clickCount++
      }else {
        this.$message.warning(`添加失败！最多只能添加${tvList.length}个表情`)
      }
    }


  },
}
</script>

<style scoped lang="scss">

* {
  margin: 0;
  padding: 0;
}

.header-container {
  position: relative;
  width: 100%;
  min-height: 100vh;
  background-repeat: no-repeat;
  background-size: cover;
  background-attachment: fixed;


}


@keyframes welcomeAnimation {
  0%{
    opacity: 0;
    filter: alpha(opacity=0);
    transform: translateY(-50px);
  }
  100%{
    opacity: 1;
    -webkit-filter: none;
    filter: none;
    transform: translateY(0);
  }
}

@-webkit-keyframes welcomeAnimation {
  0%{
    opacity: 0;
    filter: alpha(opacity=0);
    transform: translateY(-50px);
  }
  100%{
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
