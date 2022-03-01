<template>
  <div>
    <el-form
      ref="labelForm"
      :model="temp"
      label-position="left"
      label-width="50px"
      size="small"
    >
      <el-form-item
        label="名称"
        class="m-input-width-80pre"
        :rules="[
          { required: true, message: '名称不能为空！', trigger: 'blur' },
        ]"
      >
        <el-input v-model="temp.name" @input="dataChange()" />
      </el-form-item>
      <el-form-item
        label="颜色"
        :rules="[
          { required: true, message: '选择一个颜色！', trigger: 'blur' },
        ]"
      >
        <el-select
          v-model="temp.color"
          placeholder="请选择"
          class="m-input-width-80pre"
        >
          <el-option
            v-for="item in colors"
            :id="item"
            :key="item"
            :value="item"
            @change="dataChange"
          >
            <span style="float: left"><el-tag :color="item" class="m-input-width-100" /></span>
            <span style="float: right">{{ item }}</span>
          </el-option>
        </el-select>
      </el-form-item>
      <slot />
    </el-form>
  </div>
</template>
<script>
import { colors } from '@/utils/color'

export default {
  name: 'MyLabel',
  props: {
    temp: {
      type: Object,
      default: () => {
        return { name: '', color: 'teal' }
      }
    },
    dialogStatus: {
      type: String,
      default: 'create'
    }
  },
  data() {
    return {
      colors: colors,
      rules: {
        name: [{ required: true, message: '名称不能为空！', trigger: 'blur' }],
        color: [{ required: true, message: '颜色不能为空！', trigger: 'blur' }]
      }
    }
  },
  methods: {
    dataChange() {
      this.$refs.labelForm.validate((valid) => {
        if (valid) {
          this.$emit('dataChange', this.temp)
        }
      })
    }
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
