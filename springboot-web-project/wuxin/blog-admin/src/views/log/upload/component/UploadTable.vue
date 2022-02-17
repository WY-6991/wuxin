<template>
  <div class="app-container">
    <el-table
      style="width: 100%"
      :fit="!hasRoot"
      max-height="350"
      :data="list">
      <el-table-column label="序号" width="50" align="center" :fixed="hasRoot?'left':''">
        <template slot-scope="{row,$index}">
          {{ $index + 1 }}
        </template>
      </el-table-column>
      <el-table-column label="文件名" width="100" align="center" v-if="hasRoot">
        <template slot-scope="{row}">
          <span class="m-message"> {{ row.name }}</span>
        </template>
      </el-table-column>

      <el-table-column label="访问地址" align="center" min-width="200">
        <template slot-scope="{row}">
          <span class="m-message">
            <el-link :href="row.url" target="_blank">{{ row.url }}</el-link>
          </span>
        </template>
      </el-table-column>

      <el-table-column label="上传地址"  align="center" min-width="200" v-if="hasRoot">
        <template slot-scope="{row}">
          <span class="m-message"> {{ row.realUrl }}</span>
        </template>
      </el-table-column>

      <el-table-column label="消息" width="160" align="center" v-if="hasRoot">
        <template slot-scope="{row}">
          <span class="m-message"> {{ row.message }}</span>
        </template>
      </el-table-column>
      <el-table-column label="sha" width="200" align="center" v-if="hasRoot">
        <template slot-scope="{row}">
          <span class="m-message"> {{ row.sha }}</span>
        </template>
      </el-table-column>
      <el-table-column label="gitUrl" width="200" align="center" v-if="hasRoot">
        <template slot-scope="{row}">
          <span class="m-message"> {{ row.gitUrl }}</span>
        </template>
      </el-table-column>
      <el-table-column label="日期" width="160" align="center">
        <template slot-scope="{row}">
          {{ row.createTime }}
        </template>
      </el-table-column>
      <el-table-column label="操作" width="150" align="center" :fixed="hasRoot?'right':null">
        <template slot-scope="{row}">
          <el-button type="primary" size="small" icon="el-icon-view" @click="showForm(row)"></el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog :visible.sync="visiableDialog" class="text-center">
      <el-image :src="log.url">
        <div slot="error">图片加载失败</div>
      </el-image>
      <div slot="footer">
        <el-button type="primary" size="small" @click="visiableDialog=false">关闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>

import { minix } from './minix'
export default {
  name: 'UploadImageTable',
  mixins: [minix],
  data() {
    return {
      visiableDialog: false,
      log: {}
    }
  },
  methods: {

    showForm(log) {
      this.log = log
      this.visiableDialog = true
    }
  }
}
</script>
<style scoped>

</style>
