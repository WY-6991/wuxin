<template>
  <div class="app-container">
    <MySearchHeader
      :query="query"
      :show-time-button="false"
      @handleCreate="handleCreate"
      @handleSearch="handleSearch"
    />

    <LabelTableList
      :list="list"
      @handleUpdate="handleUpdate"
      @deleteData="deleteData"
      @updateColor="updateColor">
    </LabelTableList>

    <!-- 分页插件 -->
    <MyPagination
      v-show="total > 0"
      :total="total"
      :page.sync="query.current"
      :limit.sync="query.limit"
      @pagination="getList"
    />

    <el-dialog width="30%"
      :title="dialogStatus === 'create' ? '添加分类' :'修改分类'"
      :visible.sync="dialogFormVisible">
      <el-form :model="temp" :rules="labelRules" label-position="left" label-width="50px" size="small" ref="labelForm">
        <el-form-item label="名称" prop="name">
          <el-input v-model="temp.name" class="m-input-width-80pre" />
        </el-form-item>
        <el-form-item label="颜色" prop="color">
          <el-select v-model="temp.color" placeholder="请选择" class="m-input-width-80pre">
            <el-option v-for="(item) in colors" :key="item" :id="item" :value="item" @change="dataChange">
              <span style="float:left;"><el-tag :color="item" class="m-input-width-100" /></span>
              <span style="float:right;">{{ item }}</span>
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button type="info" size="small" @click="cancel">取 消</el-button>
        <el-button type="primary" size="small" @click="dialogStatus === 'create' ? createData(temp) :updateData(temp)">确 定
        </el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import { updateTag, delTag, updateTagColor, createTag, getTagList } from '@/api/tag'
import { minix } from '../minix'
import {delCategory} from "@/api/category";

export default {
  name: 'Tag',
  mixins: [minix],

  mounted() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true;
      getTagList(this.query).then(res => {
        this.list = res.result.records
        this.total = res.result.total
      })
      this.listLoading = false;
    },
    // 添加
    createData(data) {
      createTag(data).then(res => {
        if (res.code === 200) {
          this.$message.success('添加成功！')
          this.dialogFormVisible = false;
          this.getList()
          this.restTemp()
        }
      })
    },
    // 修改颜色和名称
    updateData(data) {
      updateTag(data).then(res => {
        if(res.code === 200) {
          this.$message.success('修改成功！')
        }
        this.dialogFormVisible = false
        this.restTemp()
      })
    },

    // 只修改颜色
    updateColor(tag) {
      updateTagColor(tag).then(res => {
        if (res.code === 200) {
          this.$message.success('修改成功！')
          this.restTemp()
        }
      })
    },

    deleteData(obj) {
      const { data, index } = obj
      this.$confirm('此操作将永久删除该标签, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        delTag(data.tagId).then(res => {
          if (res.code === 200) {
            this.$message.success('删除成功！')
            this.list.splice(index, 1)
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    }
  }
}
</script>

<style scoped>

.el-tag {
  color: #fff;
  width: 100px !important;
}

.m-input-width-100 {
  width: 100px !important;
}

.m-input-width-80pre {
  width: 80% !important;
}
</style>
