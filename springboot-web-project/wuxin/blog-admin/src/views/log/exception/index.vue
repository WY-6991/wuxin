<template>
  <MyLog
    title="异常日志记录"
    :show-controller="false"
    :show-username="false"
    :list-loading="listLoading"
    :list="list"
    :total="total"
    :query="query"
    name="error"
    @getList="getList"
    @delLogById="removeData"
    @delAllLog="delAll"
    @handleSearch="handleFilter"
  >

  </MyLog>
</template>

<script>
import { getExceptionLogList, delExceptionLog, delAllExceptionLog } from '@/api/log'
import { query } from '@/mixin/query'
import MyLog from '@/views/log/components'

export default {
  name: 'ExceptionLog',
  components: { MyLog },
  mixins: [query],
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
      delExceptionLog(id).then(res => {
        this.$SuccessMessage(res, '删除成功', this.list, index)
      })
    },

    delAll() {
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
