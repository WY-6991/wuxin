<template>
  <div class="login-container">
    <el-row>
      <el-col :span="24">
        <el-input type="text" autocomplete="off" v-model="user.token" @keyup.enter.native="getUser" class="m-input"
                  clearable
                  placeholder="请输入你的仓库令牌！">
          <el-select slot="prepend" v-model="tokenType" style="width: 100px;" @change="changeType">
            <el-option value="github" label="github"></el-option>
            <el-option value="gitee" label="gitee"></el-option>
          </el-select>
          <el-button slot="append" icon="el-icon-search" @click="getUser"></el-button>
        </el-input>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="24" class="m-display-flex-column">
        <span class="m-margin-lf-small">当前用户</span>
        <el-avatar :src="user.avatar" :size="50" class="m-margin-lf-small">user</el-avatar>
        <span class="m-margin-lf-small">{{ user.username ? user.username : '未配置' }}</span>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="24">
        <el-button type="info" icon="el-icon-close" @click="removeUser">清除</el-button>
        <el-button type="primary" icon="el-icon-check" :disabled="isDisabled" @click="saveUser">保存</el-button>
        <el-button type="primary" :disabled="isDisabled">登录</el-button>
      </el-col>
    </el-row>
  </div>
</template>
<script>


export default {
  name: 'Login',
  data() {
    return {
      user: {
        token: '',
        username: '',
        url: '',
        avatar: ''
      },
      tokenType: 'github',

    }
  },
  computed: {
    type() {
      return this.$store.getters.tokenType
    },
    isDisabled() {
      return !(this.user && this.user.token && this.user.username && this.user.avatar)
    }
  },

  created() {
    this.tokenType = this.type
    this.changeType()
  },

  methods: {

    getUser() {
      const data = {tokenType: this.tokenType, token: this.user.token}
      this.$store.dispatch('getInfo', data).then((res) => {
        this.user = res
        this.isDisabled = false
      })
    },

    changeType() {
      this.$store.dispatch('getUser', this.tokenType).then(res => {
        this.user = res
        this.isDisabled = false
      })
    },

    saveUser() {
      this.$store.dispatch('saveUser', {'user': this.user, 'tokenType': this.tokenType})

    },

    removeUser() {
      this.$store.dispatch('removeUser', this.tokenType)
      this.user = {}
    }
  },

}
</script>
<style scoped>

.login-container {
  position: fixed;
  display: flex;
  flex-direction: column;
  align-items: center;
  height: 100vh;
  width: 100%;
  top: 200px;
}

.el-row {
  margin: 10px !important;
}

.m-input {
  width: 500px;
  min-width: 300px;
}

.m-display-flex-column {
  display: flex;
  align-items: center;
}

.m-margin-lf-small {
  margin: 0 5px;
}

</style>