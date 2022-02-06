<template>
  <MyLog
    title="访问日志记录"
    :show-username="false"
    :list="list"
    :total="total"
    :query="query"
    :fit="false"
    :list-loading="listLoading"
    @getList="getList"
    @delLogById="removeData"
    @delAllLog="deleteAll"
    @handleSearch="handleFilter"
  />

</template>

<script>
import { delAccessLog, delAllAccessLog, delPartAccessLog, getAccessLogList } from '@/api/log'
import MyLog from '@/views/log/components'
import { query } from '@/mixin/query'

export default {
  name: 'AccessLog',
  components: { MyLog },
  mixins: [query],
  methods: {

    getList() {
      this.listLoading = false
      getAccessLogList(this.query).then(res => {
        this.list = res.result.records
        this.total = res.result.total
        this.listLoading = false
      })
    },
    removeData(params) {
      delAccessLog(params.id).then(res => {
        if (res.code !== 200) return false
        this.list.splice(params.index, 1)
        this.$notify.success('删除成功！')
      })
    },

    delPart() {
      delPartAccessLog(this.ids).then(res => {
        this.$notify.success('' + this.ids)
        if (res.code !== 200) return false
        this.$message.success('删除成功！')
      })
    },

    deleteAll() {
      delAllAccessLog().then(res => {
        if (res.code !== 200) return false
        this.$notify.success('删除成功！')
        this.list = []
      })
    }
  }
}
</script>

<style scoped>

.message {
  width: 100px !important;
  overflow: hidden !important;
  text-overflow: ellipsis !important;
  white-space: nowrap !important;

}

</style>
