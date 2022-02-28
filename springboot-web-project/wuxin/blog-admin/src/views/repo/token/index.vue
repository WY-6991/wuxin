<template>
  <div class="app-container">
    <el-row class="m-margin-tb-small">
      <el-col :span="8">
        <el-alert type="warning" title="输入token查看用户仓库配置" />
      </el-col>
    </el-row>
    <el-row class="m-margin-tb-small">
      <el-col :span="8">
        <el-input v-model="user.token" placeholder="请输入token配置">
          <el-button slot="append" type="primary" icon="el-icon-search" @click="getTokenInfo">搜索</el-button>
        </el-input>
      </el-col>
    </el-row>
    <el-row class="m-margin-tb-small">
      <el-col :span="8">
        <span class="middle">当前用户:</span>
        <el-avatar :size="50" :src="user.avatar">user</el-avatar>
        <span class="middle">{{ user.username ? user.username : '未配置' }}</span>
      </el-col>
    </el-row>
    <el-row class="m-margin-tb-small">
      <el-col :span="12">
        <el-button type="primary" :disabled="disabled" icon="el-icon-check" @click="save">保存配置</el-button>
        <el-button type="info" icon="el-icon-close" @click="clean">清除配置</el-button>
        <el-button type="primary" icon="el-icon-setting" @click="toSetting">查看配置</el-button>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import {
  getUserInfo
} from '@/api/github'
import {
  repo
} from '../repo_mixins'

export default {
  name: 'RepoToken',
  mixins: [repo],
  methods: {
    getTokenInfo() {
      if (this.token === '') {
        this.$message.error('请输入token！')
        return
      }
      getUserInfo(this.user.token).then((res) => {
        console.log('res', JSON.stringify(res))
        const { login, avatar_url } = res
        this.user.username = login
        this.user.avatar = avatar_url
        this.disabled = false
      })
    },

    save() {
      window.localStorage.setItem(this.key, JSON.stringify(this.user))
      this.$message.success('保存成功')
    },

    clean() {
      window.localStorage.removeItem(this.key)
      this.$message.success('清除成功！')
    },
    toSetting() {
      this.$router.push('/repo/user')
    }

  }
}
</script>

<style lang="scss" scoped>

.el-alert .el-row, .el-row .el-row {
  margin-top: 20px;
}

.m-margin-tb-small {
  margin-top: 10px !important;
  margin-bottom: 10px !important;
}

.el-avatar {
  vertical-align: middle;
  margin-right: 15px;
  margin-left: 15px;
}

.middle {
  vertical-align: middle;
}

</style>
