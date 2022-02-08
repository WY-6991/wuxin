<template>
  <div>
    <el-descriptions title="详情信息" size="mini" direction="vertical" border>
      <el-descriptions-item label="标识">{{ log.byCreate }}</el-descriptions-item>
      <el-descriptions-item label="描述">{{ log.description }}</el-descriptions-item>
      <el-descriptions-item label="方法、类型">
        <span>{{ log.method }}</span>
        <el-tag size="small" type="warning">{{ log.type }}</el-tag>
      </el-descriptions-item>
      <el-descriptions-item label="ip、ip来源、操作系统、浏览器">
        <el-tag size="small" type="info">{{ log.ip }}</el-tag>
        <el-tag size="small" type="info">{{ log.ipSource }}</el-tag>
        <el-tag size="small" type="info">{{ log.os }}</el-tag>
        <el-tag size="small" type="info">{{ log.browser }}</el-tag>
      </el-descriptions-item>
      <el-descriptions-item label="状态码、请求结果">
        <el-tag size="small" :type="log.code===200?'success':'danger'">{{ log.code }}</el-tag>
        <el-tag size="small" :type="log.code===200?'success':'danger'">{{ log.result }}</el-tag>
      </el-descriptions-item>
      <el-descriptions-item label="请求日期">
        <span>{{ log.createTime }}</span>
      </el-descriptions-item>
    </el-descriptions>
  </div>
</template>
<script>
export default {
  name: 'LogDetail',
  props: ['log']
}
</script>
<style scoped>
.el-tag{
  margin: 1px !important;
}
</style>
