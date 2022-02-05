<template>
  <div>
    <MyCard title="操作">
      <el-alert
        class="m-margin-tb-mini"
        type="warning"
        description=" 该功能用于测试使用 删除文件需要谨慎操作！"
        :closable="false"
        show-icon>
      </el-alert>
      <el-form size="small" class="m-margin-tb-small" :model="github" ref="githubOperation" :rules="rules">
        <el-form-item label="地址" required prop="url">
          <el-input v-model="github.url" placeholder="地址..."></el-input>
        </el-form-item>
        <el-form-item label="sha" required prop="sha">
          <el-input v-model="github.sha" placeholder="sha..."></el-input>
        </el-form-item>
        <el-form-item label="提交消息" required prop="message">
          <el-input v-model="github.message" placeholder="写点什么..."></el-input>
        </el-form-item>
        <el-form-item>
          <el-button size="small" type="warning" plain @click.prevent="handleDelete">删除</el-button>
        </el-form-item>
      </el-form>

      <el-button type="text" @click="getData" :loading="loadingButton" size="small" class="m-float-right">仓库信息
      </el-button>


    </MyCard>
    <el-card v-if="response">
      <JsonCode :params="response"></JsonCode>
    </el-card>


  </div>
</template>
<script>
import MyCard from "@/components/MyComponents/MyCard";
import JsonCode from "@/views/log/components/JsonFormat";
import {validateUrl} from "@/utils/validate";
import {deleteGithubRecords} from "@/api/system";

export default {
  name: 'GithubOperation',
  components: {JsonCode, MyCard},
  data() {
    return {
      github: {url: '', sha: '', message: ''},
      loadingButton: false,
      rules: {
        url: [
          {validator: validateUrl, trigger: 'blur'}
        ],
        sha: [
          {required: true, message: 'sha不能为空！', trigger: 'blur'}
        ],
        message: [
          {required: true, message: '提交消息不能为空！', trigger: 'blur'}
        ]
      },
      response: {
        "content": null,
        "commit": {
          "sha": "48d00d8b1f85ad1f1932f1dcd0f64a9d864cf518",
          "node_id": "C_kwDOGAaK7doAKDQ4ZDAwZDhiMWY4NWFkMWYxOTMyZjFkY2QwZjY0YTlkODY0Y2Y1MTg",
          "url": "https://api.github.com/repos/WY-6991/wuxin/git/commits/48d00d8b1f85ad1f1932f1dcd0f64a9d864cf518",
          "html_url": "https://github.com/WY-6991/wuxin/commit/48d00d8b1f85ad1f1932f1dcd0f64a9d864cf518",
          "author": {
            "name": "WY-6991",
            "email": "65836396+WY-6991@users.noreply.github.com",
            "date": "2022-01-10T09:31:44Z"
          },
          "committer": {
            "name": "WY-6991",
            "email": "65836396+WY-6991@users.noreply.github.com",
            "date": "2022-01-10T09:31:44Z"
          },
          "tree": {
            "sha": "f32bff1eb3331f972eed73aa2cc73eedd699f6b9",
            "url": "https://api.github.com/repos/WY-6991/wuxin/git/trees/f32bff1eb3331f972eed73aa2cc73eedd699f6b9"
          },
          "message": "删除一张图片",
          "parents": [
            {
              "sha": "5b6dc533d9ec7a506966df9825d1aed7e868d0e1",
              "url": "https://api.github.com/repos/WY-6991/wuxin/git/commits/5b6dc533d9ec7a506966df9825d1aed7e868d0e1",
              "html_url": "https://github.com/WY-6991/wuxin/commit/5b6dc533d9ec7a506966df9825d1aed7e868d0e1"
            }
          ],
          "verification": {
            "verified": false,
            "reason": "unsigned",
            "signature": null,
            "payload": null
          }
        }
      }
    }
  },
  methods: {
    getData() {
      if (!this.isRoot) {
        this.$message.error('获取失败！无权限获取仓库信息')
        return
      }
      this.loadingButton = true
      setTimeout(() => {
        this.loadingButton = false
        window.open('https://api.github.com/repos/WY-6991/wuxin/contents/img', '_blank')
      }, 3000)
    }
    ,

    handleDelete() {
      this.$refs.githubOperation.validate(valid => {
        if (valid) {

          this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            if (!this.isRoot) {
              this.$message.error('操作失败！无权限执行该操作！')
              return
            }
            deleteGithubRecords(this.github).then(res => {
              if (res.code === 200) {
                this.$message.success("" + JSON.stringify(res.result))
                this.response = res.result
              }

            })

          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消删除'
            });
          });

        }
      })
    }

  }


}
</script>
<style scoped>

</style>
