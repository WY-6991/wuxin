<template>
  <MyLabel @dataChange="dataChange">
    <el-form-item>
      <el-button @click="cancel">取 消</el-button>
      <el-button type="primary" @click="createCategory">确 定</el-button>
    </el-form-item>
  </MyLabel>
</template>
<script>
import {createCategory} from "@/api/category";
import {minix} from "@/views/blog/label/minix";

export default {
  name: "AddCategory",
  mixins: [minix],
  methods: {
    createCategory() {
      if(!this.isRoot){
        this.$message.error('添加失败，无权限执行该操作！')
        return
      }
       createCategory(this.temp).then(res => {
        if (res.code === 200) {
          // 添加成功之后重新加载list
          this.$emit('getList')
          // 添加成功之后关闭窗口
          this.$emit('closeAddLabel')
          // 添加成功之后重置数据
          this.restTemp()
        }
      })


    },

  },


};
</script>



