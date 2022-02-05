<template>
  <div class="app-container">

    <MySearchHeader
      @handleCreate="handleCreate"
      @handleSearch="handleFilter"
    />
    <!-- 表格数据 -->
    <el-table
      v-loading="listLoading"
      :data="list"
      fit
      highlight-current-row
      class="m-table"
      max-height="350"
    >
      <el-table-column
        label="序号"
        prop="id"
        align="center"
        width="100"
        fit
      >
        <template slot-scope="{ row,$index }">
          <span>{{ $index + 1 }}</span>
        </template>
      </el-table-column>

      <el-table-column label="信息" align="center">
        <template slot-scope="{ row }">
          <el-tooltip placement="bottom" width="400" trigger="hover" :content="row.content">
            <span class="m-message link-type">{{ row.content }}</span>
          </el-tooltip>
        </template>
      </el-table-column>

      <el-table-column label="节点颜色" align="center">
        <template slot-scope="{ row }">
          <el-color-picker v-model="row.color" @change="updateColor(row)" />
        </template>
      </el-table-column>


      <el-table-column label="发布日期" align="center">
        <template slot-scope="{ row }">
          <span>{{ row.createTime }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center">
        <template slot-scope="{ row,index }">
          <el-button type="primary" size="mini" icon="el-icon-edit" @click="handleUpdate(row)">修改</el-button>
          <el-button type="danger" size="mini" icon="el-icon-delete" @click="removeData(row.id,index)">删除</el-button>
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


    <!-- 编辑或者添加操作-->
    <el-dialog
      :title="dialogStatus === 'create' ? '添加' :'修改'"
      :visible.sync="dialogFormVisible"
      width="40%"
    >
      <el-form :model="row" :rules="rules" ref="dataForm" style="width: 400px" label-width="100px">
        <el-form-item label="内容" prop="content">
          <el-input type="textarea" clearable :rows="5" v-model="row.content"
                    class="m-input-textarea"
          />
        </el-form-item>
        <el-form-item label="颜色" prop="color">
          <el-color-picker v-model="row.color" />
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button size="small" @click="cancel">取 消</el-button>
        <el-button type="primary" size="small" @click="dialogStatus === 'create' ? create(row) : update(row)">确 定
        </el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>


import {getUpdateQuestionList, createQuestion, updateQuestion, delQuestion} from "@/api/update";
import {query} from "@/mixin/query";

export default {
  name: "UpdateQuestion",
  mixins:[query],
  data() {
    return {
      // 表格key
      tableKey: 0,
      formLabelWidth: '120px',
      dialogStatus: '',  // 执行对应方法 create和update
      dialogFormVisible: false, // 显示对话框

      rules: {
        content: [
          {
            required: true,
            message: "内容不能为空哦！",
            trigger: "blur"
          }
        ],
        color: [
          {
            required: true,
            message: "还未选择标签颜色哦！",
            trigger: "blur"
          }
        ]
      },

      row: {
        color: 'rgba(19, 206, 102, 0.8)',
        content: ''
      }
    };
  },
  mounted() {
    this.getList()
  },
  methods: {


    restTemp() {
      this.row = {color: 'rgba(19, 206, 102, 0.8)', content: ''}
    },


    // 获取blogList
    getList() {
      this.listLoading = false;
      getUpdateQuestionList(this.query).then(res => {
        this.list = res.result.records
        this.total = res.result.total
        this.listLoading = false
      })
    },


    removeData(id, index) {
      if (!this.isRoot) {
        this.$message.error('操作失败！没有权限执行该操作！')
        return;
      }
      delQuestion(id).then(res => {
        if (res.code !== 200) return false
        this.$message.success("删除成功！")
        this.list.splice(index, 1)
        this.getList()
      })
    },

    handleCreate() {
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.restTemp()
    },

    handleUpdate(question) {
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.row = question
    },

    updateColor(question) {
      if (!this.isRoot) {
        this.$message.error('操作失败！没有权限执行该操作！')
        return;
      }
      updateQuestion(question).then(res => {
        if (res.code === 200) {
          this.$notify.success("修改成功！")
        }
      })
    },

    create() {
      this.$refs.dataForm.validate(valid => {
        if (valid) {
          if (!this.isRoot) {
            this.$message.error('操作失败！没有权限执行该操作！')
            return;
          }
          createQuestion(this.row).then(res => {
            if (res.code === 200) {
              this.$notify.success("发布成功")
              this.getList()
            }
            this.dialogFormVisible = false
          })

        }
      })

    },

    update() {
      this.$refs.dataForm.validate(valid => {
        if (valid) {
          updateQuestion(this.row).then(res => {
            if (res.code === 200) {
              this.$notify.success("修改成功")
              this.getList()
            }
            this.dialogFormVisible = false
          })
        }
      })
    },

    cancel() {
      this.dialogFormVisible = false
    }


  }
};
</script>
<style>
.m-input-textarea {
  width: 300px !important;
  font-size: 16px !important;
  font-family: Consolas, Menlo, Monaco, Lucida Console, Liberation Mono, DejaVu Sans Mono, Bitstream Vera Sans Mono, Courier New, monospace, serif;
}
</style>

