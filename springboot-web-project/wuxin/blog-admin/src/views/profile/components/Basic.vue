<template>
  <div>
    <transition-group name="el-zoom-in-center">

        <div v-show="showBasic" key="other1">
          <el-form :label-width="labelWidth" size="small" :rules="rules" ref="chatUrl" >
            <el-form-item  prop="url">
              <el-tooltip content="点击访问github">
                <el-link :href="chatUrl.github"  :underline="false"  target="_blank" class="share-type">github</el-link>
              </el-tooltip>
              <el-input prefix-icon="el-icon-position" v-model.trim="chatUrl.github" />

            </el-form-item>
            <el-form-item  prop="url">
              <el-tooltip content="点击访问gitee">
                <el-link :href="chatUrl.gitee"  :underline="false"  target="_blank" class="share-type">gitee</el-link>
              </el-tooltip>
              <el-input prefix-icon="el-icon-position" v-model.trim="chatUrl.gitee" />

            </el-form-item>
            <el-form-item  prop="url">
              <el-tooltip content="点击访问哔哩哔哩">
                <el-link :href="chatUrl.bilibili" :underline="false" target="_blank" class="share-type">bilibili</el-link>
              </el-tooltip>
              <el-input prefix-icon="el-icon-position" v-model.trim="chatUrl.bilibili" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click.prevent="submit" >提交</el-button>
            </el-form-item>
          </el-form>
        </div>

        <div v-show="!showBasic" key="other2">
          <el-alert
            title="注意，显示滑稽说明图片没有显示成功或者上传失败"
            type="warning">
          </el-alert>
          <el-card style="text-align: center">
            <div slot="header"><span>联系方式</span></div>
            <el-row :gutter="20">
              <el-col :span="12" :xs="24">
                <el-tag>QQ</el-tag>
                <CustomUploadAvatar :image="chatUrl.qq" @updateImage="uploadQQ"></CustomUploadAvatar>
              </el-col>
              <el-col :span="12" :xs="24">
                <el-tag>微信</el-tag>
                <CustomUploadAvatar :image="chatUrl.wechat" @updateImage="uploadWechat"></CustomUploadAvatar>
              </el-col>
            </el-row>

          </el-card>

          <el-card style="text-align: center" class="m-margin-tb-mini">
            <div slot="header"><span>支付方式</span></div>
            <el-row :gutter="20">
              <el-col :span="12" :xs="24">
                <el-tag type="success">支付宝</el-tag>
                <CustomUploadAvatar :image="chatUrl.alipay" @updateImage="uploadAlipay"></CustomUploadAvatar>
              </el-col>
              <el-col :span="12" :xs="24">
                <el-tag type="success">微信</el-tag>
                <CustomUploadAvatar :image="chatUrl.wechatPayment" @updateImage="uploadWechatPayment"></CustomUploadAvatar>
              </el-col>
            </el-row>

          </el-card>
        </div>
    </transition-group>
    <el-button type="text"  class="m-float-right" :icon="showBasic?'el-icon-arrow-right':'el-icon-arrow-left'" @click="showBasic=!showBasic">{{ showBasic?'更多':'返回' }}</el-button>

  </div>

</template>

<script>
import CustomUploadAvatar from "@/components/MyComponents/CustomUploadAvatar";
import {updateChatUrl} from "@/api/chaturl";
import { validURL} from "@/utils/validate";

export default {
  name:'Basic',
  components: {CustomUploadAvatar},
  props: {
    chatUrl: {
      type: Object,
      default: () => {
        return {
          charUrl: {},
        }
      }
    },
    labelWidth:{
      type:String,
      default:"0px"
    }
  },
  data() {

    const validateUrl = (rule, value, callback) => {
      if (!validURL(value)) {
        callback(new Error('邮箱格式输入错误'))
      }
      callback()
    }

    return {
      showBasic: true,
      chat:{
        qq:require('@/assets/image/qq.jpg'),
        wechat:require('@/assets/image/weixin.jpg'),
        alipay:require('@/assets/image/alipay.jpg'),
        wechatPayment:require('@/assets/image/wechatPayment.jpg')
      },
      rules:{
          url: [
            {validate: validateUrl, trigger: 'blur'},
          ],
      }
    }
  },
  methods: {


    submit() {
      this.$refs.chatUrl.validate(valid=>{
        if(valid){
          updateChatUrl(this.chatUrl).then(res=>{
            if(res.code === 200){
              this.$message.success('修改成功！')
            }
          })
        }
      })

    },
    /*qq*/
    uploadQQ(url){
      this.chatUrl.qq = url;
      updateChatUrl(this.chatUrl).then(res=>{
        if(res.code === 200){
          this.chatUrl.qq = url;
          this.$message.success('修改成功！')
        }
      })
    },
    /*微信*/
    uploadWechat(url){
      this.chatUrl.wechat = url;
      updateChatUrl(this.chatUrl).then(res=>{
        if(res.code === 200){
          this.chatUrl.wechat = url;
          this.$message.success('修改成功！')
        }
      })
    },
    /*支付宝*/
    uploadAlipay(url){
      this.chatUrl.alipay = url;
      updateChatUrl(this.chatUrl).then(res=>{
        if(res.code === 200){
          this.chatUrl.alipay = url;
          this.$message.success('修改成功！')
        }
      })
    },
    /*微信支付*/
    uploadWechatPayment(url){
      this.chatUrl.wechatPayment = url;
      updateChatUrl(this.chatUrl).then(res=>{
        if(res.code === 200){
          this.chatUrl.wechatPayment = url;
          this.$message.success('修改成功！')
        }
      })
    },
  }
}
</script>

<style scoped>
.share-type{
  font-size: 16px !important;
}
</style>
