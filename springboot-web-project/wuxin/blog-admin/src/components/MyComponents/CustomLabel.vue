<template>
  <div>
    <el-form
      ref="labelForm"
      :model="label"
      :rules="rules"
      label-position="left"
      label-width="50px"
      size="small"
    >
      <el-form-item
        label="名称"
        class="m-input-width-80pre"
        prop="name"
      >
        <el-input v-model="label.name" />
      </el-form-item>
      <el-form-item label="颜色" prop="color">
        <el-select
          v-model="label.color"
          placeholder="请选择"
          class="m-input-width-80pre"
        >
          <el-option
            v-for="item in colors"
            :id="item"
            :key="item"
            :value="item"
          >
            <span style="float: left"><el-tag :color="item" class="m-input-width-100" /></span>
            <span style="float: right">{{ item }}</span>
          </el-option>
        </el-select>
      </el-form-item>
      <slot>
        <el-form-item>
          <el-button type="info" size="small" @click="cancel">取消</el-button>
          <el-button type="primary" size="small" @click="createLabel">{{ dialogStatus==='create'?'添加':'修改' }}</el-button>
        </el-form-item>
      </slot>
    </el-form>
  </div>
</template>
<script>
import { colors } from '@/utils/color'
import { createCategory, updateCategory } from '@/api/category'
import { createTag, updateTag } from '@/api/tag'

export default {
  name: 'CustomLabel',
  props: {
    labelName: {
      type: String,
      default: 'category'
    },
    dialogStatus: {
      type: String,
      default: 'create'
    },
    temp: {
      type: Object,
      default: () => {
        return {}
      }
    }
  },

  watch: {
    'temp'() {
      this.label = this.temp
      console.log('watch content')
    }
  },
  data() {
    return {
      colors: colors,
      label: { name: '', color: 'teal' },
      rules: {
        name: [{ required: true, message: '名称不能为空！', trigger: 'blur' }],
        color: [{ required: true, message: '颜色不能为空！', trigger: 'blur' }]
      }
    }
  },
  mounted() {
    this.initData()
  },
  methods: {
    initData() {
      if (this.dialogStatus === 'update') {
        console.log('init')
        this.label = this.temp
      }
    },
    resetTemp() {
      console.log('cancel resetTemp')
      this.label = { 'name': '', 'color': 'teal' }
    },
    createLabel() {
      this.$refs.labelForm.validate(valid => {
        if (valid) {
          if (this.dialogStatus === 'create' && this.labelName === 'category') {
            createCategory(this.label).then(res => {
              if (res.code === 200) {
                this.$message.success('添加成功！')
                this.$emit('addCategorySuccess')
                this.cancel()
              }
            })
          }
          if (this.dialogStatus === 'update' && this.labelName === 'category') {
            updateCategory(this.label).then(res => {
              if (res.code === 200) {
                this.$message.success('修改成功！')
                this.$emit('updateCategorySuccess')
                this.cancel()
              }
            })
          }

          if (this.dialogStatus === 'create' && this.labelName === 'tag') {
            createTag(this.label).then(res => {
              if (res.code === 200) {
                this.$message.success('添加成功！')
                this.$emit('addTagSuccess')
                this.cancel()
              }
            })
          }

          if (this.dialogStatus === 'update' && this.labelName === 'tag') {
            updateTag(this.label).then(res => {
              if (res.code === 200) {
                this.$emit('updateTagSuccess')
                this.$message.success('修改成功！')
                this.cancel()
              }
            })
          }
        }
      })
    },
    cancel() {
      this.resetTemp()
      this.$emit('cancel')
    }
  },
  destroyed() {
    this.$destroy()
  }
}
</script>
<style scoped>
.m-input-width-100 {
  width: 100px !important;
}

.m-input-width-80pre {
  width: 80% !important;
}
</style>
