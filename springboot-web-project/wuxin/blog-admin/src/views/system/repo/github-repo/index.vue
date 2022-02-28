<template>
  <div class="app-container">
 
    <el-row :gutter="20">
      <el-col :span="12" :xs="24">
        <GithubSetting></GithubSetting>
      </el-col>
      <el-col :span="12" :xs="24">
        <GithubOperation></GithubOperation>
      </el-col>
    </el-row>
  </div>
</template>
<script>
import GithubSetting from "./github-setting.vue";
import GithubOperation from "./github-operation.vue";
export default {
  name: "Github",
  components: { GithubOperation, GithubSetting },
};
</script>
<style scoped>
</style>
