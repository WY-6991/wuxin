<template>
  <div>
    <el-row>
      <el-alert type="warning" title="输入仓库token获取配置"></el-alert>
    </el-row>
    <el-row>
      <el-input v-model="user.token" @keyup.enter="getUserInfo">
        <el-select v-model="tokenType" slot="prepend" style="width: 100px;" @change="changeType">
          <el-option value="github" label="github"></el-option>
          <el-option value="gitee" label="gitee"></el-option>
        </el-select>
        <el-button slot="append" icon="el-icon-search" @click="getUserInfo"></el-button>
      </el-input>
    </el-row>
    <el-row>
      <span class="middle m-margin-lr">当前用户</span>
      <el-avatar :src="user.avatar" class="middle m-margin-lr">user</el-avatar>
      <el-tooltip content="点击前往仓库" :disabled="!user.username">
        <el-link target="_blank" :href="user.url" :underline="false" :disabled="!user.username"
                 class="middle m-margin-lr">{{ user.username ? user.username : '未配置' }}
        </el-link>
      </el-tooltip>
    </el-row>
    <el-row>
      <el-button type="info" size="small" icon="el-icon-close" @click="removeSetting">清除配置</el-button>
      <el-button type="primary" size="small" icon="el-icon-check" :disabled="isDisabled" @click="saveSetting">保存配置
      </el-button>
    </el-row>
  </div>
</template>
<script>
import {getUserInfo} from '@/api/github'
import {getGiteeUserInfo} from '@/api/gitee'
import {setSetStore, getStore, removeStore} from "@/utils/session";
import {TOKEN_GITEE_KEY, TOKEN_GITHUB_KEY, TOKEN_KEY_TYPE} from '@/store/mutations-type.js'

export default {
  name: 'Token',
  data() {
    return {
      user: {
        token: '',
        username: '',
        url: '',
        avatar: ''
      },
      tokenType: 'github',
      isDisabled: true,
    }
  },

  created() {
    this.tokenType = this.getType()
    this.getSetting()
  },
  methods: {
    getUserInfo() {
      if (this.tokenType === 'github') {
        getUserInfo(this.user.token).then(res => {
          const {login, avatar_url, html_url} = res
          this.user.username = login
          this.user.avatar = avatar_url
          this.user.url = html_url
          this.isDisabled = false
        })
      } else {
        getGiteeUserInfo(this.user.token).then(res => {
          const {login, avatar_url, html_url} = res
          this.user.username = login
          this.user.avatar = avatar_url
          this.user.url = html_url
          this.isDisabled = false
        })
      }

    },

    changeType() {
      setSetStore(TOKEN_KEY_TYPE, this.tokenType)
      this.getSetting()
    },

    getType() {
      return getStore(TOKEN_KEY_TYPE) ? getStore(TOKEN_KEY_TYPE) : 'github'
    },

    saveSetting() {
      this.getSetting()
      if (this.user && this.user.token && this.user.username && this.user.avatar) {
        this.isDisabled = false
      }
      if (this.tokenType === 'github') {
        setSetStore(TOKEN_GITHUB_KEY, this.user)
      } else {
        setSetStore(TOKEN_GITEE_KEY, this.user)
      }
      this.$message.success('保存成功！')
    },

    getSetting() {
      let user = {};
      if (this.tokenType === 'github') {
        user = getStore(TOKEN_GITHUB_KEY)
      } else {
        user = getStore(TOKEN_GITEE_KEY)
      }
      if (user && user.token && user.username && user.avatar) {
        this.user = user
        this.isDisabled = false
      }

    },

    removeSetting() {
      if (this.tokenType === 'github') {
        removeStore(TOKEN_GITHUB_KEY)
      } else {
        removeStore(TOKEN_GITEE_KEY)
      }
      this.$message.success('清除成功！')
    }
  },

}
</script>
<style scoped>
.el-row {
  margin: 20px !important;
}

.middle {
  vertical-align: middle !important;
}

</style>