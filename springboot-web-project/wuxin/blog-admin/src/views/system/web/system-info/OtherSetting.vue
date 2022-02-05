<template>
  <MyCard title="其他设置">
    <el-form :model="systemInfo" label-width="100px" size="small">
      <el-form-item label="评论标签名">
        <el-input prefix-icon="el-icon-s-opportunity" v-model="systemInfo.commentLabelName" style="width: 200px;" />
        <el-select v-model="systemInfo.commentLabelColor" placeholder="评论标签颜色" style="width: 100px;" class="m-margin-left-small">
          <el-option :value="color" :key="index" v-for="(color,index) in colors"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="域名备案">
        <el-input type="textarea"  :autosize="{ minRows: 2, maxRows: 4}" v-model="systemInfo.webRecord" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="small" @click.prevent="updateData">确认</el-button>
      </el-form-item>
    </el-form>
  </MyCard>

</template>
<script>
import {colors} from "@/utils/color";
import {minix} from "@/views/system/web/system-info/minix";

export default {
  name: 'OtherSetting',

  mixins: [minix],
  data() {
    return {
      colors: colors,
    }
  },
}
</script>
