<template>
  <div>
    <image-cropper
      v-show="imageCropperShow"
      :width="imageWidth"
      :height="imageHeight"
      lang-type="zh"
      @close="imageCropperShow = false"
      @crop-upload-success="cropSuccess"
    />

    <el-tooltip :content="image?'点击修改图片':'只有图片显示失败，才会看到我哦~'">
      <el-image :src="image" @click.prevent="imageCropperShow=true" class="m-cursor-pointer" :style="{'border-radius': borderRadius?'50%':'','width':width,'height': height }" >
        <div slot="error">
          <el-tooltip content="只有图片显示失败，才会看到我哦~">
            <el-image :src="errorUrl" @click="imageCropperShow=true" />
          </el-tooltip>
        </div>
      </el-image>
    </el-tooltip>
  </div>
</template>
<script>
import ImageCropper from '@/components/ImageCropper'
export default {
  name: 'CustomUploadAvatar',
  components: {
    ImageCropper
  },
  data() {
    return {
      // 图片裁剪显示
      imageCropperShow: false,
      // 默认错误图片地址
      errorUrl:require('@/assets/image/huaji.jpg'),
    }
  },
  props: {
    image: {
      type: String,
      default: ''
    },
    imageWidth: {
      type: Number,
      default: 200
    },
    imageHeight: {
      type: Number,
      default: 200
    },

    width:{
      type:String,
      default:'150px'
    },
    height:{
      type:String,
      default:'150px'
    },
    borderRadius:{
      type:Boolean,
      default:false
    },
    uploadAvatarUrl:{
      type:String,
      default:'/github/upload/user/avatar'
    }
  },

  methods: {
    // 上传图片地址
    cropSuccess(url) {
      this.$emit('updateImage', url)
      // 默认3000ms之后自动关闭
      setTimeout(()=>{
        this.imageCropperShow = false
      },3000)
    },
  },

}
</script>
