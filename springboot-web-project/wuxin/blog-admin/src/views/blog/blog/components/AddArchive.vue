<template>
  <div>
    <el-form
      ref="dataForm"
      :model="archive"
      label-position="left"
      label-width="70px"
      style="width: 400px; margin-left:50px;"
    >

      <el-form-item
        label="标题"
        :rules="[
          {required:true,message:'标题名称不能为空！',trigger:'blur'},
        ]"
      >
        <el-input v-model="archive.title" disabled />
      </el-form-item>

      <el-form-item label="类型">
        <el-radio-group v-model="archive.type">
          <el-radio :label="1">原创</el-radio>
          <el-radio :label="2" :disabled="true">转载</el-radio>
        </el-radio-group>
      </el-form-item>

      <el-form-item>
        <el-button @click="cancelArchive">返回</el-button>
        <el-button type="primary" @click.prevent="addArchive">确认</el-button>
      </el-form-item>
    </el-form>

  </div>
</template>
<script>
export default {
  name: 'AddArchive',
  props: ['archive'],
  methods: {
    addArchive() {
      this.$emit('addArchive', this.archive)
    },
    cancelArchive() {
      this.$emit('cancelArchive')
    }
  }

}
</script>
<style scoped>

</style>
