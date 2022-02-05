<template>

  <MyLog
    :show-username="true"
    :show-controller="false"
    :show-method="false"
    :show-controller-button="false"
    :show-type="false"
    :show-url="false"
    :show-message="false"
    :query="query"
    :title="title"
    :list="list"
    :list-loading="listLoading"
    :total="total"
    @getList="getList"
    @delLogById="removeData"
    @delAllLog="delAllLog"
    @handleSearch="handleSearch"
  />
</template>

<script>


import Pagination from "@/components/Pagination";
import {getLoginLogList, delLoginLog, delAllLoginLog, getAccessLogList} from "@/api/log";
import MyLog from "@/views/log/components";

export default {
  name: "LoginLog",
  components: {MyLog, Pagination},

  data() {
    return {
      // 表格key
      title: '登录日志记录',
      list: [], // 列表数据
      total: 0, // 数据总数
      listLoading: true,
      query: {current: 1, limit: 10, keywords: null, start: null, end: null},

    };
  },

  methods: {


    getData(query) {
      this.listLoading = false;
      this.query.keywords = query.keywords
      getLoginLogList(query).then(res => {
        this.list = res.result.records
        this.total = res.result.total
        this.listLoading = false
      })
    },


    // 获取blogList
    getList() {
      this.getData(this.query)
    },

    handleSearch(query) {

      this.getData(query)
    },

    removeData(params) {
      delLoginLog(params.id).then(res => {
        if (res.code !== 200) return false
        this.$notify.success("删除成功！")
        this.list.splice(params.index, 1)
      })
    },

    delAllLog() {
      delAllLoginLog().then(res => {
        if (res.code !== 200) return false
        this.$notify.success("删除成功！")
        this.list = []
      })
    }
  }
};
</script>

<style scoped>

.message {
  max-width: 120px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;

}

</style>
