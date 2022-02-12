<template>
  <MyLog
    title="登录日记记录"
    :show-username="true"
    :show-message="false"
    :show-type="false"
    :show-description="false"
    :query="query"
    :list="list"
    :list-loading="listLoading"
    :total="total"
    @getList="getList"
    @delLogById="removeData"
    @delAllLog="delAllLog"
    @handleSearch="handleFilter"
  />
</template>

<script>
import { getLoginLogList, delLoginLog, delAllLoginLog } from '@/api/log'
import MyLog from '@/views/log/components'
import { query } from '@/mixin/query'

export default {
  name: 'LoginLog',
  components: { MyLog },
  mixins: [query],
  methods: {
    getList() {
      getLoginLogList(this.query).then(res => {
        this.list = res.result.records
        this.total = res.result.total
        this.listLoading = false
      })
    },

    removeData(data) {
      const { id, index } = data
      delLoginLog(id).then(res => {
        if (res.code === 200) {
          this.list.splice(index, 1)
          this.$notify.success('删除成功！')
        }
      })
    },

    delAllLog() {
      delAllLoginLog().then(res => {
        if (res.code === 200) {
          this.list = []
          this.$message.success('日志全部删除成功！')
        }
      })
    }

  }
}
</script>

