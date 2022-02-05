<template>
  <div>

    <div>
      <!-- 对话框 -->
      <image-cropper
        v-show="imagecropperShow"
        :width="300"
        :height="300"
        lang-type="zh"
        @close="close"
        @crop-upload-success="cropSuccess"
      />
    </div>

    <el-form size="mini" :rules="rules" ref="dataForm">
      <el-form-item>
        <el-image :src="image" @click.prevent="imagecropperShow=true" style="width: 150px;height: 150px;">
          <div slot="error">
            <el-image :src="errorUrl" @click="imagecropperShow=true" />
          </div>
        </el-image>
      </el-form-item>
      <el-form-item>
        <el-tooltip placement="right" content="点击上传图片" effect="light">
          <el-button type="primary" size="mini" icon="el-icon-upload" @click="imagecropperShow=true">上传图片</el-button>
        </el-tooltip>
      </el-form-item>
      <el-form-item prop="image">
        <el-tooltip placement="bottom" content="输入地址也可修改图片" effect="light">
          <el-input v-model="image" @change="cropSuccess" />
        </el-tooltip>
      </el-form-item>
    </el-form>


  </div>
</template>
<script>
import ImageCropper from '@/components/ImageCropper'
import {validURL} from "@/utils/validate";

export default {
  name: 'ImageCode',
  components: {
    ImageCropper
  },
  data() {
    let validateAvatar = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入图片地址！'));
      }
      if (!validURL(value)) {
        callback(new Error('图片地址格式不正确！'));
      }
      callback()
    }
    return {
      imagecropperShow: false,
      errorUrl: 'https://cdn.jsdelivr.net/gh/WY-6991/wuxin/img/202112/20211203095430.png',
      rules: {
        image: [
          {required: true, validator: validateAvatar, trigger: 'blur'}
        ],
      }
    }
  },
  props: {
    image: {
      type: String,
      default: 'https://cdn.jsdelivr.net/gh/WY-6991/wuxin/img/202112/20211202195650.png'
    },
  },

  methods: {
    // 取消操作！
    close() {
      this.imagecropperShow = false
    },
    // 上传图片地址
    cropSuccess(avatarUrl) {
      this.$emit('updateImage', avatarUrl)
    },
  },

}
</script>
<style scoped>

.el-icon-picture {
  width: 150px !important;
  height: 150px !important;
}
</style>
