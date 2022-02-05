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
import GithubSetting from "@/views/system/github-repo/github-setting";
import GithubOperation from "@/views/system/github-repo/github-operation";
export default {
  name:'GithubIndex',
  components: {GithubOperation, GithubSetting},
}
</script>
<style scoped>

</style>
