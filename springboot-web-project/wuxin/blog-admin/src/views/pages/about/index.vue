<template>
  <div class="app-container">
    <el-button
      style="position: fixed;right: 10px;top: 200px;z-index: 999"
      class="m-margin-left-small"
      type="primary"
      size="small"
      icon="el-icon-check"
      @click="updateData"
    >
      确认
    </el-button>

    <!-- 内容 -->
    <el-form ref="dataForm" class="form-container">
      <el-form-item>
        <el-switch
          v-model="about.commentEnabled"
          @change="updateData"
          active-text="开启评论"
          inactive-text="关闭评论">
        </el-switch>
      </el-form-item>

      <el-form-item>
        <div id="vditor-content"></div>
      </el-form-item>
    </el-form>

  </div>
</template>

<script>


import {mapGetters} from "vuex";
import {createVditor} from "@/plugins/CreateVditor";
import {findAbout, updateAbout} from "@/api/about";


export default {
  name: "About",
  data() {
    return {
      contentVditor: '',
      about: {
        content: '',
        name: '',
        commentEnabled: true
      },
      msg: '关于'
    };
  },
  methods: {


    initVditor() {
      this.contentVditor = createVditor('vditor-content',  500, false)
    },

    //添加数据
    updateData() {
      if (!this.isRoot) {
        this.$message.error('操作失败，不具备操作权限！')
        return;
      }
      this.about.content = this.contentVditor.getHTML()
      updateAbout(this.about).then(res => {
        if (res.code === 200) {
          this.$notify.success('修改成功！')
        }

      })

    },

    getAboutData() {
      findAbout().then(res => {
        if (res.code === 200) {
          const {content, commentEnabled} = res.result
          this.about.commentEnabled = commentEnabled
          this.about.content = this.contentVditor.html2md(content)
          this.contentVditor.setValue(this.about.content)
        }

      })
    }


  },
  created() {
    if (this.about.content === '') {
      this.getAboutData()
    }

  },
  mounted() {
    this.initVditor()
  },


};
</script>

