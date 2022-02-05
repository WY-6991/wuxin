<template>
  <div class="app-container">
    <div class="filter-container">
      <el-button type="primary" size="mini" icon="el-icon-plus" class="m-margin-left-small" @click="NoUpdate">添加</el-button>
    </div>
    <el-table
      :key="tableKey"
      v-loading="listLoading"
      :data="roleList"
      fit
      highlight-current-row
      class="m-table"
      max-height="350"
    >
      <el-table-column
        label="序号"
        prop="id"
        sortable="custom"
        align="center"
        width="160"
      >
        <template slot-scope="{ row,$index }">
          <span>{{ $index +1 }}</span>
        </template>
      </el-table-column>

      <el-table-column label="名称"  align="center" >
        <template slot-scope="{ row }">
          <span>{{ row.roleName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="描述"  align="center" >
        <template slot-scope="{ row }">
          <span>{{ row.description }}</span>
        </template>
      </el-table-column>

      <el-table-column
        label="操作"
        align="center"
        width="250"
      >
        <template slot-scope="{ row, $index }">
          <el-button type="primary" icon="el-icon-edit" size="mini" @click="NoUpdate">编辑</el-button>
          <el-button type="danger" icon="el-icon-delete" size="mini" @click="NoUpdate">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <MyPagination
      v-show="total > 0"
      :total="total"
      :page.sync="query.current"
      :limit.sync="query.limit"
      @pagination="roleListPage"
    />

  </div>
</template>

<script>

import {getRoleList} from "@/api/role";
export default {
  name: "RoleList",
  data() {
    return {
      roleList: [],
      tableKey: 0,
      total: 0,
      listLoading: true,
      query: {
        current: 1,
        limit: 10,
      },

      dialogFormVisible: false,
      downloadLoading: false,

    };
  },
  created() {
    this.roleListPage();
  },
  methods: {


    roleListPage() {
      this.listLoading = true
      getRoleList(this.query).then(res => {
        this.roleList = res.result.records
        this.total = res.result.total
      })

      this.listLoading = false

    },
    NoUpdate(){
      this.$message.warning('该功能还未上线,暂不支持修改！')
    },









  }
};
</script>
