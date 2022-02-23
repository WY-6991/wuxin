<template>
  <div class="dashboard-container">
    <el-alert :title="`登录成功！欢迎用户${name}`" type="success" center />
    <PanelGroup />
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
          <VisitorMap />
        </el-card>
      </el-col>
    </el-row>
    <el-card shadow="hover">
      <VisitorCount />
    </el-card>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import PanelGroup from '@/views/dashboard/components/PanelGroup'
import CategoryMap from '@/views/dashboard/components/category'
import TagMap from '@/views/dashboard/components/tag'
import VisitorMap from '@/views/dashboard/components/visitor_map'
import VisitorCount from '@/views/dashboard/components/visit'

export default {
  name: 'Dashboard',
  components: { VisitorCount, VisitorMap, TagMap, CategoryMap, PanelGroup },
  computed: {
    ...mapGetters(['name', 'avatar', 'roles'])
  }
}
</script>
