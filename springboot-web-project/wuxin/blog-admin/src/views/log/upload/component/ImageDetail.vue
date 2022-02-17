<template>
  <div style="display: flex;flex-direction: column;align-items: center;">
    <el-form :model="image" size="mini" label-position="top">
      <el-form-item label="名称">
        <el-link :href="image.url" :underline="false" target="_blank">{{ image.name }}</el-link>
      </el-form-item>
      <el-form-item label="访问地址">
        <el-input v-model="image.url" :disabled="true" style="width:400px;max-width:100%;" />
        <el-button v-clipboard:copy="image.url" v-clipboard:success="clipboardSuccess" size="mini" type="primary"
                   icon="el-icon-document">
          复制
        </el-button>
        <el-button type="danger" icon="el-icon-delete" size="mini" @click.prevent="deletePic()">删除</el-button>
      </el-form-item>
      <el-form-item label="上传日期">
        <el-input v-model="image.createTime" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="上传地址" v-show="image.realUrl" v-if="hasRoot&&image.realUrl">
        <el-input v-model="image.realUrl" :disabled="true"></el-input>
        <el-button v-clipboard:copy="image.realUrl" v-clipboard:success="clipboardSuccess" size="mini" type="primary"
                   icon="el-icon-document">
          复制
        </el-button>
      </el-form-item>
      <el-form-item label="message" v-show="image.message" v-if="hasRoot&&image.message">
        <el-input v-model="image.message" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="sha" v-show="image.sha" v-if="hasRoot&&image.sha">
        <el-input v-model="image.sha" :disabled="true"></el-input>
        <el-button v-clipboard:copy="image.sha" v-clipboard:success="clipboardSuccess" size="mini" type="primary"
                   icon="el-icon-document">
          复制
        </el-button>
      </el-form-item>
      <el-form-item label="git" v-show="image.gitUrl" v-if="hasRoot&&image.gitUrl">
        <el-input v-model="image.gitUrl" :disabled="true"></el-input>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import clip from '@/utils/clipboard'
import clipboard from '@/directive/clipboard/index.js'

export default {
  name: 'ImageDetail',
  directives: {
    clipboard
  },
  data() {
    return {
      activeName: 'directly',
    }
  },
  props: {
    image: {
      type: Object,
      default: () => {
      }
    },
    hasRoot:{
      type:Boolean,
      default:false
    }
  },
  methods: {
    handleCopy(text, event) {
      clip(text, event)
    },
    clipboardSuccess() {
      this.$message({
        message: '复制成功',
        type: 'success',
        duration: 1500
      })
    },
    deletePic() {
      this.$emit("deletePic", this.image.id)
    }
  }
}
</script>
<style scoped>

.el-button {
  margin-top: 10px;
}

</style>

