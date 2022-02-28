<template>
  <!-- 表格 -->
  <div class="app-container">
    <div class="filter-container">
      <el-input
        v-model="query.keywords"
        placeholder="请输入用户名..."
        style="width: 400px;"
        @keyup.enter.native="handleFilter"
      >
        <el-select v-model="query.id" slot="prepend" placeholder="分类" style="width: 120px" @change="handleFilter()">
          <el-option :value="null" label="全部"></el-option>
          <el-option :value="1" label="普通用户"></el-option>
          <el-option :value="2" label="管理员"></el-option>
          <el-option :value="3" label="超级管理员"></el-option>
        </el-select>
        <el-button slot="append" type="primary" icon="el-icon-search" @click="handleFilter">搜索</el-button>

      </el-input>
    </div>


    <el-table
      :key="tableKey"
      v-loading="listLoading"
      :data="userList"
      fit
      highlight-current-row
      class="m-table"
      max-height="350"
    >
      <el-table-column label="序号" prop="id" sortable="custom" align="center" width="160">
        <template slot-scope="{ row,$index }">
          <span>{{ $index + 1 }}</span>
        </template>
      </el-table-column>
      <el-table-column label="昵称" align="center" width="150">
        <template slot-scope="{ row }">
          <MyUserLink :username="row.nickname" />
        </template>
      </el-table-column>
      <el-table-column label="头像" align="center" width="150">
        <template slot-scope="{ row }">
          <MyImage :image="row.avatar" />
        </template>
      </el-table-column>
      <el-table-column label="级别" align="center">
        <template slot-scope="{ row }">
          <el-tag v-if="row.roleId===1" type="warning">普通用户</el-tag>
          <el-tag v-else-if="row.roleId===2" type="success">管理员</el-tag>
          <el-tag v-else-if="row.roleId===3" type="danger">超级管理员</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="200">
        <template slot-scope="{ row }">
          <el-select v-model="row.roleId" @change="updateRole(row)">
            <el-option :value="1" label="普通用户"></el-option>
            <el-option :value="2" label="管理员"></el-option>
            <el-option :value="3" label="超级管理员"></el-option>
          </el-select>
        </template>


      </el-table-column>


    </el-table>

    <!-- 分页 -->
    <MyPagination
      v-show="total > 0"
      :total="total"
      :page.sync="query.current"
      :limit.sync="query.limit"
      @pagination="getList"
    />


  </div>
</template>

<script>
import {getUserRoleList, updateUserRole} from "@/api/role";
import MyImage from "@/components/MyComponents/MyImage";
import MyUserLink from "@/components/MyComponents/MyUsernameLink";


export default {
  name: "UserList",
  components: {MyUserLink, MyImage},

  filters: {
    userRoleName(roleId) {
      for (let roleListElement of roleList) {
        console.log('roleListElement', roleListElement)
        if (Number(roleId) === Number(roleListElement.roleId)) {
          return roleListElement.roleName
        }
      }
    }
  },

  data() {

    return {
      userList: [],
      tableKey: 0,
      total: 0,
      listLoading: true,
      query: {
        current: 1,
        limit: 10,
        keywords: null,
        id: null,
      },
      role: 3,
      temp: {
        userId: '',
        username: '',
        nickName: '',
        roleId: '',
        avatar: '',
      },
      dialogFormVisible: false,
      downloadLoading: false,


    };
  },

  created() {
    this.getList();
  },
  methods: {
    getList() {
      this.listLoading = true
      getUserRoleList(this.query).then(res => {
        this.userList = res.result.records
        this.total = res.result.total
      })
      this.listLoading = false

    },

    //搜索用户信息
    handleFilter() {
      this.query.current = 1;
      this.query.limit = 10;
      this.getList();
    },

    //修改用户信息
    updateRole(user) {
      updateUserRole(user).then(res => {
        if (res.code === 200) {
          this.$message.success('修改成功！')
        }
      })
    },


  }
};
</script>

