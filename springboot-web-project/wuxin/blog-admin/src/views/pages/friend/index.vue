<template>
  <div class="app-container">
    <MySearchHeader
      @handleCreate="handleCreate"
      @handleSearch="handleFilter"
    >

      <el-switch
        v-model="message.commentEnabled"
        class="m-margin-left-small"
        active-text="开启评论"
        inactive-text="关闭评论"
        @change="updateMessage"
      />

    </MySearchHeader>

    <!-- 表格数据 -->
    <el-table
      :key="tableKey"
      v-loading="listLoading"
      :data="friendList"
      fit
      max-height="350"
      highlight-current-row
      style="width: 100%;"
      size="small"
      @cell-click="moustEnter"
    >
      <el-table-column
        label="序号"
        prop="id"
        align="center"
        width="100"
      >
        <template slot-scope="{ row,$index }">
          <span>{{ $index + 1 }}</span>
        </template>
      </el-table-column>

      <el-table-column label="用户名" width="110" align="center">
        <template slot-scope="{ row }">
          <span>{{ row.username }}</span>
        </template>
      </el-table-column>
      <el-table-column label="头像" width="110" align="center">
        <template slot-scope="{ row }">
          <MyImage :image="row.avatar" />
        </template>
      </el-table-column>

      <el-table-column label="链接地址" width="200" align="center">
        <template slot-scope="{ row }">
          <el-tooltip :content="`链接地址:${row.url},点击可访问`">
            <a class="link-type m-message" :href.sync="row.url" target="_blank">{{ row.url }}</a>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column label="简介" align="center">
        <template slot-scope="{ row }">
          <el-tooltip :content="row.introduction">
            <span class=" m-message" @click="handleUpdate(row)">{{ row.introduction }}</span>
          </el-tooltip>
        </template>
      </el-table-column>

      <el-table-column label="是否显示" width="100" align="center">
        <template slot-scope="{ row }">
          <el-switch v-model="row.status" @change="updateStatus(row)" />
        </template>
      </el-table-column>

      <el-table-column label="创建日期" width="160" align="center">
        <template slot-scope="{ row }">
          <span>{{ row.createTime }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="200">
        <template slot-scope="{ row, $index }">
          <el-button type="primary" size="mini" icon="el-icon-edit" @click="handleUpdate(row)">编辑</el-button>
          <el-button type="danger" size="mini" icon="el-icon-delete" @click="handleDelete(row.friendId, $index)">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <MyPagination
      v-show="total > 0"
      :total="total"
      :page.sync="query.current"
      :limit.sync="query.limit"
      @pagination="getList"
    />

    <!--   上传头像-->
    <div class="components-container">
      <image-cropper
        v-show="imageCropperShow"
        :key="imageCropperKey"
        :with-credentials="true"
        :headers="headers"
        :width="300"
        :height="300"
        :field="field"
        :url="uploadAvatarUrl"
        lang-type="zh"
        @close="close"
        @crop-upload-success="cropSuccess"
      />
    </div>

    <el-button size="small" type="primary" icon="el-icon-edit" class="m-margin-tb-small" @click="updateMessage">修改
    </el-button>
    <div id="vditor-content-friend-message" />

    <el-dialog title="添加标签" :visible.sync="dialogFormVisible">
      <el-row>
        <el-col :span="8" style="margin-left: 44px;" :xs="24">
          <el-row>
            <pan-thumb :image="ruleForm.avatar" />
          </el-row>
          <el-row style="margin-top: 30px;">
            <el-button type="primary" icon="el-icon-upload" @click="imageCropperShow=true">点击可自定义头像</el-button>
          </el-row>
        </el-col>
        <el-col :span="14" :xs="24">
          <el-form ref="dataForm" :model="ruleForm" :rules="rules" label-width="50px" class="demo-ruleForm">
            <el-form-item label="昵称" prop="username">
              <el-input
                v-model="ruleForm.username"
                prefix-icon="el-icon-user"
                clearable
                maxlength="20"
                show-word-limit
                class="m-width-400"
              />
            </el-form-item>
            <el-form-item label="头像">
              <el-select
                v-model="ruleForm.avatar"
                placeholder="请选择图片"
                class="m-width-400 m-message"
              >
                <el-option
                  v-for="(img,index) in imgList"
                  :key="index"
                  class="m-message"
                  :label="img"
                  :value="img"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="网址" prop="url">
              <el-input
                v-model="ruleForm.url"
                prefix-icon="el-icon-s-promotion"
                clearable
                class="m-width-400"
              />
            </el-form-item>
            <el-form-item label="简介" prop="introduction">
              <el-input
                v-model="ruleForm.introduction"
                class="m-width-400"
                maxlength="20"
                prefix-icon="el-icon-edit"
                show-word-limit
                clearable
              />
            </el-form-item>
            <el-form-item style="margin-left: 44px!important;">
              <el-button type="primary" @click="status === 'create' ? createData() : updateData()">{{ textMap }}
              </el-button>
              <el-button @click="resetForm">重置</el-button>
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>
    </el-dialog>
  </div>

</template>

<script>

import ImageCropper from '@/components/ImageCropper'
import PanThumb from '@/components/PanThumb'
import { validURL } from '@/utils/validate'
import MyImage from '@/components/MyComponents/MyImage'
import { query } from '@/mixin/query'
import { imgList } from '@/utils/color'
import { createVditor } from '@/plugins/CreateVditor'
import {
  createFriend,
  updateFriend,
  delFriend,
  getFriendMessage,
  updateFriendMessage,
  getFriendList
} from '@/api/friends'
export default {
  name: 'Friend',
  components: {MyImage, ImageCropper, PanThumb},
  mixins: [query],
  data() {
    const validateUrl = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入网址！'))
      }
      if (!validURL(value)) {
        callback(new Error('网址格式不正确！'))
      }
      callback()
    }
    return {
      // 表格key
      tableKey: 0,
      contentVditor: null,
      friendList: [],
      total: 0, // 数据总数
      listLoading: false,
      dialogFormVisible: false,
      imgList: imgList,
      uploadAvatarUrl: '/github/upload/user/avatar',
      headers: {'Content-Type': 'multipart/form-data'},
      field: 'file',
      imageCropperShow: false,
      imageCropperKey: 0,
      // 临时数据
      temp: {username: '', avatar: '', url: '', introduction: '', status: true},

      ruleForm: {
        username: '',
        avatar: 'https://github.com/WY-6991/wuxin/img/202202/20220223234230.png',
        url: '',
        introduction: '',
        status: true
      },
      rules: {
        url: [
          { validator: validateUrl, trigger: 'blur'}
        ],
        username: [{
          required: true, message: '请输入用户名', trigger: 'blur'
        }],
        introduction: [{
          required: true, message: '请输入介绍', trigger: 'blur'
        }]

      },
      status: 'create',
      textMap: '添加',
      message: {
        id: 0,
        content: '',
        commentEnabled: true
      }

    }
  },
  mounted() {
    this.getList()
    this.initVditor()
  },
  methods: {
    getData(query) {
      this.listLoading = true
      getFriendList(query).then(res => {
        if (res.code === 200) {
          const { records, total} = res.result
          this.friendList = records
          this.total = total
        }
      }).catch(() => {
        setTimeout(() => {
          this.listLoading = false
        }, 10000)
      })
    },
    restTemp() {
      this.ruleForm = {
        username: '',
        avatar: 'https://github.com/WY-6991/wuxin/img/202202/20220223234230.png',
        url: '',
        introduction: '',
        status: true
      }
    },

    updateStatus(friend) {
      updateFriend(friend).then(res => {
        if (res.code === 200) {
          if (friend.status) {
            this.$message.success('显示成功')
          } else {
            this.$message.success('隐藏成功')
          }
        }
      })
    },

    toBottom() {
      // 滚动内容的坐标位置0,50000：
      window.scrollTo(0, 20000)
    },

    /* 添加 */
    handleCreate() {
      this.toBottom()
      this.status = 'create'
      this.textMap = '添加'
      this.dialogFormVisible = true
      this.restTemp()
    },

    // 修改操作
    handleUpdate(row) {
      this.dialogFormVisible = true
      this.toBottom()
      this.status = 'update'
      this.ruleForm = row
      this.textMap = '修改'
    },
    resize() {
      console.log('resize')
    },

    // 删除操作
    handleDelete(friendId, index) {
      this.$confirm('此操作将永久删除该用户, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        delFriend(friendId).then(res => {
          if (res.code === 200) {
            this.friendList.splice(index, 1)
            this.$message({
              type: 'success',
              message: '删除成功!'
            })
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },

    // 获取blogList
    getList() {
      this.getData(this.query)
    },
    createData(formName) {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          createFriend(this.ruleForm).then(res => {
            if (res.code !== 200) return false
            this.$notify.success('添加成功！')
            this.$refs['dataForm'].resetFields()
            this.getList()
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },

    updateComment() {
      this.$message.warning('该功能还没上线01！')
    },

    updateData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          updateFriend(this.ruleForm).then(res => {
            if (res.code !== 200) return false
            this.$notify.success('修改成功！')
            this.$refs['dataForm'].resetFields()
            this.getList()
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    resetForm() {
      this.$refs['dataForm'].resetFields()
    },
    // 上传图片地址
    cropSuccess(resData) {
      console.log('res====>' + resData)
      this.ruleForm.avatar = resData
      // this.$store.dispatch('user/updateUser', this.user).then(res => {
      //   // this.user.avatar = resData // 获取用户头像地址
      //   // this.avatar = resData // 获取用户头像地址
      //
      //   this.$store.state.avatar = resData
      // })  // 修改用户头像
    },
    close() {
      this.imageCropperShow = false
    },

    moustEnter(row, column, cell, event) {
      console.log(row)
    },
    getMessage() {
      getFriendMessage().then(res => {
        if (res.code === 200) {
          console.log(res)
          this.message = res.result
          this.message.content = this.contentVditor.html2md(this.message.content)
          this.contentVditor.setValue(this.message.content)
        }
      })
    },

    // 描述
    initVditor() {
      this.contentVditor = createVditor('vditor-content-friend-message', 400, false)
      this.$nextTick(()=>{
        this.getMessage()
      })
    },

    updateMessage() {
      this.message.content = this.contentVditor.getHTML()
      updateFriendMessage(this.message).then(res => {
        if (res.code === 200) {
          this.$message.success('修改成功！')
        }
      })
    },

  }
}
</script>

<style>
.m-width-400 {
  max-width: 400px !important;
  width: 90% !important;
}

</style>
