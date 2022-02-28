<template>
  <div class="app-container">
    <el-row v-if="!isSetting" class="m-margin-tb-small">
      <el-col :span="8">
        <el-alert
          type="warning"
          title="获取不到仓库信息，点击前往配置"
          :closable="false"
          class="m-cursor-pointer"
          @click.native="toSetting"
        />
      </el-col>
    </el-row>
    <div v-if="isSetting">
      <el-row class="m-margin-tb-small" :gutter="20">
        <!--        选择一个仓库-->
        <el-col :span="6" :xs="24">
          <el-select v-model="repoName" class="m-width-100-pre" placeholder="请选择一个仓库" @change="resetTemp">
            <el-option v-for="(item,index) in repos" :key="index" :value="item.name" :label="item.name" />
          </el-select>
        </el-col>
        <!--        选择仓库文件夹-->
        <el-col :span="12" :xs="24">
          <el-cascader
            :key="resourceShow"
            v-model="path"
            clearable
            class="m-width-100-pre"
            :options="repoInfo"
            :props="props"
            :disabled="!repoName"
            placeholder="请选择仓库路径"
          />
        </el-col>
        <el-col :span="4" :xs="24">
          <el-button icon="el-icon-upload" type="primary" @click="drawer = true">上传文件</el-button>
        </el-col>
      </el-row>
      <el-row>
        <el-col>
          <el-dialog
            title="文件上传"
            size="40%"
            :visible.sync="drawer"
            direction="rtl"
            :before-close="handleClose"
          >
            <!--            文件类型-->
            <el-row>
              <el-col :span="22">
                <el-radio v-model="nameType" label="UUID">使用UUID文件名</el-radio>
                <el-radio v-model="nameType" label="sourceName">使用原文件名</el-radio>
                <el-button type="primary" icon="el-icon-upload" size="mini" @click="submitUpload">确认上传</el-button>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="22">
                <p>当前仓库:<el-tag type="info" class="m-margin-left-small">{{ repoName }}</el-tag> 当前路径:<el-tag type="info" class="m-margin-left-small">{{ selectPath }}</el-tag></p>
                <el-switch v-model="isCustomPath" active-text="自定义目录" />
                <el-input v-model="customPath" placeholder="例子: FolderName1 / FolderName2" :disabled="!isCustomPath" class="m-margin-tb-small" />
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="22">
                <el-upload
                  ref="upload"
                  class="m-width-100-pre"
                  :auto-upload="false"
                  :file-list="fileList"
                  list-type="picture"
                  action=""
                  :http-request="upload"
                  drag
                  multiple
                >
                  <i class="el-icon-upload" />
                  <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
                  <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
                </el-upload>
              </el-col>
            </el-row>
          </el-dialog>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import { repo } from '../repo_mixins'
import { uuid } from '@/utils/uuid'
import { isImgExt } from '@/utils/validate'
import { handleClipboard } from '@/utils/clipboard'
import { getReposContents, getUserRepos, delFile, upload } from '@/api/github'

