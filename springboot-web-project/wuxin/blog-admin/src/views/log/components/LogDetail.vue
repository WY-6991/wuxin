<template>
  <div>
    <!--    <el-form :model="log" size="mini" label-width="88px" label-position="left">-->
    <!--      <el-form-item label="请求标识">-->
    <!--        <el-tag type="info">{{ log.byCreate }}</el-tag>-->
    <!--      </el-form-item>-->
    <!--      <el-form-item label="请求方法">-->
    <!--        <el-tag type="info">{{ log.method }}</el-tag>-->
    <!--      </el-form-item>-->
    <!--      <el-form-item label="请求类型">-->
    <!--        <el-tag type="info">{{ log.type }}</el-tag>-->
    <!--      </el-form-item>-->
    <!--      <el-form-item label="描述" v-show="log.description">-->
    <!--        <el-tag type="info">{{ log.description }}</el-tag>-->
    <!--      </el-form-item>-->
    <!--      <el-form-item label="ip">-->
    <!--        <el-tag type="info">{{ log.ip }}</el-tag>-->
    <!--      </el-form-item>-->
    <!--      <el-form-item label="ip来源">-->
    <!--        <el-tag type="info">{{ log.ipSource }}</el-tag>-->
    <!--      </el-form-item>-->
    <!--      <el-form-item label="请求日期">-->
    <!--        <el-tag type="info">{{ log.createTime }}</el-tag>-->
    <!--      </el-form-item>-->
    <!--      <el-form-item label="浏览器">-->
    <!--        <el-tag type="info">{{ log.browser }}</el-tag>-->
    <!--      </el-form-item>-->
    <!--      <el-form-item label="os">-->
    <!--        <el-tag type="info">{{ log.os }}</el-tag>-->
    <!--      </el-form-item>-->
    <!--      <el-form-item label="状态码">-->
    <!--        <el-tag :type="log.code===200?'success':'danger'">{{ log.code }}</el-tag>-->
    <!--      </el-form-item>-->
    <!--      <el-form-item label="结果">-->
    <!--        <el-tag type="info">{{ log.result }}</el-tag>-->
    <!--      </el-form-item>-->
    <!--    </el-form>-->
  </div>
</template>
<script>
export default {
  name: 'LogDetail',
  props: ['log']
}
</script>
<style scoped>

</style>
