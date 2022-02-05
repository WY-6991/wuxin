<template>
  <MyCard :title="title">
    <div class="m-flex-justify-container">
      <UploadBackgroundImage :admin-image="system.adminBackgroundImage" />
    </div>
  </MyCard>
</template>
<script>
import MyCard from "@/components/MyComponents/MyCard";
import UploadBackgroundImage from "@/views/system/background/components/MyUpload";
import {mapGetters} from "vuex";


export default {
  name: 'BackgroundImageList',
  components: {UploadBackgroundImage, MyCard},
  data() {
    return {
      title: "登录背景设置"
    }
  },
  computed: {
    ...mapGetters('system', ['system'])
  }
}
</script>
<style scoped>
.m-flex-justify-container {
  display: flex !important;
  flex-direction: column !important;
  align-items: center !important;


}

ul li {
  list-style: none;
  margin-top: 10px;
}
</style>
