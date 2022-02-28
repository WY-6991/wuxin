<template>
  <div class="app-container">
    <div class="add-blog">
      <!-- 发布 -->
      <el-button
        class="m-margin-left-small"
        type="primary"
        icon="el-icon-edit"
        @click="updateData"
      >
        修改
      </el-button>
    </div>

    <!-- 内容 -->
    <el-form  class="form-container">
      <el-col :span="24">
        <el-form-item>
          <div id="vditor-content"></div>
        </el-form-item>
      </el-col>
    </el-form>

  </div>
</template>

<script>
import {mapGetters} from "vuex";
import {createVditor} from "@/plugins/CreateVditor";
import {updateMoment, detailMoment} from "@/api/moment";
export default {
  name: "Friend",
  data() {
    return {
      contentVditor: '',
      msg: '动态修改',
      moment: '',
      content: '',
    };
  },
  computed: {
    ...mapGetters(['token'])
  },
  mounted() {
    // 加载编辑器
    this.initVditor()
    this.getData()
  },
  methods: {
    initVditor() {
      this.contentVditor = createVditor('vditor-content', 500, false)
    },
    getData() {
      detailMoment(this.$route.params.momentId).then(res => {
        this.moment = res.result
        this.content = this.contentVditor.html2md(res.result.content)
        this.contentVditor.setValue(this.content)
      })
    },
    updateData: function () {
      this.moment.content = this.contentVditor.getHTML()
      updateMoment(this.moment).then(res => {
        this.$notify.success('修改成功!')
      })
    }
  }
}
</script>

<style scoped>

.add-blog {
  display: flex;
  position: fixed;
  right: 0;
  z-index: 2000;
  margin-right: 20px;
}
</style>
