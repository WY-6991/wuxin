<template>
  <div class="ui segments blog-container" ref="contents">
    <h2>vue vue vue vue id</h2>
  </div>
</template>
<script>
export default {
  name: 'BlogContents',
  data() {
    return {
      container: null
    }
  },
  mounted() {
    this.container = this.$refs.contents
    // 获取当前容器距离顶部高度
    const offsetTop = this.container.offsetTop
    const offsetHeight = this.container.offsetHeight
    console.log(offsetTop);
    console.log(offsetHeight);

    // 监听页面滚动事件
    const container = this.container
    window.document.addEventListener('scroll', function (e) {
      // console.log(e);
      // console.log(window.pageYOffset);
      // if (window.pageYOffset >= offsetTop) {
      //   container.style.position = 'fixed'
      //   container.style.top = '40px'
      // } else {
      //   container.style.position = 'relative'
      // }
    })
  }
}
</script>
<style scoped>

.blog-container {
  margin-top: 100px;
  width: 100%;
  height: 200px;
  background-color: black;
  color: white;
  padding: 25px;
  border: 10px skyblue solid;

}

</style>
