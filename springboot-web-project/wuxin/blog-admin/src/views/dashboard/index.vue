<template>
  <div class="dashboard-container">
    <el-alert :title="`登录成功！欢迎用户${name}`" type="success" center />
    <RootDashboard />
    <el-row  :gutter="20" style="margin-top: 10px;">
      <el-col :span="8" :xs="24">
        <el-card shadow="hover">
          <CategoryCount />
        </el-card>
      </el-col>
      <el-col :span="8" :xs="24">
        <el-card shadow="hover">
          <TagCount />
        </el-card>
      </el-col>
      <el-col :span="8" :xs="24">
        <el-card shadow="hover">
<!--          <MapCount />-->
          <TagCount />
        </el-card>
      </el-col>
    </el-row>
    <el-card shadow="hover">
      <VisitCount />
    </el-card>
  </div>
</template>

<script>
import {mapGetters} from 'vuex'
import RootDashboard from './components'
import CategoryCount from "@/views/dashboard/components/category";
import TagCount from "@/views/dashboard/components/tag";
import MapCount from "@/views/dashboard/components/map";
import VisitCount from "@/views/dashboard/components/visit";

export default {
  name: 'Dashboard',
  components: {VisitCount, MapCount, TagCount, CategoryCount, RootDashboard},

  computed: {
    ...mapGetters(['name', 'avatar', 'roles'])
  },
  created() {

    // this.$store.dispatch('user/getInfo')
    //   .then(res=>{
    //   console.log("获取登录的用户信息=》"+JSON.stringify(res.result))
    // })



  }
}
</script>
