<template>
  <div class="pass-container">
    <el-form
      ref="loginForm"
      :model="loginForm"
      :rules="rules"
      class="login-form"
      autocomplete="off"
      size="small"
    >
      <el-form-item prop="username">

        <el-input
          v-model="loginForm.username"
          clearable
          prefix-icon="el-icon-user"
          placeholder="用户名"
          type="text"
          autocomplete="off"
        />
      </el-form-item>

      <el-form-item prop="password">
        <el-input
          v-model="loginForm.password"
          prefix-icon="el-icon-message"
          placeholder="密码"
          show-password
          @keyup.enter.native="handleLogin"
        />

      </el-form-item>
      <el-form-item>
        <el-button
          :loading="loading"
          type="primary"
          style="width:100%;"
          @click.native.prevent="handleLogin"
        >登录
        </el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="text" style="float: left;color: white" @click="showName('Register')">注册</el-button>
        <el-button type="text" style="float: right;color: white" @click="showName('Email')">忘记密码</el-button>
      </el-form-item>
    </el-form>

  </div>

</template>

<script>
import { mixin } from './mix'
export default {
  name: 'PassTab',
  mixins: [mixin],
  data() {
    return {
      loginForm: {
        username: '',
        password: '',
        rememberMe: false
      },

      capsTooltip: false,
      loading: false,
      showDialog: false,
      redirect: undefined,
      otherQuery: {},
      rules: {
        username: [
          { required: true, min: 2, max: 15, message: '用户名由2-15个字符组成', trigger: 'blur' }
        ],
        password: [
          { required: true, min: 5, max: 15, message: '密码4-15个字符组成', trigger: 'blur' }
        ]
      }
    }
  },
  watch: {
    $route: {
      handler: function(route) {
        const query = route.query
        if (query) {
          this.redirect = query.redirect
          this.otherQuery = this.getOtherQuery(query)
        }
      },
      immediate: true
    }
  },
  methods: {

    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true
          setTimeout(() => {
            this.$store.dispatch('user/login',
              {
                'username': encodeURIComponent(this.loginForm.username),
                'password': this.loginForm.password
              })
              .then(() => {
                this.loading = false
                this.$router.push({ path: this.redirect || '/', query: this.otherQuery })
              })
              .then(() => {
                this.$store.dispatch('user/getInfo')
              })
              .catch(() => {
                this.loading = false
              })
          }, 2000)
        } else {
          return false
        }
      })
    },
    getOtherQuery(query) {
      return Object.keys(query).reduce((acc, cur) => {
        if (cur !== 'redirect') {
          acc[cur] = query[cur]
        }
        return acc
      }, {})
    }
  }
}
</script>
<style scoped>

.pass-container {
  padding-top: 20px;
}

</style>

