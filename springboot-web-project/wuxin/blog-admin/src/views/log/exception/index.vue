<template>
  <div class="app-container">
    <MySearchHeader
      :show-create-button="false"
      @handleSearch="handleFilter"
    >
      <el-button
        class="m-margin-left-mini"
        icon="el-icon-delete"
        size="small"
        type="danger"
        @click.native.prevent="delAll"
      >全部删除
      </el-button>
    </MySearchHeader>

    <!-- 表格数据 -->
    <el-table
      v-loading="listLoading"
      class="m-table"
      max-height="350"
      :data="list"
      highlight-current-row
      fit
      size="mini"
    >

      <el-table-column type="selection" width="55" />
      <el-table-column label="序号" prop="id" align="center" width="55" fixed>
        <template slot-scope="{ row,$index }">
          <span>{{ $index + 1 }}</span>
        </template>
      </el-table-column>
      <el-table-column label="访问标识" align="center" width="200">
        <template slot-scope="{ row }">
          <span class="m-message link-type">{{ row.byCreate }}</span>
        </template>
      </el-table-column>

      <el-table-column label="请求方法" align="center" width="200">
        <template slot-scope="{ row }">
          <span class="m-message">{{ row.method }}</span>
        </template>
      </el-table-column>

      <el-table-column label="请求类型" align="center" width="100">
        <template slot-scope="{ row }">
          <span>{{ row.type | lowerCase }} </span>
        </template>
      </el-table-column>
      <el-table-column label="来源" align="center" width="150">
        <template slot-scope="{ row }">
          <span>{{ row.ipSource }}</span>
        </template>
      </el-table-column>
      <el-table-column label="浏览器" align="center" width="150">
        <template slot-scope="{ row }">
          <span>{{ row.browser | lowerCase }}</span>
        </template>
      </el-table-column>
      <el-table-column label="description" align="center" width="150">
        <template slot-scope="{ row}">
          <span>{{ row.description }}</span>
        </template>
      </el-table-column>

      <el-table-column label="os" align="center" width="150">
        <template slot-scope="{ row}">
          <span>{{ row.os | lowerCase }}</span>
        </template>
      </el-table-column>

      <el-table-column label="日期" width="160" align="center">
        <template slot-scope="{ row }">
          <span>{{ row.createTime }}</span>
        </template>
      </el-table-column>

      <el-table-column label="操作" align="center" width="150" fixed="right">
        <template slot-scope="{ row }">
          <el-button type="primary" circle icon="el-icon-view" @click="showErrorMessage(row.result)" />
          <el-button type="danger" circle icon="el-icon-delete" @click="removeData(row.id,index)" />
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页-->
    <MyPagination
      v-show="total > 0"
      :total="total"
      :page.sync="query.current"
      :limit.sync="query.limit"
      @pagination="getList"
    />

    <el-dialog :visible.sync="dialogVisible" title="详情" width="700px">
      <div slot="title">
        详情
      </div>
      <div>
        <pre class="language-number">
        <code class="language-java" v-text="message" />
      </pre>
      </div>
      <div slot="footer">
        <el-button type="primary" @click="dialogVisible = false">关闭</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import { getExceptionLogList, delExceptionLog, delAllExceptionLog } from '@/api/log'
import { query } from '@/mixin/query'

export default {
  name: 'ExceptionLog',
  filters: {
    lowerCase(value) {
      return value.toLowerCase()
    }
  },
  mixins: [query],
  data() {
    return {
      // 表格key
      dialogVisible: false,
      message: {}
    }
  },
  mounted() {
    this.getList()
  },
  methods: {

    getList() {
      this.listLoading = false
      getExceptionLogList(this.query).then(res => {
        if (res.code === 200) {
          this.list = res.result.records
          this.total = res.result.total
          this.listLoading = false
        }
      })
    },

    removeData(id, index) {
      if (!this.isRoot) {
        this.$message.error('操作失败，不具备该权限！')
        return
      }
      delExceptionLog(id).then(res => {
        this.$SuccessMessage(res, '删除成功', this.list, index)
      })
    },

    delAll() {
      if (!this.isRoot) {
        this.$message.error('操作失败，不具备该权限！')
        return
      }
      delAllExceptionLog().then(res => {
        if (res.code === 200) {
          this.$notify.success('删除成功！')
          this.getList()
        }
      })
    },

    showErrorMessage(e) {
      this.dialogVisible = true
      this.message = e
    }
  }
}
</script>

<style scoped>

code.language-java,
pre.language-java {
  color: #f8cb06;
  background: none;
  text-shadow: 0 1px rgba(0, 0, 0, 0.3);
  font-family: Consolas, Monaco, 'Andale Mono', 'Ubuntu Mono', monospace;
  font-size: 1em;
  text-align: left;
  white-space: pre;
  word-spacing: normal;
  word-break: normal;
  word-wrap: normal;
  line-height: 1.5;

  -moz-tab-size: 4;
  -o-tab-size: 4;
  tab-size: 4;

  -webkit-hyphens: none;
  -moz-hyphens: none;
  -ms-hyphens: none;
  hyphens: none;
}

pre.language-java {
  padding: 1em;
  margin: .5em 0;
  overflow: auto;
  border-radius: 0.3em;
}

:not(pre) > code.language-java,
pre.language-java {
  background: rgb(22, 22, 22);
}

:not(pre) > code.language-java {
  padding: .1em;
  border-radius: .3em;
  white-space: normal;
}
</style>
