<template>

  <MyLog
    :show-username="false"
    :list="list"
    :total="total"
    :query="query"
    :fit="false"
    :title="title"
    :listLoading="listLoading"
    @getList="getList"
    @delLogById="removeData"
    @delAllLog="deleteAll"
    @handleSearch="handleSearch"
  >
  </MyLog>



</template>

<script>


import {delAccessLog, delAllAccessLog, delPartAccessLog, getAccessLogList} from "@/api/log";
import MyLog from "@/views/log/components";

export default {
  name: "AccessLog",
  components: {MyLog},
  data() {
    return {
      // 表格key
      title:'访问日志记录',
      list: [],
      total: 0, // 数据总数
      listLoading: true,
      query: {current: 1, limit: 10,keywords:null,start:null,end:null},
    };
  },

  methods: {

    getData(query){
      this.listLoading = false;
      getAccessLogList(query).then(res => {
        this.list = res.result.records
        this.total = res.result.total
        this.listLoading = false

      })
    },


    // 获取blogList
    getList() {
      this.getData(this.query)
    },

    handleSearch(query){

      this.getData(query)
    },


    removeData(params) {
      delAccessLog(params.id).then(res => {
        if (res.code !== 200) return false
        this.list.splice(params.index,1)
        this.$notify.success("删除成功！")
      })
    },

    delPart() {
      delPartAccessLog(this.ids).then(res => {
        this.$notify.success(""+this.ids)
        if (res.code !== 200) return false
        this.$message.success("删除成功！")
      })
    },

    deleteAll(){
      delAllAccessLog().then(res => {
        if (res.code !== 200) return false
        this.$notify.success("删除成功！")
        this.list = []
      })
    },



  },


};
</script>

<style scoped>

.message {
  width: 100px !important;
  overflow: hidden !important;
  text-overflow: ellipsis !important;
  white-space: nowrap !important;

}


</style>
