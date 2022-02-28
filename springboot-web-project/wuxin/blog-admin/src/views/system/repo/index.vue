<template>
  <div class="app-container">
    <div class="filter-container">
      <el-alert type="warning" title="请输入token查看仓库配置"></el-alert>
      <el-input
        v-model="token"
        placeholder="请输入token查看配置"
        style="width: 400px"
      >
      <el-button slot="append" type="primary" icon="el-icon-search" >搜索</el-button>
      </el-input>

      <el-select v-model="repoName" placeholder="" class="m-margin-left-small">
        <el-option label="gitee" value="gitee"></el-option>
        <el-option label="github" value="github"></el-option>
      </el-select>
    </div>
    <!-- 仓库容器 -->
    <component :is="repoName"></component>
  </div>
</template>

<script>
import Github from "./github-repo/index.vue";
import Gitee from "./gitee-repo/index.vue";
export default {
  data() {
    return {
      repoName: "gitee",
      token: "",
    };
  },
  components: {
    Github,
    Gitee,
  },
};
</script>

<style lang="scss" scoped>
</style>