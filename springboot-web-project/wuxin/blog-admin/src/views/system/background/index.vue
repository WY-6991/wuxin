<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="12" :xs="24">
        <BackgroundIndexImage />
      </el-col>
      <el-col :span="12" :xs="24">
        <BackgroundIndexImage />
      </el-col>
    </el-row>
  </div>
</template>
<script>
import BackgroundIndexImage from "@/views/system/background/index-image";
import BackgroundImageList from "@/views/system/background/image";

export default {
  name: 'WegBackground',
  components: {BackgroundImageList, BackgroundIndexImage},
}
</script>
<style scoped>

</style>
