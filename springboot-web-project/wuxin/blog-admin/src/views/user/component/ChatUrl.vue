<template>
  <!-- 表格 -->
  <div class="app-container">
    <el-form
      ref="dataForm"
      :rules="rules"
      :model="dataForm"
      label-position="left"
      label-width="70px"
      style="width: 400px; margin-left:50px;"
    >
      <el-form-item label="QQ" >
        <el-popover placement="right-end" trigger="hover" >
          <ImageCode :image="dataForm.qq"  @updateImage="updateImageQQ"/>
          <el-button slot="reference" size="mini" type="primary">显示图片</el-button>
        </el-popover>
      </el-form-item>
      <el-form-item label="微信" >
        <el-popover placement="right-end" trigger="hover">
          <ImageCode :image="dataForm.wechat" @updateImage="updateImageWechat"/>
          <el-button slot="reference" size="mini" type="primary">显示图片</el-button>
        </el-popover>

      </el-form-item>
      <el-form-item label="微信支付">
        <el-popover placement="right-end" trigger="hover">
          <ImageCode :image="dataForm.wechatPayment" @updateImage="updateImageWechatPayment" />
          <el-button slot="reference" size="mini" type="primary">显示图片</el-button>
        </el-popover>

      </el-form-item>
      <el-form-item label="支付宝">
        <el-popover placement="right-end" trigger="hover">
          <ImageCode :image="dataForm.alipay" @updateImage="updateImageAlipay"/>
          <el-button slot="reference" size="mini" type="primary">显示图片</el-button>
        </el-popover>

      </el-form-item>
      <el-form-item label="gitee" prop="url">
        <el-input prefix-icon="el-icon-s-promotion" v-model="dataForm.gitee"/>
      </el-form-item>
      <el-form-item label="github" prop="url">
        <el-input prefix-icon="el-icon-s-promotion" v-model="dataForm.github"/>
      </el-form-item>
      <el-form-item label="bilibili" prop="url">
        <el-input prefix-icon="el-icon-s-promotion" v-model="dataForm.bilibili"/>
      </el-form-item>
      <el-form-item>
        <el-button>取消</el-button>
        <el-button type="primary" @click="update(dataForm)">确认</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>

import {validURL} from '@/utils/validate'
import {findChatUrl, updateChatUrl} from "@/api/chaturl";
import ImageCode from "@/views/user/component/Image";

export default {
  name: "ChatUrl",
  components: {ImageCode},
  data() {

    let validateUrl = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入图片地址！'));
      }
      if (!validURL(value)) {
        callback(new Error('图片地址格式不正确！'));
      }
      callback()
    }

    return {
      rules: {
        url: [
          {required: true, validator: validateUrl, trigger: 'blur'}
        ],
      },
      dataForm: {}
    };
  },

  props: {
    userId: {
      type: Number,
      default: 1
    },
  },


  methods: {

    //修改用户信息
    update(data) {
      this.$refs["dataForm"].validate(valid => {
        if (valid) {
          updateChatUrl(data).then(res => {
            if (res.code === 200) {
              this.$notify.success("修改成功！")
            }
          })
        }
      });


    },

    updateImageQQ(url){
      this.dataForm.qq = url
    },
    updateImageWechat(url){
      this.dataForm.wechat = url
    },
    updateImageWechatPayment(url){
      this.dataForm.wechatPayment = url
    },
    updateImageAlipay(url){
      this.dataForm.alipay = url
    },

    find(userId) {
      findChatUrl(userId).then(res => {
        console.log(res.result)
        this.dataForm = res.result
      })
    },


    // 取消操作！
    close() {
      this.imagecropperShow = false
    },


  },

  created() {
    this.find(this.userId)
  }
};
</script>


