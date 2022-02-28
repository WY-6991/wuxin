<template>
  <div class="app-container">
    <MySearchHeader @handleCreate="openBlogList" @handleSearch="handleFilter"></MySearchHeader>
    <!-- 表格数据 -->
    <el-table
      :key="tableKey"
      v-loading="listLoading"
      :data="list"
      highlight-current-row
      size="small"
      class="m-table"
      fit

    >
      <el-table-column
        label="序号"
        prop="id"
        sortable="custom"
        align="center"
        width="100"
      >
        <template slot-scope="{ row,$index }">
          <span>{{ $index + 1 }}</span>
        </template>
      </el-table-column>

      <el-table-column label="标题" align="center">
        <template slot-scope="{ row }">
          <el-tooltip placement="bottom" trigger="hover" :content="row.title" width="auto">
            <span class="link-type m-message" @click="handleUpdate(row)">{{ row.title }}</span>
          </el-tooltip>
        </template>
      </el-table-column>

      <el-table-column label="分类" align="center">
        <template slot-scope="{ row }">
          <span>{{ row.archiveTitle }}</span>
        </template>
      </el-table-column>

      <el-table-column label="类型" align="center" width="100">
        <template slot-scope="{ row }">
          <el-tag :type="row.type===1?'success':'warning'">{{ row.type === 1 ? '原创' : '转载' }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="日期" align="center">
        <template slot-scope="{ row }">
          <span>{{ row.createTime }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="200">
        <template slot-scope="{ row, $index }">
          <el-button size="mini" type="primary" icon="el-icon-edit" @click="handleUpdate(row, $index)">
            编辑
          </el-button>
          <el-button size="mini" type="danger" icon="el-icon-delete" @click="handleDelete(row.archiveId, $index)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页插件 -->
    <MyPagination
      v-show="total > 0"
      :total="total"
      :page.sync="query.current"
      :limit.sync="query.limit"
      @pagination="getList"
    />
    <el-dialog title="文章列表" :visible.sync="dialogTableVisible" width="50%">
      <el-row>
        <el-col :span="8">
          <el-input style="width: 200px;" prefix-icon="el-icon-search" v-model="blogKeywords" size="mini"
                    placeholder="请输入关键词..."></el-input>
        </el-col>
        <el-col :span="6">
          <el-button size="mini" type="warning" plain icon="el-icon-plus" @click="handleCreate({'title':'',type:2})">
            添加转载
          </el-button>
        </el-col>
      </el-row>

      <el-table
        :data="blogs"
        fit
        max-height="350"
        highlight-current-row
        style="width: 100%;"
        size="mini"

      >
        <el-table-column
          label="序号"
          prop="id"
          align="center"
          width="50"
        >
          <template slot-scope="{ row,$index }">
            <span>{{ $index + 1 }}</span>
          </template>
        </el-table-column>

        <el-table-column label="标题" align="center">
          <template slot-scope="{ row }">
            <span class="m-message">{{ row.title }}</span>
          </template>
        </el-table-column>


        <el-table-column label="发布日期" width="160" align="center">
          <template slot-scope="{ row }">
            <span>{{ row.createTime }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" width="100" class-name="small-padding fixed-width">
          <template slot-scope="{ row, $index }">
            <el-button size="mini" icon="el-icon-plus" type="primary"
                       @click.prevent="handleCreate({'title':row.title,'type':1,'blogId':row.blogId})">添加
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <div slot="footer">
        <el-button @click.prevent="dialogTableVisible=false" size="small">关闭</el-button>
      </div>
    </el-dialog>


    <el-dialog :title="status==='create'?'添加':'编辑'" :visible.sync="dialogFormVisible" width="35%">
      <el-form :model="archive" :rules="rules" ref="archiveFrom" size="mini" label-position="left" label-width="50px">
        <el-form-item label="标题" prop="title">
          <el-input v-model="archive.title"></el-input>
        </el-form-item>
        <el-form-item label="类型" required>
          <el-radio :label="2" v-model="archive.type">转载</el-radio>
          <el-radio :label="1" v-model="archive.type">原创</el-radio>
        </el-form-item>
        <el-form-item label="地址" v-if="archive.type===2" prop="url">
          <el-input v-model="archive.url"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click.prevent="dialogFormVisible=false" size="small">取 消</el-button>
        <el-button type="primary" @click.prevent="status==='create'?createData():updateData()" size="small">确定
        </el-button>
      </div>
    </el-dialog>


  </div>
</template>

<script>
import {addArchive, delArchive, getAllBlog, getArchiveList, updateArchive} from "@/api/archive";
import {validateUrl} from "@/utils/validate";
import {query} from "@/mixin/query";

export default {
  name: "Archive",
  mixins:[query],
  data() {
    return {
      // 表格key
      tableKey: 0,
      dialogFormVisible: false,
      dialogTableVisible: false,
      blogList: [],
      blogKeywords: '',
      archive: {},
      status: 'create',
      rules: {
        title: [
          {required: true, message: '文章标题必填', trigger: 'blur'}
        ],
        url: [
          {required: true, validator: validateUrl, trigger: 'blur'}
        ],
      }
    };
  },
  mounted() {
    this.getList()
    this.getBlogList()
  },

  computed: {
    blogs() {
      return this.blogList.filter(blog => {
        return blog.title.indexOf(this.blogKeywords) !== -1 || blog.createTime.indexOf(this.blogKeywords) !== -1
      })
    }
  },
  methods: {
    getBlogList() {
      getAllBlog().then(res => {
        if (res.code === 200) {
          this.blogList = res.result
        }
      })
    },

    // 获取list
    getList() {
      this.listLoading = true;
      getArchiveList(this.query).then(res => {
        this.list = res.result.records
        this.total = res.result.total
        this.listLoading = false
      })
    },


    // 修改操作
    handleUpdate(row) {
      this.status = 'update'
      this.archive = row
      this.dialogFormVisible = true
    },

    handleCreate(row) {
      this.status = 'create'
      this.archive = row
      this.dialogFormVisible = true
    },


    openBlogList() {
      this.dialogTableVisible = true
    },


    // 删除操作
    handleDelete(id, index) {
      delArchive(id).then(res => {
        if (res.code === 200) {
          this.list.splice(index, 1)
          this.$notify.success("删除成功！")
        }
      })
    },
    createData() {
      this.$refs.archiveFrom.validate(valid => {
        if (valid) {
          if (this.archive.type === 2 && (!this.archive.url)) {
            this.$message.error('操作失败，转载地址不能为空！')
          }
          if (this.archive.type === 1 && (!this.archive.blogId)) {
            this.$message.error('添加失败，获取不到原创文章id')
          }
          addArchive(this.archive).then(res => {
            if (res.code === 200) {
              this.$message.success('添加成功！')
              this.$nextTick(() => {
                this.getList()
                this.dialogFormVisible = false
              })
            }
          })
        }
      })
    },
    updateData() {
      this.$refs.archiveFrom.validate(valid => {
        if (valid) {
          if (this.archive.type === 2 && (!this.archive.url)) {
            this.$message.error('操作失败，转载地址不能为空！')
          }
          if (this.archive.type === 1 && (!this.archive.blogId)) {
            this.$message.error('添加失败，获取不到原创文章id')
          }
          updateArchive(this.archive).then(res => {
            if (res.code === 200) {
              this.$message.success('修改成功')
              this.dialogFormVisible = false
            }
          })
        }
      })


    }
  }
};
</script>
