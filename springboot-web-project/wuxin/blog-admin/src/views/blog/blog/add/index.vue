<template>
  <div class="app-container">
    <!-- 发布 -->
    <el-button
      class="m-fixed-button"
      type="primary"
      icon="el-icon-plus"
      @click="handleCreate"
    >
      发布
    </el-button>

    <!-- 内容 -->
    <el-form ref="blogForm" :model="blog" :rules="rules" class="form-container">
      <el-col :span="24">
        <el-form-item prop="title">
          <el-input v-model="blog.title" :maxlength="100" placeholder="请输入标题" clearable show-word-limit />
        </el-form-item>

        <el-form-item prop="desc">
          <div id="vditor-description" />
        </el-form-item>

        <el-form-item prop="content">
          <div id="vditor-content" />
        </el-form-item>
      </el-col>
    </el-form>

    <!--    选项内容-->
    <el-dialog title="添加博客" :visible.sync="dialogFormVisible" width="40%">
      <el-form
        ref="dataForm"
        :rules="rules"
        :model="blog"
        label-position="left"
        label-width="70px"
      >
        <el-form-item label="类型">
          <el-select
            v-model="blog.cid"
            style="min-width: 300px;"
            class="filter-item"
            placeholder="请选择，输入关键字搜索"
          >
            <el-option
              v-for="item in categoryList"
              :key="item.cid"
              :label="item.name"
              :value="item.cid"
            />
          </el-select>
          <el-button type="primary" size="mini" class="m-margin-left-small" icon="el-icon-plus" @click="addCategory" />
        </el-form-item>

        <el-form-item label="标签" prop="tagIds">
          <el-drag-select
            v-model="blog.tagIds"
            filterable
            style=" width:300px;min-width: 300px;"
            multiple
            placeholder="请选择，输入关键字搜索"
          >
            <el-option
              v-for="item in tagList"
              :key="item.tagId"
              :label="item.name"
              :value="item.tagId"
            />
          </el-drag-select>
          <el-button type="primary" size="mini" class="m-margin-left-small" icon="el-icon-plus" @click="addTag" />
        </el-form-item>
        <el-form-item label="权限">
          <el-switch v-model="blog.publish" active-text="发布" />
          <el-switch v-model="blog.commentEnabled" active-text="开启评论" class="m-margin-left-small" />
          <el-switch v-model="blog.top" active-text="置顶" :active-value="1" :inactive-value="0" />
          <el-switch v-model="blog.appreciation" active-text="开启赞赏" class="m-margin-left-small" />
        </el-form-item>
        <el-form-item label="保密">
          <el-switch v-model="blog.secrecy" />
        </el-form-item>
        <el-form-item v-if="blog.secrecy" label="密码" prop="password">
          <el-input v-model="blog.password" style="width: 200px;" placeholder="请输入密码" show-word-limit />
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="createData">确认</el-button>
      </div>
    </el-dialog>
    <el-dialog title="添加分类" :visible.sync="addCategoryDialogFormVisible" width="30%">
      <AddCategory @closeAddLabel="closeAddLabel" @getList="getLabelList" />
    </el-dialog>

    <el-dialog title="添加标签" :visible.sync="addTagDialogFormVisible" width="30%">
      <AddTag @closeAddLabel="closeAddLabel" @getList="getLabelList" />
    </el-dialog>

  </div>
</template>

<script>
import { createBlog } from '@/api/blog'
import { minix } from '../minix'

export default {
  name: 'BlogAdd',
  mixins: [minix],
  data() {
    return {
      blog: {
        title: '',
        content: '',
        description: '',
        top: 0, // 是否置顶 不置顶
        commentEnabled: true, // 开启评论
        publish: true, // 默认发布
        appreciation: false, // 开启赞赏
        secrecy: false, // 是否保密 默认不开启公开的
        password: '', // 密码
        cid: '',
        tagIds: [],
        views: 0
      },
      dialogStatus: ''
    }
  },

  methods: {
    // 验证数据
    handleCreate() {
      // 第一次验证
      this.$refs['blogForm'].validate(valid => {
        if (valid) {
          this.dialogStatus = 'create'
          this.dialogFormVisible = true
          this.$nextTick(() => {
            this.$refs['dataForm'].clearValidate()
          })
        }
      })
    },
    // 添加数据
    createData: function() {
      this.$refs['dataForm'].validate(valid => {
        if (valid) {
          this.blog.description = this.descriptionVditor.getHTML()
          this.blog.content = this.contentVditor.getHTML()
          this.dialogFormVisible = false

          createBlog(this.blog).then(res => {
            this.initVditor()
            this.$notify.success({
              title: 'success',
              message: '添加成功！',
              duration: 2000
            })
          })
        }
      })
    }

  }

}

</script>

