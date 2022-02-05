<template>
  <div>
    <ul class="list-group list-group-striped">
      <li class="list-group-item">
        <el-row :gutter="gutter">
          <el-col :span="icon">
            <el-icon name="user"></el-icon>
          </el-col>
          <el-col :span="info">
            <div class="pull-right" >{{ user.nickname }}</div>
          </el-col>
        </el-row>
      </li>
      <li class="list-group-item">
        <el-row :gutter="gutter">
          <el-col :span="icon">
            <el-icon name="message"></el-icon>
          </el-col>
          <el-col :span="info">
            <div class="pull-right" >{{ user.email }}</div>
          </el-col>
        </el-row>
      </li>
      <li class="list-group-item">
        <el-row :gutter="gutter">
          <el-col :span="icon">
            <el-icon name="phone"></el-icon>
          </el-col>
          <el-col :span="info">
            <div class="pull-right" >{{ user.phone }}</div>
          </el-col>
        </el-row>
      </li>
      <li class="list-group-item">
        <el-row :gutter="gutter">
          <el-col :span="icon">
            <el-icon name="s-opportunity"></el-icon>
          </el-col>
          <el-col :span="info">
            <div class="pull-right">{{ user.motto }}</div>
          </el-col>
        </el-row>
      </li>
      <li class="list-group-item ">
        <el-button type="text" icon="el-icon-edit" @click.prevent="dialogFormVisible=!dialogFormVisible"></el-button>
      </li>

    </ul>

    <el-dialog >

    </el-dialog>

    <el-dialog title="编辑信息" :visible.sync="dialogFormVisible"  >
      <UserEdit :temp="user" @cancelEdit="dialogFormVisible=false"></UserEdit>
    </el-dialog>

  </div>

</template>
<script>
import {validPhone, validEmail} from "@/utils/validate";
import {updateUser} from "@/api/user";
import UserEdit from "@/components/MyComponents/UserEdit";

export default {
  name: 'EditUserInfo',
  components: {UserEdit},
  data() {
    return {
      gutter: 10,
      icon: 3,
      info: 21,
      editMode: false,
      dialogFormVisible:false
    }
  },
  props: ['user'],
  methods: {
    editData() {
      if (this.editMode) {
        this.$confirm('确认修改信息?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.editMode = false
          this.validData(this.user)
        }).catch(() => {
          this.editMode = false
          this.$message({
            type: 'info',
            message: '已取消'
          });
        });

      } else {
        this.editMode = true
      }
    },

    validData(user) {
      if (user.nickname.length < 2 || user.nickname > 15) {
        this.$message.error('昵称为2~15个字符！')
        return false
      }
      if (!validEmail(user.email)) {
        this.$message.error('邮箱格式不正确！')
        return false
      }
      if (user.phone !== ''||typeof(this.user.phone)!=='undefined') {
        if (!validPhone(user.phone) ) {
          this.$message.error('手机号格式不正确！')
          return false
        }
      }
      updateUser(user).then(res => {
        if (res.code === 200) {
          this.$message.success('修改成功！')
        }
      })
    }
  },
  created() {
    console.log('手机号验证=》'+JSON.stringify(this.user))
    const reg = /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/
    console.log('手机号验证结果=>'+reg.test(this.user.phone),)
  }

}
</script>

<style scoped>
.list-group-item {
  list-style: none !important;
  margin: 20px 0 !important;
  font-size: 14px;
}

.pull-right {
  right: 0;
}

</style>
