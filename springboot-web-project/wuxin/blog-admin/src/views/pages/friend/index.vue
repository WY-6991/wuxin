<template>
  <div class="app-container">
    <MySearchHeader
      @handleCreate="handleCreate"
      @handleSearch="handleFilter"
    >

      <el-switch
        class="m-margin-left-small"
        v-model="commentEnabled"
        @change.prevent="updateComment"
        disabled
        active-text="开启评论"
        inactive-text="关闭评论"
      >
      </el-switch>

    </MySearchHeader>

    <!-- 表格数据 -->
    <el-table
      :key="tableKey"
      v-loading="listLoading"
      :data="friendList"
      fix
      max-height="350"
      highlight-current-row
      @cell-click="moustEnter"
      style="width: 100%;"
      size="mini"

    >
      <el-table-column
        label="序号"
        prop="id"
        align="center"
        width="100"
        fixed

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
      <el-table-column label="简介" width="300" align="center">
        <template slot-scope="{ row }">
          <el-tooltip :content="row.introduction">
            <span class=" m-message" @click="handleUpdate(row)">{{ row.introduction }}</span>
          </el-tooltip>
        </template>
      </el-table-column>

      <el-table-column label="创建日期" width="160" align="center">
        <template slot-scope="{ row }">
          <span>{{ row.createTime }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="250" fixed="right" class-name="small-padding fixed-width">
        <template slot-scope="{ row, $index }">
          <el-button
            size="mini"
            :type="row.status?'info':'success'"
            :icon="row.status? 'el-icon-bell':'el-icon-close-notification'"
            @click.native.prevent="updateStatus(row)"
          >{{ row.status ? '显示' : '隐藏' }}
          </el-button>
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

    <el-row>
      <el-col :span="6"></el-col>
      <el-col :span="6" style="margin-top: 59px; margin-left: 100px; margin-right: -20px;">
        <el-row>
          <pan-thumb :image="ruleForm.avatar" />
        </el-row>
        <el-row style="margin-top: 30px;">
          <el-button type="primary" icon="el-icon-upload" @click="imageCropperShow=true">点击可自定义头像</el-button>
        </el-row>
      </el-col>
      <el-col :span="12">
        <el-form :model="ruleForm" :rules="rules" ref="dataForm" label-width="50px" class="demo-ruleForm">
          <el-form-item label="昵称" prop="username">
            <el-input
              prefix-icon="el-icon-user"
              v-model="ruleForm.username"
              clearable
              maxlength="20"
              show-word-limit
              style="width: 400px" />
          </el-form-item>
          <el-form-item label="头像">
            <el-select
              v-model="ruleForm.avatar"
              placeholder="请选择图片" style="width: 400px!important;">
              <el-option v-for="(img,index) in imgList"
                         :label="img" :key="index"
                         :value="img"
              />
            </el-select>
          </el-form-item>

          <el-form-item label="网址" prop="url">
            <el-input
              prefix-icon="el-icon-s-promotion"
              v-model="ruleForm.url"
              clearable
              style="width: 400px" />
          </el-form-item>
          <el-form-item label="简介" prop="introduction">
            <el-input
              v-model="ruleForm.introduction"
              style="width: 400px"
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

  </div>

</template>

<script>

import ImageCropper from '@/components/ImageCropper'
import PanThumb from '@/components/PanThumb'
import {validURL} from "@/utils/validate";
import {createFriend, updateFriend, delFriend, getFriendList} from "@/api/friends";
import MyImage from "@/components/MyComponents/MyImage";
import {query} from "@/mixin/query";
import {imgList} from "@/utils/color";

export default {
  name: "Friend",
  components: { MyImage, ImageCropper, PanThumb},
  mixins:[query],
  data() {
    let validateUrl = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入网址！'));
      }
      if (!validURL(value)) {
        callback(new Error('网址格式不正确！'));
      }
      callback()
    };
    return {
      // 表格key
      tableKey: 0,
      friendList: [],
      total: 0, // 数据总数
      listLoading: false,
      commentEnabled: true,

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
        avatar: 'https://cdn.jsdelivr.net/gh/WY-6991/wuxin/img/admin.jpg',
        url: '',
        introduction: '',
        status: true
      },
      rules: {
        url: [
          {validator: validateUrl, trigger: 'blur'}
        ],
        username: [{
          required: true, message: '请输入用户名', trigger: 'blur'
        }],
        introduction: [{
          required: true, message: '请输入介绍', trigger: 'blur'
        }]

      },
      status: 'create',
      textMap: '添加'

    };
  },
  mounted() {

    this.getList()
  },
  methods: {
    getData(query){
      this.listLoading = true;
      getFriendList(query).then(res => {
        this.friendList = res.result.records
        this.total = res.result.total
        this.listLoading = false
      })
    },

    restTemp() {
      this.ruleForm = {
        username: '',
        avatar: 'https://cdn.jsdelivr.net/gh/WY-6991/wuxin/img/admin.jpg',
        url: '',
        introduction: '',
        status: true,
      }
    },

    updateStatus(friend) {
      friend.status = !friend.status
      if (!this.isRoot) {
        this.$message.error('操作失败，无权限执行该操作！')
        return;
      }
      updateFriend(friend).then(res => {
        if (res.code === 200) {
          if (friend.status) {
            this.$notify.success("显示成功")
          } else {
            this.$notify.success("隐藏成功")
          }

        }
      })
    },

    toBottom() {
      // 滚动内容的坐标位置0,50000：
      window.scrollTo(0, 20000);
    },


    /* 添加 */
    handleCreate() {
      this.toBottom()
      this.status = 'create'
      this.textMap = '添加'
      this.restTemp()
    },

    // 修改操作
    handleUpdate(row) {

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
      // this.delBlog(blogId).then(res => {
      //   this.blogList.splice(index, 1);
      // })
      this.$confirm('此操作将永久删除该用户, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        if (!this.isRoot) {
          this.$message.error('操作失败，无权限执行该操作！')
          return;
        }
        this.friendList.splice(index, 1)
        this.$message({
          type: 'success',
          message: '删除成功!'
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });

      // delFriend(friendId).then(res=>{
      //   this.friendList.splice(index,1)
      // })
    },

    // 获取blogList
    getList() {
     this.getData(this.query)
    },
    createData(formName) {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          if (!this.isRoot) {
            this.$message.error('操作失败，无权限执行该操作！')
            return;
          }
          createFriend(this.ruleForm).then(res => {
            if (res.code !== 200) return false;
            this.$notify.success("添加成功！")
            this.$refs['dataForm'].resetFields();
            this.getList()
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },


    updateComment(){
      this.$message.warning('该功能还没上线01！')
    },

    updateData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          if (!this.isRoot) {
            this.$message.error('操作失败，无权限执行该操作！')
            return;
          }
          updateFriend(this.ruleForm).then(res => {
            if (res.code !== 200) return false;
            this.$notify.success("修改成功！")
            this.$refs['dataForm'].resetFields();
            this.getList()
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    resetForm() {
      this.$refs['dataForm'].resetFields();
    },
    // 上传图片地址
    cropSuccess(resData) {
      console.log("res====>" + resData)
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
      console.log(row);
    }
  }
};
</script>

<style>
/*.el-form {*/
/*  margin-top: 55px !important;*/
/*}*/

/*.el-form > .el-form-item > button {*/
/*  padding-left: 20px !important;*/
/*}*/


</style>
