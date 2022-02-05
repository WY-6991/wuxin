<template>
  <div>
    <ul>
      <li>
        <el-upload
          drag
          :action="action"
        >
          <i class="el-icon-upload"></i>
          <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
          <div class="el-upload__tip" slot="tip">只能上传jpg/png文件，且不超过500kb</div>
        </el-upload>
      </li>
      <li v-for="(image,index) in imageList" :key="index" v-if="imageList&&imageList.length!==0">
        <el-skeleton style="width: 240px" :loading="loading" animated>
          <div slot="template">
            <el-skeleton-item variant="image" style="width: 100%;;" />
          </div>
          <div>
            <el-card>
              <el-image :src="image.url" />
            </el-card>
          </div>
        </el-skeleton>
      </li>
      <li v-else>
        <p>暂无数据！</p>
      </li>

    </ul>
  </div>
</template>
<script>
export default {
  name: 'UploadBackgroundImage',
  props: {
    imageList: {
      default: []
    },
    action: {
      type: String,
      default: 'http://localhost:8888/github/upload/blog/img'
    },

  },
  data() {
    return {
      loading: true,
      currentDate: '2021-06-01'
    }
  },
}
</script>
<style scoped>
ul li {
  list-style: none;
  margin-top: 20px;
  width: 100%;
}

.el-upload {
  width: 100% !important;
}
</style>
