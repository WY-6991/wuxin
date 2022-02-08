<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="12" :xs="24">
        <BasicSetting :system-info="systemInfo" />
      </el-col>
      <el-col :span="12" :xs="24">
        <OtherSetting :system-info="systemInfo" />
      </el-col>
    </el-row>
    <WebFooter :footer-label="label" />
  </div>
</template>

<script>
import WebFooter from '@/views/system/web/footer/index.vue'
import OtherSetting from '@/views/system/web/system-info/OtherSetting'
import BasicSetting from '@/views/system/web/system-info/BasicSetting'
import { getMySystemInfo } from '@/api/system'

export default {
  name: 'MyWeb',
  components: { BasicSetting, OtherSetting, WebFooter },
  data() {
    return {
      systemInfo: {},
      label: []
    }
  },

  computed: {
    // ...mapGetters(['systemInfo'])
  },

  mounted() {
    this.getData()
  },
  methods: {
    // ...mapActions('system', ['getMySystemInfo']),

    getData() {
      getMySystemInfo().then(res => {
        if (res.code === 200) {
          const { label, system } = res
          this.label = label
          this.systemInfo = system
        }
      })
    }
  }
}
</script>
<style scoped>

</style>
