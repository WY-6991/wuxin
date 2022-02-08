<template>
  <MyLog
    title="异常日志记录"
    :show-controller="false"
    :show-username="false"
    :list-loading="listLoading"
    :list="list"
    :total="total"
    :query="query"
    @getList="getList"
    @delLogById="removeData"
    @delAllLog="delAll"
    @handleSearch="handleFilter"
  />
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
