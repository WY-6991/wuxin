<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="12" :xs="24">
        <BasicSetting :systemInfo="systemInfo"></BasicSetting>
      </el-col>
      <el-col :span="12" :xs="24">
        <OtherSetting :systemInfo="systemInfo"></OtherSetting>
      </el-col>
    </el-row>
    <WebFooter />
  </div>
</template>

<script>
import WebFooter from "@/views/system/web/footer/index.vue";
import {mapActions, mapGetters} from "vuex";
import OtherSetting from "@/views/system/web/system-info/OtherSetting";
import BasicSetting from "@/views/system/web/system-info/BasicSetting";
import {getMySystemInfo} from "@/api/system";

export default {
  name: 'MyWeb',
  components: {BasicSetting, OtherSetting, WebFooter},
  data() {
    return {
      systemInfo: {}
    }
  },

  computed: {
    // ...mapGetters(['systemInfo'])
  },
  methods: {
    // ...mapActions('system', ['getMySystemInfo']),

    getData() {
      getMySystemInfo().then(res => {
        if (res.code === 200) {
          this.systemInfo = res.result
        }
      })
    }
  },


  mounted() {
    this.getData()
  }
}
</script>
<style scoped>

</style>
