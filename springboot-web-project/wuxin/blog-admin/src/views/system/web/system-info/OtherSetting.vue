<template>
  <MyCard title="其他设置">
    <el-form :model="systemInfo" label-width="100px" size="small">
      <el-form-item label="名称">
        <el-input v-model="systemInfo.webName" prefix-icon="el-icon-s-opportunity" />
      </el-form-item>
      <el-form-item label="网址" prop="webUrl">
        <el-input v-model="systemInfo.webUrl" prefix-icon="el-icon-s-home" />
      </el-form-item>
      <el-form-item label="登录地址" prop="loginUrl">
        <el-input v-model="systemInfo.loginUrl" prefix-icon="el-icon-s-platform" />
      </el-form-item>
      <el-form-item label="评论标签名">
        <el-input v-model="systemInfo.commentLabelName" prefix-icon="el-icon-s-opportunity" style="width: 200px;" />
        <el-select v-model="systemInfo.commentLabelColor" placeholder="评论标签颜色" style="width: 100px;" class="m-margin-left-small">
          <el-option v-for="(color,index) in colors" :key="index" :value="color" />
        </el-select>
      </el-form-item>
      <el-form-item label="域名备案">
        <el-input v-model="systemInfo.webRecord" type="textarea" :autosize="{ minRows: 2, maxRows: 4}" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="small" @click.prevent="updateData">确认</el-button>
      </el-form-item>
    </el-form>
  </MyCard>

</template>
<script>
import { colors } from '@/utils/color'
import { minix } from '@/views/system/web/system-info/minix'

export default {
  name: 'OtherSetting',

  mixins: [minix],
  data() {
    return {
      colors: colors
    }
  }
}
</script>
