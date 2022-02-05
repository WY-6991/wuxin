<template>
  <div class="app-container">

    <MySearchHeader
      type="datetimerange"
      :show-create-button="false"
      @handleSearch="handleSearch"
    >
      <el-button icon="el-icon-delete" type="danger" size="mini" @click.native.prevent="deleteAll">全部清空</el-button>
    </MySearchHeader>

    <!-- 表格数据 -->
    <el-table
      ref="multipleTable"
      v-loading="listLoading"
      highlight-current-row
      max-height="350"
      :key="tableKey"
      :data="list"
      size="mini"
      @selection-change="handleSelectionChange"
    >

      <el-table-column
        type="selection"
        width="55">
      </el-table-column>

      <el-table-column label="序号" align="center" width="55" :fixed="fit?'':'left'">
        <template slot-scope="{ row,$index }">
          <span>{{ $index + 1 }}</span>
        </template>
      </el-table-column>

      <el-table-column label="用户" align="center" :width="fit?'auto':100" v-if="showUsername">
        <template slot-scope="{ row,$index }">
          <MyUserLink :username="row.username ? row.username : '未知'"
                      @click.prevent.native="handleFilterByUsername(row.username)" />
        </template>
      </el-table-column>

      <el-table-column label="标识" align="center" :width="fit?'auto':100" v-if="showByCreate">
        <template slot-scope="{ row }">
          <el-tooltip :content="row.byCreate" v-if="row.byCreate">
            <span class="m-message">{{ row.byCreate ? row.byCreate : '未知' }}</span>
          </el-tooltip>
        </template>
      </el-table-column>

      <el-table-column label="请求路径" align="center" :width="fit?'auto':200" v-if="showUrl">
        <template slot-scope="{ row }">
          <el-tooltip :content="row.url">
            <span class="m-message">{{ row.url ? row.url : '未知' }}</span>
          </el-tooltip>
        </template>
      </el-table-column>

      <el-table-column label="方法" align="center" :width="fit?'auto':150" v-if="showMethod">
        <template slot-scope="{ row }">
          <span>{{ row.method ? row.method : '未知' }}</span>
        </template>
      </el-table-column>

      <el-table-column label="type" align="center" :width="fit?'auto':60" v-if="showType">
        <template slot-scope="{ row }">
          <span>{{ row.type ? row.type : '未知' }}</span>
        </template>
      </el-table-column>

      <el-table-column label="ip" align="center" :width="fit?'auto':120">
        <template slot-scope="{ row }">
          <span>{{ row.ip ? row.ip : '未知' }}</span>
        </template>
      </el-table-column>

      <el-table-column label="来源" align="center" :width="fit?'auto':120">
        <template slot-scope="{ row }">
          <span>{{ row.ipSource ? row.ipSource : '未知' }}</span>
        </template>
      </el-table-column>

      <el-table-column label="浏览器" align="center" :width="fit?'auto':100">
        <template slot-scope="{ row }">
          <span>{{ row.browser ? row.browser : '未知' }}</span>
        </template>
      </el-table-column>

      <el-table-column label="os" align="center" :width="fit?'auto':100">
        <template slot-scope="{ row }">
          <span>{{ row.os ? row.os : '未知' }}</span>
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
          <el-button type="primary" icon="el-icon-view" circle v-if="showMessage"
                     @click.native.prevent="showDetail(row.params,row.result) " />
          <el-button type="danger" icon="el-icon-delete" circle @click.native.prevent="delLogById(row.id,$index)" />
        </template>
      </el-table-column>
    </el-table>

    <div style="margin-top: 20px">
      <el-button icon="el-icon-delete" plain type="primary" size="mini" @click.prevent="delLogByIds">删除所选择</el-button>
      <el-button @click="toggleSelection()" size="mini">取消选择</el-button>
    </div>

    <!-- 分页-->
    <MyPagination
      v-show="total > 0"
      :total="total"
      :page.sync="query.current"
      :limit.sync="query.limit"
      @pagination="getList"
    />


    <el-dialog width="800px" title="参数信息" :visible.sync="dialogVisible" v-if="showMessage">
      <JsonCode :params="log.params" :result="log.result" />
      <div slot="footer">
        <el-button @click.native.prevent="cancel" type="info">关闭</el-button>
      </div>

    </el-dialog>

  </div>
</template>

<script>


import Pagination from "@/components/Pagination";
import MyUserLink from "@/components/MyComponents/MyUsernameLink";
import JsonCode from "@/views/log/components/JsonFormat";
import MySearchHeader from "@/components/MyComponents/MySearchHeader";

export default {
  name: "MyLog",
  components: {JsonCode, MyUserLink},
  data() {
    return {
      // 表格key
      tableKey: 0,
      log: {},
      dialogVisible: false,
      multipleSelection: [],
      ids: []
    };
  },

  props: {
    list: {
      type: Array,
      default: []
    },
    query: {
      type: Object,
      default: {current: 1, limit: 10},
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
    showUrl: {
      type: Boolean,
      default: true
    },

    showController: {
      type: Boolean,
      default: true
    },

    showMethod: {
      type: Boolean,
      default: true
    },
    showType: {
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
      default: 0,
    }


  },

  mounted() {
    this.getList()
  },
  methods: {

    getList() {
      console.log('请求下一页数据....')
      this.$emit('getList', this.query)
    },

    handleFilterByUsername(username) {
      this.query.keywords = username
      this.handleSearch(this.query)
    },

    delLogById(id, index) {
      if (!this.isRoot) {
        return this.$message.error('操作失败，无权限执行该操作！')
      }
      this.$emit("delLogById", {'id': id, 'index': index})

    },

    delLogByIds() {
      if (!this.isRoot) {
        return this.$message.error('操作失败，无权限执行该操作！')
      }
      this.$message.warning("该功能暂实现哦")
      // this.$emit("delLogByIds")
    },

    deleteAll() {
      if (!this.isRoot) {
        return this.$message.error('操作失败，无权限执行该操作！')
      }
      this.$emit("delAllLog")
    },

    showDetail(params, result) {
      this.dialogVisible = true
      this.log.params = params
      this.log.result = result

    },
    // 取消
    cancel() {
      this.dialogVisible = false
    },

    toggleSelection() {
      this.$refs.multipleTable.clearSelection();
    },

    handleSelectionChange(val) {
      this.multipleSelection = val;
    },

    handleSearch(query) {
      this.$message.success("query=>" + JSON.stringify(query))
      this.$emit('handleSearch', query)
    }
  },


};
</script>
<style>

</style>

