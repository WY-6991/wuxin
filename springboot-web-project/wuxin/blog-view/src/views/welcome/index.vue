<template>
  <div class="welcome">
    <div class="stars">
      <div class="star" v-for="item in startCount" :key="`start${item}`" ref="star"></div>
    </div>
    <div class="motto">{{ motto }}</div>
    <div class="enter">
      <router-link to="/index">
        <svg t="1643112468373" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg"
             p-id="3688" width="44" height="44">
          <path
              d="M512.007561 0C229.239061 0 0.015121 229.22394 0.015121 511.992439s229.22394 512.007561 511.99244 512.007561S1024 794.77606 1024 512.007561A511.992439 511.992439 0 0 0 512.007561 0z m0 952.294747c-243.165788 0-440.287187-197.121399-440.287187-440.287186S268.841773 71.705253 512.007561 71.705253 952.294747 268.841773 952.294747 512.007561A440.287187 440.287187 0 0 1 512.007561 952.294747z m0 0"
              p-id="3689" fill="#ffffff"></path>
          <path
              d="M754.024129 512.007561a29.909951 29.909951 0 0 1 0 13.881362 24.768706 24.768706 0 0 1-6.57777 8.77036l-159.333245 157.866478a36.170174 36.170174 0 0 1-51.155392-51.094907l92.088779-92.814602H306.690412a36.548207 36.548207 0 1 1 0-73.081291H629.046501l-94.281369-92.814602a36.215538 36.215538 0 0 1 51.155393-51.276362l154.948064 154.237363a21.9259 21.9259 0 0 1 6.57777 9.496182 29.955315 29.955315 0 0 1 6.57777 16.814897z m0 0"
              p-id="3690" fill="#ffffff"></path>
        </svg>
      </router-link>
    </div>
  </div>
</template>
<script>


import {load} from "@/utils/motto";

export default {
  name: 'Welcome',
  data() {
    return {
      showLink: true,
      startCount: 800,
      distance: 800,
      motto: null,

    }
  },
  methods: {
    loadMotto() {
      load((res) => {
            if (res.status === "success") {
              this.motto = res.data.content;
            } else {
              this.motto = "人生代代无穷已，江月年年望相似。";
            }
          },
          () => {
            this.motto = "人生代代无穷已，江月年年望相似。";
          }
      );


    },

    star() {
      const that = this
      let starArr = that.$refs.star
      starArr.forEach(star => {
        let speed = 0.1 + (Math.random());
        let thisDistance = this.distance + (Math.random() * 300);
        star.style.transformOrigin = `0 0 ${thisDistance}px`;
        star.style.transform =
            `
      translate3d(0,0,-${thisDistance}px)
      rotateY(${Math.random() * 360}deg)
      rotateX(${Math.random() * (-50)}deg)
      scale(${speed})
      `
      })


      this.$nextTick(() => {
        this.loadMotto()
      })
    }


  },
  mounted() {
    this.star()

  }
}
</script>
<style scoped>

/*css文档参考*/
/*https://developer.mozilla.org/zh-CN/docs/Web/CSS/transform*/
/*星空动画来自 哔哩哔哩  https://b23.tv/g7Sdtg8 */

/*main容器*/
.welcome {
  position: fixed;
  width: 100%;
  min-height: 100vh;
  /*层级样式*/
  background: radial-gradient(220% 110% at top center, #1b2847 10%, #75517d 50%, #e96f92 65%, #f7f7b6);
  animation: welcomeAnimation 500ms ease;
  z-index: 10;
}

/*星星容器*/
.stars {
  position: absolute;
  transform: perspective(500px);
  transform-style: preserve-3d;
  perspective-origin: 50% 100%;
  animation: rotate 100s infinite linear;
  left: 50%;
  bottom: 0;
  z-index: 20;
}


/*星星样式*/
.star {
  position: absolute;
  width: 2px;
  height: 2px;
  background: #f7f7b8;
  left: 0;
  top: 0;
  backface-visibility: hidden;
  z-index: 30;
}


/*诗词样式*/
.motto {
  z-index: 40;
  position: absolute;
  height: 44px;
  color: white;
  font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
  line-height: 44px;
  letter-spacing: 2px;
  top: 43%;
  left: 50%;
  transform: translate(-50%, -50%) ;
  font-size: 30px;
  transition: color 1s linear inherit;
}


.motto::after {
  content: " | ";
  animation: mottoLine 2s linear 1s infinite ;
  z-index: 40;
}

.enter {
  text-align: center !important;

}

.icon {
  cursor: pointer;
  position: absolute;
  top: 50%;
  left: 48%;
  transform: translate(-50%, -50%);
  color: black;
  animation: enterIcon 2s linear infinite;
  z-index: 40;
}

/*页面加载下滑出现动画*/
@keyframes welcomeAnimation {
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

/*页面加载下滑出现动画*/
@-webkit-keyframes welcomeAnimation {
  0% {
    opacity: 0;
    filter: alpha(opacity=0);
    transform: translateY(-50px);
    background-position: 0 50%;

  }

  50% {
    background-position: 100% 50%;

  }
  100% {
    opacity: 1;
    -webkit-filter: none;
    filter: none;
    transform: translateY(0);
    background-position: 0 50%;
  }
}

/*星空动画*/
@keyframes rotate {
  0% {
    transform: perspective(400px) rotateZ(20deg) rotateX(-40deg) rotateY(0);
  }

  100% {
    transform: perspective(400px) rotateZ(20deg) rotateX(-40deg) rotateY(-360deg);
  }
}


@-webkit-keyframes rotate {
  0% {
    transform: perspective(400px) rotateZ(20deg) rotateX(-40deg) rotateY(0);
  }

  100% {
    transform: perspective(400px) rotateZ(20deg) rotateX(-40deg) rotateY(-360deg);
  }
}

/*竖线动画*/
@keyframes mottoLine {
  0% {
    opacity: 0.1;
  }

  100% {
    opacity: 1;
  }
}

@-webkit-keyframes mottoLine {
  0% {
    opacity: 0.1;
  }

  100% {
    opacity: 0.8;
  }
}


/*图标动画*/
@keyframes enterIcon {
  0% {
    opacity: 0.3;
    transform: translateX(0);
  }

  50% {
    opacity: 0.3;
    transform: translateX(20px);
  }

  100% {
    opacity: 0.8;
    transform: translateX(0px);
  }
}


@-webkit-keyframes enterIcon {
  0% {
    opacity: 0.8;
    transform: translateX(0);
  }

  50% {
    opacity: 0.3;
    transform: translateX(20px);
  }

  100% {
    opacity: 0.8;
    transform: translateX(0);
  }
}


</style>