export default {
  name: 'RepoSetting',
  mixins: [repo],
  data() {
    return {
      repos: [], // 仓库列表
      repoName: '', // 仓库名
      path: [''], // 路径
      selectPath: '',
      repoInfo: [{ value: '', label: '根目录' }], // 选中的仓库信息
      resourceShow: 0,
      props: {
        lazy: true,
        lazyLoad: async(node, resolve) => {
          const { path } = node
          let realPath = ''
          try {
            realPath = path.join('/')
            this.selectPath = path.join('/')
          } catch (e) {

          } finally {
            const list = []
            console.log('path', realPath)
            await this.getReposContents(list, realPath)
            resolve(list)
          }
        }
      },
      drawer: false,
      isCustomPath: false, // 自定义目录
      customPath: '', // 自定义目录
      fileName: 'uuid', // 自定义目录
      nameType: 'UUID',
      fileList: []
    }
  },
  computed: {
    // 获取真实路径
    realPath() {
      if (this.isCustomPath) {
        return `/${this.user.username}/${this.repoName}/${this.customPath}`
      }
      return `/${this.user.username}/${this.repoName}${this.path.join('/')}/`
    }
  },
  mounted() {
    if (this.user && this.user.username && this.user.token && this.user.avatar) {
      this.isSetting = true
      console.log(this.user)
      this.getUserRepos()
    } else {
      this.error()
    }
  },
  methods: {
    error() {
      this.$confirm('获取不到仓库配置信息，是否前往配置?', '提示', {
        confirmButtonText: '确定',
        showCancelButton: false,
        type: 'warning'
      }).then(() => {
        this.toSetting()
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消'
        })
      })
    },
    toSetting() {
      this.$router.push('/repo/token')
    },
    // 获取仓库列表
    async getUserRepos() {
      await getUserRepos(this.user.username).then(res => {
        this.repos = res
      })
    },
    // 仓库重新选择了列表需要重置
    resetTemp() {
      this.resourceShow++
      this.path = ['']
      this.fileList = []
    },
    // 获取指定仓库路径
    async getReposContents(list, path) {
      // console.log(this.path)
      await getReposContents(this.user.username, this.repoName, path).then(res => {
        res.forEach(item => {
          // 只获取目录路径
          if (item.type === 'dir') {
            list.push({ value: item.name, label: item.name, leaf: false })
          }
        })
      })
    },
    handleClose(done) {
      this.$confirm('取消上传？')
        .then(_ => {
          done()
        })
        .catch(_ => {
        })
    },

    search() {
      this.fileList = []
      const path = this.activePath.join('/')
      getReposContents(this.user.username, this.repoName, path).then(res => {
        res.forEach(item => {
          if (item.type === 'file' && isImgExt(item.name)) {
            this.fileList.push(item)
          }
        })
      })
    },
    noDisplay(id) {
      localStorage.setItem(`hintShow${id}`, '1')
    },
    imgUrl(file) {
      return this.isCDN ? `https://cdn.jsdelivr.net/gh/${this.userInfo.login}/${this.activeRepos}/${file.path}` : file.download_url
    },

    copy(type, file) {
      // type 1 cdn link  2 Markdown
      const imgUrl = `https://cdn.jsdelivr.net/gh/${this.user.username}/${this.repoName}/${file.path}`
      console.log('copy fileName',imgUrl)
      // const copyCont = imgUrl
      // if (type == 2) {
      //   copyCont = `![${file.name}](${imgUrl})`
      // }
      // copy(copyCont)
      this.msgSuccess('复制成功')
    },

    delFile(file) {
      this.$confirm('此操作将永久删除该文件, 是否删除?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const data = {
          message: `Delete file ${file.name}`,
          sha: file.sha
        }
        delFile(this.user.username, this.repoName, file.path, data).then(() => {
          this.msgSuccess('删除成功')
          this.search()
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },

    submitUpload() {
      // https://github.com/ElemeFE/element/issues/12080
      this.uploadList = this.$refs.uploadRef.uploadFiles
      if (this.uploadList.length) {
        // 触发 el-upload 中 http-request 绑定的函数
        this.$refs.upload.submit()
      } else {
        this.$message.error('请先选择文件')
      }
    },

    upload(data) {
      const reader = new FileReader()
      reader.readAsDataURL(data.file)
      reader.onload = () => {
        const base64 = reader.result.split(',')[1]
        let fileName = data.file.name
        if (this.nameType === '2') {
          fileName = uuid() + fileName.substr(fileName.lastIndexOf('.'))
        }
        // 批量上传需要间隔时间，否则可能commit版本号冲突，返回409错误码，Status: 409 Conflict
        taskQueue(() => this.push2Github(data, fileName, base64), 1000)
      }
    },

    push2Github(data, fileName, base64) {
      const requestData = {
        message: 'Add files via PictureHosting',
        content: base64
      }
      let path = this.activePath.join('/')
      if (this.isCustomPath) {
        if (this.customPath === '/') {
          path = ''
        } else {
          path = this.customPath
          if (path.charAt(0) !== '/') {
            path = '/' + path
          }
          if (path.charAt(path.length - 1) === '/') {
            path = path.substring(0, path.length - 1)
          }
        }
      }
      upload(this.userInfo.login, this.activeRepos, path, fileName, requestData).then(() => {
        this.msgSuccess('上传成功')
        data.onSuccess()
      })
    },

    removeFile(data) {
      delFile().then(res => {
        console.log(res)
      })
    }

  }
}
</script>

<style lang="scss" scoped>
.el-row {
  padding-left: 20px !important;
}

.el-col {
  margin: 10px 0;
}

.m-width-100-pre {
  width: 100% !important;
}

</style>
