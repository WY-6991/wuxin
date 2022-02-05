<template>
  <!-- 表格 -->
  <div class="app-container">

    <MySearchHeader :show-create-button="false" @handleSearch="handleFilter"></MySearchHeader>


    <el-table
      v-loading="listLoading"
      :data="userList"
      fit
      highlight-current-row
      class="m-table"
      max-height="350"
    >
      <el-table-column
        label="序号"
        prop="id"
        align="center"
        width="100"
      >
        <template slot-scope="{ row,$index }">
          <span>{{ $index + 1 }}</span>
        </template>
      </el-table-column>

      <el-table-column label="用户昵称" width="200" align="center">
        <template slot-scope="{ row }">
          <MyUserLink :username="row.nickname" />
        </template>
      </el-table-column>

      <el-table-column label="头像" width="100" align="center">
        <template slot-scope="{ row }">
          <MyImage :image="row.avatar" />
        </template>
      </el-table-column>

      <el-table-column label="邮箱" align="center">
        <template slot-scope="{ row }">
          <span class="link-type" @click="handleUpdateUser(row)">{{
              row.email
            }}</span>
        </template>
      </el-table-column>

      <el-table-column label="注册日期" width="200" align="center">
        <template slot-scope="{ row }">
          <span>{{ row.createTime }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        align="center"
      >
        <template slot-scope="{ row, $index }">
          <el-button
            type="success"
            icon="el-icon-user"
            circle
          />
          <el-button
            type="primary"
            icon="el-icon-edit"
            circle
            @click="handleUpdateUser(row)"
          />

          <el-button
            type="danger"
            icon="el-icon-delete"
            circle
            @click="del(row,$index)"
          />
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

    <!-- 对话框 -->
    <el-dialog title="编辑用户信息" :visible.sync="dialogFormVisible">
      <UserEdit :temp="temp" @cancelEdit="cancelEdit" @getList="getList" />
    </el-dialog>

  </div>
</template>

<script>
import UserEdit from "@/views/user/component/UserEdit";
import MyImage from "@/components/MyComponents/MyImage";
import MyUserLink from "@/components/MyComponents/MyUsernameLink";
import {delUser, updateUser, getUserList} from "@/api/user";
import {query} from "@/mixin/query";

export default {
  name: "UserList",
  mixins:[query],
  components: {MyUserLink, MyImage, UserEdit},
  data() {
    return {
      userList: [],
      total: 0,
      listLoading: true,
      dialogFormVisible: false,
      temp: {},
    };
  },
  mounted() {
    this.getList();
  },
  methods: {
    getList() {
      this.listLoading = true
      getUserList(this.query).then(res => {
        this.userList = res.result.records
        this.total = res.result.total
      })

      this.listLoading = false

    },

    update() {
      this.$message.success("修改用户操作还未上线哦！")
    },


    //修改用户信息处理
    handleUpdateUser(user) {
      this.temp = user // 将用户信息赋值给对话框
      this.dialogFormVisible = true;  // 显示对话框
    },

    cancelEdit() {
      this.dialogFormVisible = false
    },

    del(user, index) {
      delUser(user.userId).then(res => {
        if (res.code === 200) {
          this.$message.success("删除成功！")
          this.userList.splice(index, 1)
        }
      })
    },


  }
};
</script>
