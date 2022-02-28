<template>
  <div>
    <MyCard title="github图床配置">

      <el-alert
        class="m-margin-tb-mini"
        type="warning"
        description="
          用户名 为 github 账号名,
          仓库 为仓库名称，
          文件夹 为该仓库下的文件夹 ,
          token 用于访问 github 仓库,
"
        :closable="false"
        show-icon>
      </el-alert>


      <el-form size="small" style="width:500px;" :model="github" ref="dataForm"
               class="m-margin-tb-small">

        <el-form-item label="用户名">
          <el-input prefix-icon="el-icon-s-custom" v-model="github.username" placeholder="github仓库用户名 ..."></el-input>
        </el-form-item>
        <el-form-item label="仓库">
          <el-input prefix-icon="el-icon-s-shop" v-model="github.repository" placeholder="github仓库名 ..."></el-input>
        </el-form-item>
        <el-form-item label="分支">
          <el-input prefix-icon="el-icon-share" v-model="github.master" placeholder="提交的分支..."></el-input>
        </el-form-item>
        <el-form-item label="文件夹">
          <el-input prefix-icon="el-icon-folder-opened" v-model="github.folder" placeholder="提交的文件夹..."></el-input>
        </el-form-item>
        <el-form-item label="token">
          <el-input prefix-icon="el-icon-s-check" v-model="github.accessToken" placeholder="ghp_xxxxxxxxxxxxx"></el-input>
        </el-form-item>
        <el-form-item label="上传路径">
          <el-input prefix-icon="el-icon-upload" v-model="github.api" disabled></el-input>
        </el-form-item>

        <el-form-item label="访问路径">
          <el-input prefix-icon="el-icon-s-promotion" v-model="github.accessApi" disabled></el-input>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click.prevent="updateSetting">确认</el-button>
        </el-form-item>

      </el-form>
      <el-link type="primary" :underline="false" href="https://docs.github.com/en/rest/reference/repos" target="_blank"
               class="m-float-right">api文档
      </el-link>
    </MyCard>


  </div>


</template>
<script>
import MyCard from "@/components/MyComponents/MyCard";
import {updateGithubSetting, getGithubSetting} from "@/api/system";

export default {
  name: 'GithubSetting',
  components: {MyCard},
  data() {
    return {
      github: {
        username: "",
        repository: "",
        master: "",
        folder: "",
        access_token: "",
        jsdelivr: "https://cdn.jsdelivr.net/gh/",
        api: "https://api.github.com/repos/",
      },


    }
  },
  methods: {

    updateSetting() {
      updateGithubSetting(this.github).then(res => {
        if (res.code === 200) {
          this.$message.success("修改成功！")
        }
      })

    },
    getData() {
      getGithubSetting().then(res => {
        if (res.code === 200) {
          this.github = res.result
        }
      })
    }
  },
  created() {
    this.getData()
  }

}
</script>
<style scoped>

</style>
