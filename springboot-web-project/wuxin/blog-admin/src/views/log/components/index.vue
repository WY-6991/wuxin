<template>
  <div class="app-container">
    <MySearchHeader
      type="datetimerange"
      :show-create-button="false"
      :query="query"
      @handleSearch="handleSearch"
    >
      <el-button icon="el-icon-delete" type="danger" size="mini" @click.native.prevent="deleteAll">全部清空</el-button>
    </MySearchHeader>
    <el-table
      ref="multipleTable"
      :key="tableKey"
      v-loading="listLoading"
      highlight-current-row
      max-height="350"
      :data="list"
      size="large"
      @selection-change="handleSelectionChange"
    >
      <el-table-column
        type="selection"
        width="55"
      />

      <el-table-column label="序号" align="center" width="55" :fixed="fit?'':'left'">
        <template slot-scope="{ row,$index }">
          <span>{{ $index + 1 }}</span>
        </template>
      </el-table-column>

      <el-table-column v-if="showUsername" label="用户" align="center" :width="fit?'auto':100">
        <template slot-scope="{ row }">
          <MyUserLink :username="row.username ? row.username : '未知'" />
        </template>
      </el-table-column>

      <el-table-column v-if="showByCreate" label="标识" align="center" width="150">
        <template slot-scope="{ row }">
          <el-tooltip v-if="row.byCreate" :content="row.byCreate">
            <span
              class="m-message link-type m-cursor-pointer"
              @click.prevent="keywordsSearch(row.byCreate)"
            >{{ row.byCreate ? row.byCreate : '未知' }}</span>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column label="ip" align="center" :width="fit?'auto':120">
        <template slot-scope="{ row }">
          <span class="m-message">{{ row.ip ? row.ip : '未知' }}</span>
        </template>
      </el-table-column>

      <el-table-column label="来源" align="center" :width="fit?'auto':120">
        <template slot-scope="{ row }">
          <span class="m-message">{{ row.ipSource ? row.ipSource : '未知' }}</span>
        </template>
      </el-table-column>

      <el-table-column label="浏览器" align="center" :width="fit?'auto':100">
        <template slot-scope="{ row }">
          <span class="m-message">{{ row.browser ? row.browser : '未知' }}</span>
        </template>
      </el-table-column>

      <el-table-column label="os" align="center" :width="fit?'auto':100">
        <template slot-scope="{ row }">
          <span class="m-message">{{ row.os ? row.os : '未知' }}</span>
        </template>
      </el-table-column>

      <el-table-column v-show="showDescription" label="描述" align="center" :width="fit?'auto':100">
        <template slot-scope="{ row }">
          <span class="m-message">{{ row.description ? row.description : '未知' }}</span>
        </template>
      </el-table-column>

      <el-table-column label="日期" align="center" width="160">
        <template slot-scope="{ row }">
          <span>{{ row.createTime ? row.createTime : '未知' }}</span>
        </template>
      </el-table-column>

      <el-table-column label="状态" align="center" :width="fit?'auto':50">
        <template slot-scope="{ row }">
          <el-tag :type="row.code===200?'success':'danger'">{{ row.code === 200 ? '成功' : '失败' }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column label="操作" align="center" :width="fit?'auto':150" :fixed="fit?null:'right'">
        <template slot-scope="{ row,$index }">
          <el-button
            v-if="showMessage"
            type="primary"
            icon="el-icon-view"
            circle
            @click.native.prevent="showDetail(row) "
          />
          <el-button type="danger" icon="el-icon-delete" circle @click.native.prevent="delLogById(row.id,$index)" />
        </template>
      </el-table-column>
    </el-table>

    <div style="margin-top: 20px">
      <el-button icon="el-icon-delete" plain type="primary" size="mini" @click.prevent="delLogByIds">删除所选择</el-button>
      <el-button size="mini" @click="toggleSelection()">取消选择</el-button>
    </div>

    <!-- 分页-->
    <MyPagination
      v-show="total > 0"
      :total="total"
      :page.sync="query.current"
      :limit.sync="query.limit"
      @pagination="getList"
    />
    <el-dialog width="50%" title="参数信息" :visible.sync="dialogVisible">
      <LogDetail v-if="name!=='error'" :log="log" />
      <ErrorLogResult v-else :error="log"/>
      <div slot="footer">
        <el-button type="info" @click="dialogVisible = false">关闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>

import MyUserLink from '@/components/MyComponents/MyUsernameLink'
import LogDetail from '@/views/log/components/LogDetail'
import ErrorLogResult from '@/views/log/components/ErrorLog'

export default {
  name: 'MyLog',
  components: { ErrorLogResult, LogDetail, MyUserLink },
  props: {
    list: {
      type: Array,
      // eslint-disable-next-line vue/require-valid-default-prop
      default: []
    },
    name: {
      type: String,
      default: 'ok'
    },
    query: {
      type: Object,
      // eslint-disable-next-line vue/require-valid-default-prop
      default: { current: 1, limit: 10 }
    },
    fit: {
      type: Boolean,
      default: true
    },
    showUsername: {
      type: Boolean,
      default: false
    },
    showByCreate: {
      type: Boolean,
      default: true
    },

    showMessage: {
      type: Boolean,
      default: true
    },
    listLoading: {
      type: Boolean,
      default: true
    },
    title: {
      type: String,
      default: '日志记录'
    },
    total: {
      type: Number,
      default: 0
    },
    showDescription: {
      type: Boolean,
      default: true
    }

  },
  data() {
    return {
      // 表格key
      tableKey: 0,
      log: {},
      dialogVisible: false,
      multipleSelection: [],
      ids: []
    }
  },

  mounted() {
    this.getList()
    setTimeout(() => {
      this.$prismjs.highlightAll()
    }, 1000)
  },
  methods: {

    getList() {
      console.log('请求下一页数据....')
      this.$emit('getList', this.query)
    },

    delLogById(id, index) {
      if (!this.isRoot) {
        return this.$message.error('操作失败，无权限执行该操作！')
      }
      this.$emit('delLogById', { 'id': id, 'index': index })
    },

    delLogByIds() {
      if (!this.isRoot) {
        return this.$message.error('操作失败，无权限执行该操作！')
      }
      this.$message.warning('该功能暂实现哦')
    },

    deleteAll() {
      if (!this.isRoot) {
        return this.$message.error('操作失败，无权限执行该操作！')
      }
      this.$emit('delAllLog')
    },

    showDetail(log) {
      this.log = log
      this.dialogVisible = true
    },

    toggleSelection() {
      this.$refs.multipleTable.clearSelection()
    },
    handleSelectionChange(val) {
      this.multipleSelection = val
    },
    handleSearch(query) {
      this.$emit('handleSearch', query)
    },
    keywordsSearch(keywords) {
      this.query.keywords = keywords
      this.$emit('handleSearch', this.query)
    }
  }

}
</script>
<style scoped>

code.language-json,
pre.language-json {
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

pre.language-json {
  padding: 1em;
  margin: .5em 0;
  overflow: auto;
  border-radius: 0.3em;
}

:not(pre) > code.language-json,
pre.language-json {
  background: rgb(22, 22, 22);
}

:not(pre) > code.language-json {
  padding: .1em;
  border-radius: .3em;
  white-space: normal;
}
</style>

