<template>
  <el-table
    v-loading="listLoading"
    :data="list"
    fit
    highlight-current-row
    class="m-table"
    size="small"
    max-height="350"
  >
    <el-table-column align="center" label="序号" width="100px">
      <template slot-scope="{ row,$index }">
        <span>{{ $index + 1 }}</span>
      </template>
    </el-table-column>

    <el-table-column width="180" align="center" label="名称" prop="name" />

    <el-table-column align="center" label="颜色">
      <template slot-scope="{ row }">
        <el-tag :color="row.color" size="medium " />
      </template>
    </el-table-column>

    <el-table-column align="center" label="选项" prop="color">
      <template slot-scope="{row}">
        <el-select v-model="row.color" placeholder="请选择" style="width: 230px" @change="updateColor(row)">
          <el-option v-for="(item) in colors" :id="item" :key="item" :value="item">
            <span style="float:left;"><el-tag :color="item" /></span>
            <span style="float:right;">{{ item }}</span>
          </el-option>
        </el-select>
      </template>
    </el-table-column>
    <el-table-column align="center" label="操作" width="200">
      <template slot-scope="{ row ,$index}">
        <el-button type="primary" size="mini" icon="el-icon-edit" @click="handleUpdate(row)">编辑</el-button>
        <el-button type="danger" size="mini" icon="el-icon-delete" @click="deleteData(row,$index)">删除</el-button>
      </template>
    </el-table-column>
  </el-table>
</template>
<script>
import { colors } from '@/utils/color'

export default {
  name: 'LabelTableList',
  props: {
    list: {
      type: Array,
      default: () => {
        return []
      }
    },
    listLoading: {
      type: Boolean,
      default: () => {
        return false
      }
    }
  },
  data() {
    return {
      colors: colors
    }
  },
  methods: {
    deleteData(row, index) {
      this.$emit('deleteData', { 'data': row, 'index': index })
    },
    handleUpdate(obj) {
      this.$emit('handleUpdate', obj)
    },
    updateColor(obj) {
      this.$emit('updateColor', obj)
    }
  }

}
</script>
<style scoped>
.edit-input {
  padding-right: 100px;
}

.cancel-btn {
  position: absolute;
  right: 15px;
  top: 10px;
}

.el-tag {
  color: #ffffff;
  width: 100px !important;
}
</style>
