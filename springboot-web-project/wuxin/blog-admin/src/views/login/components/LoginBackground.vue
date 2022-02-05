<template>
  <div class="welcome">
    <div class="stars">
      <div class="star" v-for="item in startCount" :key="`start${item}`" ref="star"></div>
    </div>
    <div class="moon" @click="isShow"></div>
  </div>
</template>
<script>

export default {
  name: 'LoginBackground',
  data() {
    return {
      showLink: true,
      startCount: 800,
      distance: 800,
    }
  },
  methods: {

    isShow(){
      this.$emit('isShow')
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
  animation: welcomeAnimation 1000ms ease;
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
.moon {
  position: fixed;
  width: 66px;
  height: 66px;
  cursor: pointer;
  background: #EEEE00;
  border-radius: 50%;
  top: 100px;
  right: 100px;
  /* 添加盒子阴影 */
  box-sizing: border-box;
  box-shadow: 1px 1px 2px #EEEE00, 1px 1px 20px #EEEE00, 1px 1px 30px #EEEE00;
}

.moon:hover {
  box-shadow: 1px 1px 2px #EEEE00, 1px 1px 20px #EEEE00, 1px 1px 30px #EEEE00, 2px 2px 40px #EEEE00;
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


</style>
