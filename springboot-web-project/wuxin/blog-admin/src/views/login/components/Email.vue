<template>
  <div class="email-container">
    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form" autocomplete="off"
             size="small">

      <el-form-item prop="email">
        <el-input
          clearable
          prefix-icon="el-icon-message"
          v-model="loginForm.email"
          placeholder="请输入邮箱"
          type="text"
          autocomplete="off"
        />
      </el-form-item>
      <el-form-item prop="code">
        <div class="code-container">
          <el-input
            prefix-icon="el-icon-message"
            v-model="loginForm.code"
            type="text"
            placeholder="请输入验证码"
            style="width: 200px;"
          />

          <el-button @click="getCode()" :disabled="codeBtn" style="width: 100px;text-align: center">{{
              codeContent
            }}
          </el-button>
        </div>


      </el-form-item>

      <el-form-item>

        <el-button :loading="loading" type="primary"
                   style="width:100%;"
                   :disabled="isDisabled"
                   @click.native.prevent="login()">登录
        </el-button>
      </el-form-item>

      <el-form-item>
        <el-button type="text" @click="showName('Pass')" style="color: white;">密码登录</el-button>
        <el-button type="text" @click="showName('Register')" style="color: white;float: right"> 注册</el-button>
      </el-form-item>
    </el-form>


  </div>

</template>

<script>


import {mixin} from './mix'
import {validEmail} from "@/utils/validate";

export default {
  name: 'Email',
  data() {
    const validateEmail = (rule, value, callback) => {
      if (!value) {
        callback(new Error('请输入邮箱'))
      }
      if (!validEmail(value)) {
        callback(new Error('邮箱格式不正确'))
      }
      callback()
    }
    const validateCode = (rule, value, callback) => {
      if (value.length === 0) {
        callback(new Error('请输入验证码！'))
      }
      if (value.length !== 6) {
        callback(new Error('验证码是6位数哦！'))
      }
      callback()
    }
    return {
      loginForm: {
        email: '',
        code: ''
      },
      loading: false,
      loginRules: {
        email: [{required: true, trigger: 'blur', validator: validateEmail}],
        code: [{required: true, trigger: 'blur', validator: validateCode}]
      },
      redirect: undefined,
      isDisabled: true,// 默认为禁用状态
      codeBtn: true,
      codeContent: '获取验证码',
      otherQuery: {},
      count: 60,
    }
  },
  mixins: [mixin],
  watch: {
    $route: {
      handler: function (route) {
        const query = route.query
        if (query) {
          this.redirect = query.redirect
          this.otherQuery = this.getOtherQuery(query)
        }
      },
      // 立即执行监听
      immediate: true
    },

    'loginForm.email'(newValue) {
      if (validEmail(newValue)) {
        // 取消禁用状态
        this.codeBtn = false
        // 提示用户获取验证码

      } else {
        // 回复禁用状态
        this.isDisabled = true
        this.codeBtn = true
        // 提示文字
      }

    },

    // 监听邮箱和验证码格式是否输入正确
    'loginForm.code'(newValue) {
      this.isDisabled = !newValue;
    }
  },

  methods: {

    // 60s之后重新获取验证码
    disableGetCode() {

      const myTimer = setInterval(() => {
        if (this.count > 0) {
          this.codeBtn = true
          this.count--
          this.codeContent = this.count + 's'
        } else {
          this.codeBtn = false
          this.count = 60
          this.codeContent = '获取验证码'
          window.clearInterval(myTimer)
        }
      }, 1000)

    },


    getCode() {

      this.$store.dispatch('user/getEmailCode', this.loginForm.email).then(res => {
        // 获取验证码之后需要等待60s,减轻服务器压力
        if (res.code === 200) {
          this.isDisabled = false
          //执行60s之后重新获取验证码
          this.disableGetCode()
          this.$message.success('邮箱已发送到你的邮箱中了哦！请及时查收！')
        }
      })
    },

    login() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true
          setTimeout(() => {
            this.$store.dispatch('user/loginToEmail', this.loginForm)
              .then((res) => {
                if (res.code === 200) {
                  // 获取登录用户信息
                  this.$store.dispatch('user/getInfo', this.loginForm)
                  this.loading = false
                  setTimeout(() => {
                    this.$router.push({path: this.redirect || '/', query: this.otherQuery})
                  }, 500)
                }

              })
              .catch(() => {
                this.loading = false
              })

          }, 3000)
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
    },

  }
}
</script>
<style scoped>

.email-container {
  padding-top: 20px;
}

.code-container {
  display: flex !important;
  justify-content: space-between;

}
</style>



