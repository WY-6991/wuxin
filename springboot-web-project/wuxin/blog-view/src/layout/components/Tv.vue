<template>
  <div>
    <div class="m-tv" ref="tv"></div>
  </div>

</template>
<script>

import {tvList, settUrl} from '@/utils/setting'


export default {
  name: "MyTv",
  data() {
    return {
      count: 0,
    }
  },
  mounted() {
    this.init()
  },

  methods: {

    init() {
      const container = this.$refs.tv
      container.backgroundImage = tvList[tvList.length - 1]
      setInterval(() => {
        this.count++
        if (this.count >= tvList.length) {
          this.count = 0
        }
        container.style.backgroundImage = settUrl(tvList[this.count])
      }, 5000)
    },


  },
}
</script>

<style scoped lang="scss">
@keyframes tv_move {

  0% {
    offset-distance: 0;
    transform: rotate(0deg);
  }


  50% {
    offset-distance: 50%;
    transform: rotate(180deg);
  }

  100% {
    offset-distance: 100%;
    transform: rotate(360deg);
  }

}

@-webkit-keyframes tv_move {

  0% {
    offset-distance: 0;
    transform: rotate(0deg);
  }


  50% {
    offset-distance: 0;
    transform: rotate(180deg);
  }

  100% {
    offset-distance: 100%;
    transform: rotate(360deg);
  }

}

.m-tv {
  //offset: path("M 300 100 L 300 100 L 400 600 z") auto;
  //offset: path('M900,190  L993,245 V201  A11,11 0 0,1 1004,190  H1075  A11,11 0 0,1 1086,201  V300  L1294,423 H1216  A11,11 0 0,0 1205,434  V789  A11,11 0 0,1 1194,800  H606  A11,11 0 0,1 595,789  V434  A11,11 0 0,0 584,423  H506 L900,190') auto;
  offset: path('M900,190  L993,245 V201  A11,11 0 0,1 1004,190  H1075  A11,11 0 0,1 1086,201  V300  L1294,423 H1216  A11,11 0 0,0 1205,434  V789  A11,11 0 0,1 1194,800  H606  A11,11 0 0,1 595,789  V434  A11,11 0 0,0 584,423  H506 L900,190') auto;
  animation: 15s linear 5s infinite alternate tv_move;
  cursor: pointer;
  width: 100px;
  height: 100px;
}


</style>
