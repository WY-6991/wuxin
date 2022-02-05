<template>

  <MyLog
    :show-username="true"
    :show-by-create="false"
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
  />

</template>

<script>


import {
  delAllOperationLog,
  delOperationLog,
  delPartOperationLog,
  getOperationLogList
} from "@/api/log";
import MyLog from "@/views/log/components/index";

export default {
  name: "OperationLog",
  components: {MyLog},

  data() {
    return {
      // 表格key
      list: [],
      total: 0, // 数据总数
      listLoading: true,
      query: {current: 1, limit: 10,keywords:null,start:null,end:null},
      multipleSelection: [],
      title:'操作日志记录'
    };
  },

  methods: {

    getData(query){
      this.listLoading = false;
      getOperationLogList(query).then(res => {
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
      delOperationLog(params.id).then(res => {
        if (res.code !== 200) return false
        this.$notify.success("删除成功！")
        this.list.splice(params.index,1)
      })
    },

    delPart() {
      delPartOperationLog(this.ids).then(res => {
        this.$notify.success(""+this.ids)
        if (res.code !== 200) return false
        this.$notify.success("删除成功！")
        this.ids = []
        this.getList()
      })
    },

    deleteAll(){
      delAllOperationLog().then(res => {
        if (res.code !== 200) return false
        this.list = []
        this.$notify.success("删除成功！")
      })
    },


  },


};
</script>

