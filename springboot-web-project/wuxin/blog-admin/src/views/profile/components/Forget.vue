<template>
  <div>

    <div class="step_container m-margin-tb-large">
      <el-steps finish-status="success">
        <el-step title="步骤 1" :description="description.first" :status="step.first" icon="el-icon-message" />
        <el-step title="步骤 2" :description="description.second" :status="step.second" icon="el-icon-upload2" />
        <el-step title="步骤 3" :description="description.third" :status="step.third" icon="el-icon-lock" />
      </el-steps>
      <el-button style="margin-top: 12px;" @click="next" :disabled="isDisabled">下一步</el-button>
    </div>


    <div class="container_el_form">
      <el-form class="el-form" size="small" :inline="true" :model="dataForm" :rules="dataRules" ref="dataForm">
        <el-form-item label="邮箱" prop="email">
          <el-input placeholder="请输入邮箱" v-model="dataForm.email" clearable />
        </el-form-item>
        <el-form-item label="验证码" prop="code">
          <el-input placeholder="请输入验证码" v-model="dataForm.code" clearable :disabled="disable.code" />
        </el-form-item>
      </el-form>
    </div>


    <div class="container_el_form" v-show="showPassInput">
      <el-form class="el-form" size="small" :inline="true" :rules="dataRules" ref="passDataForm"
               :model="dataForm">
        <el-form-item label="新密码" prop="newPass">
          <el-input placeholder="请输入密码" v-model="dataForm.newPass" clearable type="password"
                    :disabled="disable.password" />
        </el-form-item>
        <el-form-item label="重新输入" prop="reNewPass">
          <el-input placeholder="请重新输入密码" v-model="dataForm.reNewPass" clearable type="password"
                    :disabled="disable.password" />
        </el-form-item>
      </el-form>
    </div>

  </div>
</template>
<script>

import {validEmail} from "@/utils/validate";
import {updatePassByEmail, validUserEmail} from "@/api/user";

export default {
  name: 'ForgetPassword',
  data() {

    const validateEmail = (rule, value, callback) => {
      if (!validEmail(value)) {
        callback(new Error('邮箱格式输入错误'))
      }
      callback()
    }

    const validateCode = (rule, value, callback) => {
      if (value.length !== 6) {
        callback(new Error('验证码是6为数字哦！'))
      }
      callback()
    }

    const validatePass = (rule, value, callback) => {
      if (value !== this.newPass) {
        callback(new Error('两次密码输入不一致哦'))
      }
      this.active = 5
      callback()
    }
    return {
      dataForm: {
        email: '',
        code: '',
        password: '',
        rePassword: ''
      },
      dataRules: {
        email: [
          {required: true, validate: validateEmail, trigger: 'blur'},
        ],
        code: [
          {required: true, validate: validateCode, trigger: 'blur'},
        ],
        newPass: [
          {required: true, message: '请输入密码', trigger: 'blur'},
          {min: 4, max: 15, message: '长度在 4 到 15 个字符', trigger: 'blur'}
        ],
        reNewPass: [
          {required: true, validate: validatePass, trigger: 'blur'},
        ],
      },


      active: 0,
      isDisabled: true,

      step: {
        first: 'process',
        second: 'wait',
        third: 'wait',
      },

      disable: {
        code: true,
        password: true,
      },
      showPassInput: false,

      description: {
        first: '请输入邮箱',
        second: '',
        third: '',
        fourth: '',
        fifth: '',
        sixth: '',
      }
    }


  },

  watch: {

    // 1、第一步操作检查邮箱格式是否正确
    'dataForm.email'(newValue) {
      if (validEmail(newValue)) {

      } else {

      }
    },


    // 3、第三步，检查验证码基本格式
    'dataForm.code'(newValue) {
      if (newValue.length === 6) {

      } else {

      }
    },

    'fromData.reNewPass'(newValue) {
      if (newValue.length !== 0 && newValue === this.passDataForm.newPass) {

      } else {

      }
    }
  },

  methods: {
    next() {
      if (this.active === 1) {
        this.getEmailCode()
      }
      if (this.active === 2) {
        this.submit()
      } else {
        this.$message.error('操作失败！')
      }
    },


    // 2、第二步， 检查邮箱是否通过
    getEmailCode() {
      this.description.second = '邮箱校验中'
      validUserEmail(this.dataForm.email).then(res => {
          if (res.code === 200) {
            this.$message.success('验证码成功发送到你的邮箱！')
            this.active = 2
            this.isDisabled = false
          }
        }
      )
    },

    submit() {
      this.$refs.passDataForm.validate(valid => {
        if (valid) {
          updatePassByEmail({
            'email': this.dataForm.email, 'code': this.dataForm.code, 'password': this.dataForm.reNewPass
          }).then(res => {
            if (res.code === 200) {
              this.$message.success('密码修改成功！')
              // todo 密码修改之后需要返回登录
            }

          })
        }
      })
    },


  },

}
</script>
<style scoped>

.step_container {
  width: 600px;
}

.container_el_form {
  position: relative;
  margin-top: 50px;
}
</style>
