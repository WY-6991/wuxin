<template>
  <div class="app-container">
    <!-- 发布 -->
    <el-button
      class="m-fixed-button"
      type="primary"
      icon="el-icon-edit"
      size="small"
      @click="handleUpdate"
    >
      修改
    </el-button>
    <el-form
      ref="updateDataForm"
      :rules="rules"
      :model="blog"
      label-position="left"
      inline
    >
      <el-form-item label="权限">
        <el-switch v-model="blog.publish" active-text="发布"   class="m-margin-left-small" />
        <el-switch v-model="blog.commentEnabled" active-text="评论" class="m-margin-left-small" />
        <el-switch v-model="blog.top" active-text="置顶" :active-value="1" :inactive-value="0"   class="m-margin-left-small" />
        <el-switch v-model="blog.appreciation" active-text="赞赏" class="m-margin-left-small" />
        <el-switch v-model="blog.secrecy" active-text="密码"   class="m-margin-left-small" />
        <el-input v-if="blog.secrecy" v-model="blog.password" style="width: 200px;" placeholder="请输入密码" :maxlength="15"
                  show-word-limit />
      </el-form-item>

      <el-row>
        <el-form-item label="分类" prop="category" required>
          <el-select
            style="width: 150px;"
            v-model="blog.cid"
            placeholder="请选择..."
          >
            <el-option
              v-for="item in categoryList"
              :key="item.cid"
              :label="item.name"
              :value="item.cid"
            />
          </el-select>

          <el-button type="primary" size="small" class="m-margin-left-small" @click="addCategory" icon="el-icon-plus" />
        </el-form-item>

        <el-form-item label="标签" prop="tagIds" required>
          <el-drag-select
            filterable
            v-model="blog.tagIds"
            style=" width:300px;"
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
          <el-button type="primary" size="small" class="m-margin-left-small" icon="el-icon-plus" @click="addTag" />
        </el-form-item>

      </el-row>
      <el-col :span="24">
        <el-form-item prop="title" label="标题">
          <el-input
            v-model="blog.title"
            :maxlength="30"
            show-word-limit
            clearable
            style=" width:300px;min-width: 300px;"
          >
          </el-input>

        </el-form-item>

        <el-form-item prop="title" label="封面图片">
          <el-input
            show-word-limit
            clearable
            style=" width:300px"
          >
          </el-input>

        </el-form-item>

        <el-form-item prop="desc" width="100%">
          <div id="vditor-description"></div>
        </el-form-item>

        <el-form-item prop="content" width="100%">
          <div id="vditor-content"></div>
        </el-form-item>
      </el-col>
    </el-form>
    <el-dialog title="添加分类" :visible.sync="addCategoryDialogFormVisible" width="30%">
      <AddCategory @closeAddLabel="closeAddLabel" @getList="getLabelList" />
    </el-dialog>

    <el-dialog title="添加标签" :visible.sync="addTagDialogFormVisible" width="30%">
      <AddTag @closeAddLabel="closeAddLabel" @getList="getLabelList" />
    </el-dialog>


  </div>
</template>

<script>
import {blogDetail, updateBlog} from "@/api/blog";
import {minix} from "@/views/pages/blog/minix";

export default {
  name: "BlogEdit",
  mixins: [minix],
  data() {
    return {
      blog: {
        tagIds: []
      },
    };
  },

  methods: {
    handleUpdate() {
      // 第一次验证
      this.$refs["updateDataForm"].validate(valid => {
        if (valid) {
          if (!this.isRoot) {
            this.$message.error('操作失败，没有权限执行该操作！')
            return;
          }
          this.blog.description = this.descriptionVditor.getHTML()
          this.blog.content = this.contentVditor.getHTML()
          // 去重处理
          this.blog.tagIds = this.uniqueList(this.blog.tagIds)
          updateBlog(this.blog).then(res => {
            this.$notify.success('修改成功！')
          })
        }
      });

    },
    //获取tag
    getData() {
      blogDetail(this.$route.params.blogId).then(res => {
        if (res.code === 200) {
          const {description, content, tags, tagIds} = res.result
          this.blog = res.result

          // 遍历获取文章标签
          for (let i = 0; i < this.blog.tags.length; i++) {
            this.blog.tagIds.push(tags[i].tagId)
          }
          // 去重处理
          this.blog.tagIds = this.uniqueList(this.blog.tagIds)

          // 将html格式语言转换markdown
          this.blog.description = this.descriptionVditor.html2md(description)
          this.blog.content = this.contentVditor.html2md(content)

          // markdown格式文件渲染到编辑器中
          this.descriptionVditor.setValue(this.blog.description)
          this.contentVditor.setValue(this.blog.content)
        }

      })

    },

    // list去重
    uniqueList(arr) {
      var newArr = [];
      for (var i = 0; i < arr.length; i++) {
        if (newArr.indexOf(arr[i]) === -1) {
          newArr.push(arr[i])
        }
      }
      return newArr;

    }

  },

  mounted() {
    this.getData()
  },
};

</script>

