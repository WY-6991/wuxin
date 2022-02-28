<template>
  <MyCard :title="title">
    <div class="m-flex-justify-container">
      <UploadBackgroundImage :image-list="imageList" />
    </div>

  </MyCard>
</template>
<script>
import MyCard from "@/components/MyComponents/MyCard";
import UploadBackgroundImage from "@/views/system/background/components/MyUpload";
import {delBackgroundMap, getBackgroundMap} from "@/api/system";

export default {
  name: 'BackgroundIndexImage',
  components: {UploadBackgroundImage, MyCard},
  data() {
    return {
      title: "首页背景设置",
      imageList: [],
    }
  },

  methods: {
    getData() {
      getBackgroundMap().then(res => {
        if (res.code === 200) {
          this.imageList = res.result
        }
      })
    },
    deleteData(id) {
      delBackgroundMap(id).then(res => {
        if (res.code === 200) {
          this.$message.success('删除成功')
        }
      })
    }
  },
  mounted() {
    this.getData()
  }
}
</script>
<style scoped>

</style>
