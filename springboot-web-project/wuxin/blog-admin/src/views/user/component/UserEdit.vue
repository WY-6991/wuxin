<template>
  <!-- 表格 -->
  <div class="app-container">
    <el-form
      ref="dataForm"
      :rules="rules"
      :model="temp"
      :size="size"
      label-width="70px"
      style="width: 400px; margin-left:50px;"
    >
      <el-form-item >
        <CustomUploadAvatar :image="temp.avatar" :border-radius="true"></CustomUploadAvatar>
      </el-form-item><el-form-item label="昵称" prop="username">
        <el-input v-model="temp.nickname" prefix-icon="el-icon-user" />
      </el-form-item>
      <el-form-item label="邮箱" prop="email">
        <el-input v-model="temp.email" prefix-icon="el-icon-message" />
      </el-form-item>

      <el-form-item label="头像" prop="avatar">
        <el-input v-model="temp.avatar" prefix-icon="el-icon-picture" />
      </el-form-item>

      <el-form-item label="电话" prop="phone">
        <el-input v-model="temp.phone" prefix-icon="el-icon-phone" />
      </el-form-item>

      <el-form-item label="motto">
        <el-input
          v-model="temp.motto"
          :autosize="{ minRows: 2, maxRows: 4 }"
          type="textarea"
          placeholder="基本信息"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="info" @click="cancel">取消</el-button>
        <el-button type="primary" @click="update(temp)">确认</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>

import { validEmail, validURL, validPhone } from '@/utils/validate'
import ImageCropper from '@/components/ImageCropper'
import PanThumb from '@/components/PanThumb'
import { updateUser } from '@/api/user'
import CustomUploadAvatar from "@/components/MyComponents/CustomUploadAvatar";
import {mapGetters} from "vuex";

export default {
  name: 'UserEdit',
  components: {CustomUploadAvatar, ImageCropper, PanThumb },

  data() {
    const validateEmail = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入邮箱！'));
      }
      if (!validEmail(value)) {
        callback(new Error('邮箱格式不正确！'));
      }
      callback()

    }
    const validateAvatar = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入图片地址！'));
      }
      if (!validURL(value)) {
        callback(new Error('图片地址格式不正确！'));
      }
      callback()
    }
    const validatePhone = (rule, value, callback) => {
      if (!validPhone(value)) {
        callback(new Error('手机号格式不正确！'));
      }
      callback()
    }
    return {
      listLoading: true,
      imagecropperShow: false,
      headers: {'Content-Type': 'multipart/form-data','Authentication':this.token},
      field: 'file',
      rules: {
        username: [
          {required: true, message: "用户名必填", trigger: "blur"}
        ],
        email: [
          {required: true, validator: validateEmail, trigger: 'blur'}
        ],
        avatar: [
          {required: true, validator: validateAvatar, trigger: 'blur'}
        ],
        phone: [
          {validator: validatePhone, trigger: 'blur'}
        ],

      },

    };
  },

  props: {
    temp: {
      type: Object,
      default: {
        userId: 0,
        username: 'admin',
        avatar: 'https://cdn.jsdelivr.net/gh/WY-6991/wuxin/img/202111/20211126201439.png'
      }
    },
    size:{
      type:String,
      default:"small"
    },
    updateUserInfo: {
      type: Function,
      default: () => {
      }
    }
  },

  computed:{
    ...mapGetters(['token'])
  },

  methods: {
    //修改用户信息
    update(user) {
      this.$refs["dataForm"].validate(valid => {
        if (valid) {
          updateUser(user).then(res => {
            if (res.code === 200) {
              this.$emit("getList") // 加载数据
              this.$emit('cancelEdit') // 关闭编辑框
              this.$nextTick(() => {  // 清空数据
                this.$refs["dataForm"].clearValidate();
              });
              this.$notify.success("修改成功")
            }
          })
        }
      });


    },

    // 取消操作！
    close() {
      this.imagecropperShow = false
    },
    // 上传图片地址
    cropSuccess(avatarUrl) {
      this.temp.avatar = avatarUrl
    },
    cancel() {
      this.$emit('cancelEdit')
    }


  }
};
</script>


