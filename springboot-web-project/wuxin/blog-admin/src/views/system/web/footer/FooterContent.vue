<template>
  <div>
    <el-form :model="item" :inline="true" :rules="rules" ref="dataForm" size="mini">
      <el-form-item label="类型名" prop="typeName">
        <el-input v-model="item.typeName" />
      </el-form-item>
      <el-form-item label="类型颜色" prop="typeColor">
        <el-select v-model="item.typeColor" placeholder="请选择" style="width: 100px;">
          <el-option v-for="color in colors" :key="color" :label="color" :value="color" />
        </el-select>
      </el-form-item>
      <el-form-item label="名称" prop="name">
        <el-input v-model="item.name" />
      </el-form-item>
      <el-form-item label="颜色" prop="color">
        <el-color-picker v-model="item.color" show-alpha />
      </el-form-item>
      <el-form-item label="地址" prop="url">
        <el-input v-model="item.url" style="width: 200px;" />
      </el-form-item>
      <el-form-item label="介绍" prop="title">
        <el-input v-model="item.title" style="width: 200px;" />
      </el-form-item>
      <el-form-item>
        <el-popconfirm
          confirm-button-text='确认'
          cancel-button-text='取消'
          icon="el-icon-info"
          icon-color="red"
          title="确定删除这个标签吗？··"
          @onCancel="cancel()"
          @onConfirm="deleteItem(item.id)"
        >
          <el-button type="danger" size="mini" slot="reference"
          >删除
          </el-button>
        </el-popconfirm>

        <el-button type="primary" size="mini" @click.prevent="submit(item)" class="m-margin-left-mini">确认</el-button>
      </el-form-item>

    </el-form>
  </div>

</template>
<script>
import {colors} from '@/utils/color'

export default {
  name: 'FooterContent',
  props: ['item'],
  data() {
    return {
      rules: {
        typeName: [
          {message: '请填写类型名', trigger: 'blur'}
        ],
        typeColor: [
          {message: '请填类型颜色', trigger: 'blur'}
        ],
        name: [
          {message: '请填写名称', trigger: 'blur'}
        ],
        color: [
          {message: '颜色不能为空！', trigger: 'blur'}
        ],

        url: [
          {message: '请地址不能为空！', trigger: 'blur'}
        ],
        title: [
          {message: '介绍不能为空', trigger: 'blur'}
        ],

      },
      colors: colors
    }
  },
  methods: {

    deleteItem(id) {
      this.$emit('deleteItem', id)
    },

    updateItem(data) {
      this.$emit('updateItem', data)
    },

    cancel() {
      this.$notify.warning('取消操作了哦')
    },

    submit(data) {
      this.$refs.dataForm.validate(valid => {

        if (valid) {

          if (data.typeName.trim() !== ""
            && data.typeColor !== ""
            && data.name.trim() !== ""
            && data.color.trim() !== ""
            && data.url.trim() !== ""
            && data.title.trim() !== ""
          ) {

          } else {
            this.$message.error("添加失败,标签内容不能为空！")
          }
          this.updateItem(data)
        }
      })
    }
  },

}
</script>
<style scoped>
.el-input {
  width: 100px;
}
</style>
