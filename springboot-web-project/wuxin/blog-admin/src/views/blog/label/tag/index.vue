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

    <el-dialog width="30%" :title="dialogStatus === 'create' ? '添加分类' :'修改分类'" :visible.sync="dialogFormVisible">
      <CustomLabel :dialog-status="dialogStatus" @addTagSuccess="getList" label-name="tag" :temp="temp" @cancel="cancel" />
    </el-dialog>

  </div>
</template>

<script>
import { delTag, updateTagColor, getTagList } from '@/api/tag'
import { minix } from '../minix'
import CustomLabel from '@/components/MyComponents/CustomLabel'

export default {
  name: 'Tag',
  components: { CustomLabel },
  mixins: [minix],

  mounted() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      getTagList(this.query).then(res => {
        this.list = res.result.records
        this.total = res.result.total
      })
      this.listLoading = false
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
