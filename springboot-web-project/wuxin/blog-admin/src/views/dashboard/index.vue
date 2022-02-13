<template>
  <div class="dashboard-container">
    <el-alert :title="`登录成功！欢迎用户${name}`" type="success" center />
    <RootDashboard />
    <el-row :gutter="20" style="margin-top: 10px">
      <el-col :span="8" :xs="24">
        <el-card shadow="hover">
          <CategoryMap />
        </el-card>
      </el-col>
      <el-col :span="8" :xs="24">
        <el-card shadow="hover">
          <TagMap />
        </el-card>
      </el-col>
      <el-col :span="8" :xs="24">
        <el-card shadow="hover">
          <TagMap />
        </el-card>
      </el-col>
    </el-row>
    <el-card shadow="hover">
      <VisitorMap />
    </el-card>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import RootDashboard from "./components";
import CategoryMap from "@/views/dashboard/components/category";
import TagMap from "@/views/dashboard/components/tag";
import MapCount from "@/views/dashboard/components/map";
import VisitorMap from "@/views/dashboard/components/visit";

export default {
  name: "Dashboard",
  components: { VisitorMap, MapCount, TagMap, CategoryMap, RootDashboard },

  computed: {
    ...mapGetters(["name", "avatar", "roles"]),
  },
};
</script>
