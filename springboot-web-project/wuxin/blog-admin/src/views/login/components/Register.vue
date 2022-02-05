<template>
  <div class="register-container">
    <el-form ref="registerForm" :model="loginForm" class="login-form" :rules="registerRules" size="small">
      <el-form-item prop="username">
        <el-input
          clearable
          prefix-icon="el-icon-user"
          v-model="loginForm.username"
          placeholder="用户名"
          type="text"
        />
      </el-form-item>
      <el-form-item prop="password">
        <el-input
          type="password"
          prefix-icon="el-icon-lock"
          v-model="loginForm.password"
          placeholder="密码"
          show-password
        />

      </el-form-item>
      <el-form-item prop="rePassword">
        <el-input
          type="password"
          prefix-icon="el-icon-lock"
          v-model="loginForm.rePassword"
          placeholder="密码"
          show-password
        />

      </el-form-item>
      <el-form-item prop="email">
        <el-input
          prefix-icon="el-icon-message"
          v-model="loginForm.email"
          placeholder="邮箱"
          typeof="text"
        />

      </el-form-item>
      <el-form-item>
        <el-input
          type="text"
          prefix-icon="el-icon-s-check"
          v-model="loginForm.code"
          placeholder="验证码"
        />

      </el-form-item>
      <el-form-item>
        <el-button type="primary" :loading="loading" style="width:100%;" @click.prevent="submit">
          {{ status === 1 ? '获取验证码' : '注册' }}
        </el-button>
        <el-button type="text" style="float: left;color: white" @click="showName('Pass')">已有账号,登录</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
import {mixin} from "./mix";
import {validEmail} from "@/utils/validate";
import {registerUser, getRegisterCode} from '@/api/user'

export default {
  name: 'Register',

  data() {

    const validRePassword = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码！'));
      }
      if (value !== this.loginForm.password) {
        callback(new Error('两次密码输入不一致！'));
      }
      callback()

    }

    const validateEmail = (rule, value, callback) => {
      if (!validEmail(value)) {
        callback(new Error('邮箱格式不正确！'));
      }
      callback()

    }
    return {
      loading:false,
      status: 1,
      loginForm: {
        username: '',
        password: '',
        rePassword: '',
        email: '',
        code: '',
      },
      registerRules: {
        username: [
          {required: true, min: 2, max: 15, message: '用户名由2-15个字符组成', trigger: 'blur'}
        ],
        password: [{
          validate: {required: true, min: 4, max: 15, message: '密码由4-15个字符组成', trigger: 'blur'},
          trigger: 'blur'
        }],
        rePassword: [{
          validator: validRePassword,
          trigger: 'blur'
        }],
        email: [{
          validator: validateEmail,
          trigger: 'blur'
        }]
      }

    }
  },

  mixins: [mixin],
  methods: {

    // 获取验证码
    submit() {
      this.$refs.registerForm.validate(valid => {
        if (valid) {
          setTimeout(()=>{
            if (this.status === 1) {
              getRegisterCode(this.loginForm).then(res => {
                if (res.code === 200) {
                  this.status = 2
                  this.$message.success('验证码已成功发送到你的邮箱了哦！')
                }
              })
            }

            if (this.status === 2) {
              if (this.loginForm.code === '' || this.loginForm.code === null) {
                this.$message.warning('验证码不能为空！')
                return;
              }
              registerUser(this.loginForm).then(res => {
                if (res.code) {
                  this.status = 1
                  this.$message.success('注册成功')
                  setTimeout(()=>{
                    // 注册成功之后跳转到密码登录
                   this.showName('Pass')
                  },2000)
                }
              })
            }

          },3000)


        }
      })
    },

    // 获取验证码


    //  注册
  },

}
</script>
<style scoped>
.register-container {
  padding-top: 20px !important;
}


</style>
