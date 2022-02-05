<template>
  <MyLabel :temp="temp" @dataChange="dataChange">
    <el-form-item>
      <el-button type="info" size="small" @click="cancel">取消</el-button>
      <el-button type="primary" size="small" @click="createTag">确定</el-button>
    </el-form-item>
  </MyLabel>
</template>
<script>

import {minix} from "@/views/pages/label/minix";
import {createTag} from "@/api/tag";

export default {
  name: "AddTag",
  mixins: [minix],
  methods: {
    createTag() {
      if(!this.isRoot){
        this.$message.error('添加失败，无权限执行该操作！')
        return
      }
      createTag(this.temp).then(res => {
        if (res.code === 200) {
          // 添加成功之后重新加载list
          this.$emit('getList')
          // 添加成功之后关闭窗口
          this.$emit('closeAddLabel')
          // 添加成功之后重置数据
          this.restTemp()
          this.$message.success('添加成功！')
        }

      })


    },
  },


};
</script>
<style scoped>
.el-tag {
  color: #ffffff;
  width: 100px !important;
}
</style>
