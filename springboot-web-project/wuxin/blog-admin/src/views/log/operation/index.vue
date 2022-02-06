<template>
  <MyLog
    title="操作日志记录"
    :show-username="true"
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

import {
  delAllOperationLog,
  delOperationLog,
  delPartOperationLog,
  getOperationLogList
} from '@/api/log'
import MyLog from '@/views/log/components/index'
import { query } from '@/mixin/query'

export default {
  name: 'OperationLog',
  components: { MyLog },
  mixins: [query],
  methods: {
    // 获取blogList
    getList() {
      this.listLoading = false
      getOperationLogList(this.query).then(res => {
        if (res.code === 200) {
          this.list = res.result.records
          this.total = res.result.total
          this.listLoading = false
        }
      })
    },

    removeData(params) {
      delOperationLog(params.id).then(res => {
        if (res.code !== 200) return false
        this.$notify.success('删除成功！')
        this.list.splice(params.index, 1)
      })
    },

    delPart() {
      delPartOperationLog(this.ids).then(res => {
        this.$notify.success('' + this.ids)
        if (res.code !== 200) return false
        this.$notify.success('删除成功！')
        this.ids = []
        this.getList()
      })
    },

    deleteAll() {
      delAllOperationLog().then(res => {
        if (res.code === 200) {
          this.list = []
          this.$notify.success('操作日志全部删除成功！')
        }
      })
    }

  }

}
</script>

