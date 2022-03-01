<template>
  <div>
    <el-row>
      <el-avatar :src="user.avatar"></el-avatar>
      <el-tooltip content="点击前往仓库">
        <el-link target="_blank" :href="user.url" :underline="false">{{ user.username }}</el-link>
      </el-tooltip>
    </el-row>

    <el-row>
      <el-col :span="22">
        <el-select class="w-100-pre" v-model="repoName" placeholder="请选择要上传的仓库..." @change="resetRepos">
          <el-option v-for="(item,index) in repoList" :key="`${repoName}${index}`" :value="item.name"
                     :label="item.name"/>
        </el-select>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="22">
        <el-cascader class="w-100-pre" :key="showResource" :disabled="!repoName" :props="props" v-model="activePath"
                     :options="root"
                     placeholder="请选择要上传的文件夹路径..."/>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="22">
        <span v-if="repoName">当前仓库 :  <el-tag type="info" size="small" class="m-margin-lr">{{
            repoName
          }}</el-tag></span>
        <span v-if="path">当前路径: <el-tag type="info" size="small" class="m-margin-lr">{{
            path === '/' ? '根目录' : path
          }}</el-tag></span>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="22">
        <el-switch v-model="isCustomUrl" active-text="CDN"  :disabled="!repoName"></el-switch>
        <el-switch active-text="自定义上传" v-model="isCustomPath"></el-switch>
        <el-input placeholder="自定义上传路径" :disabled="!isCustomPath" v-model="customPath"></el-input>
      </el-col>
    </el-row>
    <el-row>
      <el-button @click="open=true" :disabled="!path" plain>开始上传</el-button>
    </el-row>
    <sui-modal v-model="open" size="small" style="min-height: 500px">
      <sui-modal-content scrolling>
        <div class="ui warning message">
          <i class="ui icon close"></i>
          <p>当前文件会上传到仓库
            <i class="ui icon paper plane"></i>{{ repoName }}
            <i class="ui icon folder open"></i>{{ path === '/' ? '根目录' : path }}
          </p>
        </div>
        <el-upload
            class="upload-demo"
            drag
            action="https://jsonplaceholder.typicode.com/posts/"
            multiple>
          <i class="el-icon-upload"></i>
          <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
          <div class="el-upload__tip" slot="tip">只能上传jpg/png文件，且不超过500kb</div>
        </el-upload>
      </sui-modal-content>
      <sui-modal-actions>
        <sui-button @click.native="open=false" color="primary">
          关闭
        </sui-button>
      </sui-modal-actions>
    </sui-modal>

  </div>
</template>
<script>
import {getStore} from "@/utils/session";
import {getReposContents, getUserRepos, getUserInfo, delFile, upload} from "../../../api/github";
import {TOKEN_GITEE_KEY, TOKEN_GITHUB_KEY, TOKEN_KEY_TYPE} from '@/store/mutations-type.js'

export default {
  name: 'RepoUpload',
  data() {
    return {
      user: {},
      repoName: '',
      repoList: [],
      activePath: [''],
      showResource: 0,
      root: [{label: '根目录', value: ''}],
      activeName: [],
      props: {
        checkStrictly: true,
        lazy: true,
        lazyLoad: async (node, resolve) => {
          const list = []
          const path = node.path.join('/')
          await this.getReposContents(list, path)
          resolve(list)
        }
      },
      isCustomPath: false,
      isCustomUrl: false,
      customPath: '',
      open: false
    }
  },
  created() {
    this.init()
  },
  computed: {
    // 计算到真实路径
    path() {
      if (this.isCustomPath) {
        return `/${this.user.username}/${this.repoName}/${this.customPath}`
      }
      return `/${this.user.username}/${this.repoName}${this.activePath.join('/')}/`
    }
  },
  methods: {
    init() {
      const type = getStore(TOKEN_KEY_TYPE)
      let user = {}
      if (type === 'github') {
        user = getStore(TOKEN_GITHUB_KEY)
      } else {
        user = getStore(TOKEN_GITEE_KEY)
      }
      if (user && user.username && user.token && user.avatar) {
        this.user = user
        this.$nextTick(() => {
          this.getUserRepos()
        })
      } else {
        this.$message.error('加载失败！获取不到用户配置信息，请初始化配置！')
        setTimeout(() => {
          this.$emit('error')
        }, 3000)
      }
    },

    resetRepos() {
      this.root = [{label: '根目录', value: ''}]
      this.activePath = ['']
      this.showResource++
    },

    getUserRepos() {
      getUserRepos(this.user.username).then(res => {
        console.log(res);
        this.repoList = res
      })
    },

    imgUrl(file) {
      return this.isCustomUrl ? `https://cdn.jsdelivr.net/gh/${this.user.username}/${this.repoName}/${file.path}` : file.download_url
    },

    async getReposContents(list, path) {
      await getReposContents(this.user.username, this.repoName, path).then(res => {
        res.forEach(file => {
          // 加载目录
          if (file.type === 'dir') {
            console.log(file)
            list.push({label: file.name, value: file.name, leaf: false})
          }
        })
      })
      console.log(list)
    },

    // 自定义文件上传文件加


  },

}
</script>
<style scoped>

.el-row {
  margin: 20px !important;
}

.w-100-pre {
  width: 100% !important;
}

</style>