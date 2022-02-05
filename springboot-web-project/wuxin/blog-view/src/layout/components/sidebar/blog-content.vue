<!--<template>-->
<!--  <div-->
<!--      class="ui segments m-toc toc-wrapper m-box blog-contents"-->
<!--      id="blog-content"-->
<!--      ref="blog_contents"-->
<!--  >-->
<!--    <div class="ui secondary segment"><i class="list ul icon"></i>本文目录</div>-->
<!--    <div class="ui yellow segment ">-->
<!--      目录插件容器-->
<!--      <h3>可视化大小{{containerHeight}}</h3>-->
<!--      <h3>可视化大小{{containerScrollHeight}}</h3>-->
<!--      <h3>默认全局大小{{containerDefaultHeight}}</h3>-->
<!--      <h3>容器边距大小{{containerDefaultHeight - containerHeight}} </h3>-->

<!--      <h3>scrollTop{{containerScrollTop}}</h3>-->
<!--      <h3>容器距离顶部高度{{offsetTop}}</h3>-->
<!--    </div>-->
<!--  </div>-->
<!--</template>-->

<!--<script>-->
<!--export default {-->
<!--  name: 'BlogContents',-->
<!--  data() {-->
<!--    return {-->
<!--      containerHeight:0,-->
<!--      containerScrollTop:0,-->
<!--      containerDefaultHeight:0,-->
<!--      containerScrollHeight:0,-->
<!--      offsetTop:0,-->
<!--    };-->
<!--  },-->
<!--  mounted() {-->
<!--    let content = document.querySelector('.blog-contents')-->

<!--    // console.log(content)-->
<!--    // 添加滑动事件-->
<!--    let that = this-->
<!--    document.addEventListener('scroll', function (e) {-->

<!--      // 获取目录容器到顶部的距离-->
<!--      let contentTop = content.offsetTop-->
<!--      let offsetHeight = content.offsetHeight-->
<!--      // 容器可视化大小-->
<!--      that.containerHeight = content.clientHeight-->
<!--      // 不包含margin和padding以及border-->
<!--      that.containerScrollHeight = content.scrollHeight-->
<!--      // 容器大小-->
<!--      that.containerDefaultHeight = content.offsetHeight-->

<!--      // 滑动距离-->
<!--      that.containerScrollTop = content.offsetTop-->

<!--      // 容器距离顶部高度-->
<!--      that.offsetTop = content.offsetTop-->

<!--      // console.log(window.pageYOffset === window.scrollY)-->
<!--      // let pageY = window.scrollY || window.pageYOffset-->
<!--      // // 设置为固定定位-->
<!--      // if (pageY >= contentTop  - offsetHeight ) {-->
<!--      //   content.style.position = 'fixed'-->
<!--      //   content.style.top = offsetHeight + 'px'-->
<!--      // } else {-->
<!--      //   content.style.top = contentTop  - offsetHeight + 'px'-->
<!--      //   content.style.position = 'absolute'-->
<!--      // }-->
<!--    })-->

<!--  },-->
<!--};-->
<!--</script>-->

<!--<style lang="scss" scoped>-->
<!--//#blog-content {-->
<!--//  position: relative;-->
<!--//}-->
<!--</style>-->


<template>
  <div class="ui segments ">

    <div class="ui segment">
      <p>scrollTop:{{ scroll.top }}</p>
      <p>scroll-left:{{ scroll.left }}</p>
      <p>scroll-width:{{ scroll.width }}</p>
      <p>scrollT-height:{{ scroll.height }}</p>
    </div>
    <div class="ui segment">
      <p>client-top:{{ client.top }}</p>
      <p>client-left:{{ client.left }}</p>
      <p>client-width:{{ client.width }}</p>
      <p>client-height:{{ client.height }}</p>
    </div>

    <div class="ui segment attached">
      <p>offset-top:{{ offset.top }}</p>
      <p>offset-left:{{ offset.left }}</p>
      <p>offset-width:{{ offset.width }}</p>
      <p>offset-height:{{ offset.height }}</p>
    </div>


  </div>
</template>
<script>
export default {
  name: 'name',
  data() {
    return {
      scroll: {
        top: 0,
        height: 0,
        width: 0,
        left: 0
      },
      client: {
        width: 0,
        height: 0,
        top: 0,
        left: 0
      },
      offset: {
        width: 0,
        height: 0,
        top: 0,
        left: 0
      },


    }
  },
  mounted() {

    // let container = document.querySelector('.blog-container')
    // //  scroll
    // this.scroll.top = container.scrollTop
    // this.scroll.left = container.scrollLeft
    // this.scroll.width = container.scrollWidth
    // this.scroll.height = container.scrollHeight
    //
    // this.client.top = container.clientTop
    // this.client.left = container.clientLeft
    // this.client.height = container.clientHeight
    // this.client.width = container.clientWidth
    //
    //
    // this.offset.top = container.offsetTop
    // this.offset.left = container.offsetLeft
    // this.offset.height = container.offsetHeight
    // this.offset.width = container.offsetWidth

  }
}
</script>
<style scoped>

.blog-container {
  margin-top: 100px;
  width: 100%;
  height: 200px;
  background-color: red;
  padding: 25px;
  border: 10px skyblue solid;

}

</style>
