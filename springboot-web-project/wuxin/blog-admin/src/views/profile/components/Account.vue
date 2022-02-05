<template>
  <div>
    <transition name="el-zoom-in-top">
      <div v-show="showPass">
        <el-form :label-width="labelWidth" size="small" :rules="rules" :mode="formData" ref="changePass">
          <!--          <el-form-item  v-show="showPassTip.noShow">-->
          <!--            <el-tooltip content="点击关闭" placement="bottom" :openDelay="1">-->
          <!--              <el-popconfirm-->
          <!--                confirm-button-text='好的'-->
          <!--                cancel-button-text='不用了'-->
          <!--                icon="el-icon-info"-->
          <!--                icon-color="red"-->
          <!--                @onConfirm = "showPassTip.noShow = false"-->
          <!--                @onCancel = "showPassTip.show = false"-->
          <!--                title="不再显示？"-->
          <!--              >-->
          <!--                <el-alert-->
          <!--                  slot="reference"-->
          <!--                  title="提示:用户名为用户登录账号,谨慎修改！"-->
          <!--                  type="warning"-->
          <!--                  :center="true"-->
          <!--                  v-show="showPassTip.show"-->
          <!--                  style="width: 60%;min-width: 200px; cursor: pointer;"-->
          <!--                  :closable="false"-->
          <!--                  class="m-margin-tb-mini">-->
          <!--                </el-alert>-->
          <!--              </el-popconfirm>-->
          <!--            </el-tooltip>-->
          <!--          </el-form-item>-->
          <el-form-item label="用户名" prop="username">
            <el-input v-model.trim="formData.username" prefix-icon="el-icon-user" />
          </el-form-item>
          <el-form-item label="旧密码" prop="oldPass">
            <el-input v-model.trim="formData.old" prefix-icon="el-icon-key" />
          </el-form-item>
          <el-form-item label="新密码" prop="newPass">
            <el-input v-model.trim="formData.new" prefix-icon="el-icon-lock" />
          </el-form-item>
          <el-form-item label="确认新密码" prop="reNewPass">
            <el-input v-model.trim="formData.reNew" prefix-icon="el-icon-lock" />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click.prevent="submit">修改</el-button>
          </el-form-item>
        </el-form>
      </div>
    </transition>

    <transition name="el-zoom-in-top">
      <div v-show="!showPass" class="m-display-flex m-flex-justify-around">
        <ForgetPassword></ForgetPassword>
      </div>
    </transition>
    <el-button type="text" @click="showPass=!showPass" class="m-float-right">{{ showPass ? '忘记密码' : '返回' }}</el-button>
  </div>

</template>

<script>
import {mapGetters} from "vuex";
import ForgetPassword from "@/views/profile/components/Forget";
import {validateUsername} from "@/utils/validate";
import {updatePass} from "@/api/user";

export default {
  components: {ForgetPassword},
  props: {
    user: {
      type: Object,
      default: () => {
        return {
          name: '',
          email: ''
        }
      }
    },
    labelWidth: {
      type: String,
      default: "100px"
    }
  },

  computed: {
    ...mapGetters(['name', 'userId'])
  },

  data() {

    let validateOldPass = (rule, value, callback) => {
      console.log("value=>", value)
      if (value === '') {
        callback(new Error('请输入原密码！'));
      }
      if (value.length < 4 || value.length > 15) {
        callback(new Error('密码4~15位！'));
      }
      callback()

    }
    let validateNewPass = (rule, value, callback) => {
      console.log("value=>", value)
      if (value === '') {
        callback(new Error('请输入新密码！'));
      }
      if (value.length < 4 || value.length > 15) {
        callback(new Error('密码4~15位！'));
      }
      callback()

    }
    const validateRePass = (rule, value, callback) => {
      console.log("value=>", value)
      if (value === '') {
        callback(new Error('请重新输入密码！'));
      }
      if (this.user.newPass !== value) {
        callback(new Error('两次密码输入不一致'));
      }
      callback()
    }

    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (this.user.newPass !== value) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };
    return {
      showPass: true,
      formData: {
        username: '',
        oldPass: '',
        newPass: '',
        rePass: '',
      },
      showPassTip: {
        // 不在显示，默认为显示
        noShow: true,
        //提示
        show: false
      },
      rules: {
        username: [
          validateUsername()
        ],
        oldPass: [
          {required: true, min: 4, max: 15, message: '密码4-15个字符', trigger: 'blur'}
        ],
        newPass: [
          {required: true, min: 4, max: 15, message: '密码4-15个字符', trigger: 'blur'}
        ],
        rePass: [
          {validator: validatePass2, trigger: 'blur'}
        ],
      }
    }
  },

  watch: {
    'pass.username'() {
      setTimeout(() => {
        this.showPassTip.show = true
      }, 1000)
    }
  },

  created() {
    // 初始化用户名
    this.formData.username = this.name
  },
  methods: {
    submit() {
      this.$refs['changePass'].validate(valid => {
        if (valid) {
          let data = {
            'username': this.formData.username,
            'oldPassword': this.formData.oldPass,
            'newPassword': this.formData.new
          }
          updatePass(data).then(res => {
            if (res.code === 200) {
              this.$notify.success("修改成功！")
            }
          })
        }
      })
    },
  }
}
</script>
