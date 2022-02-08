<template>
  <div class="update-question-container" :style="bgColor">
    <div class="ui input" style="float: right;z-index: 100" >
      <input type="text" placeholder="Search..." v-model="keywords">
    </div>
    <el-timeline>
      <el-timeline-item v-for="(item, index) in updates" :style="bgColor" :key="`update-${index}`" :color="item.color"
                        :timestamp="new Date(item.createTime).getTime() | formatDate" size="large">
        {{ item.content }}
      </el-timeline-item>
    </el-timeline>
  </div>
</template>

<script>
import dateFormat from "@/utils/date.js";

export default {
  name: "UpdateQuestionList",
  props: ["updateQuestionList"],

  data() {
    return {
      keywords: ''
    }
  },
  filters: {
    formatDate(time) {
      let date = new Date(time);
      return dateFormat.formatDate(date, "yyyy-MM-dd");
    },
  },
  computed: {
    updates() {
      return this.updateQuestionList.filter((item, index) => {
        let time = item.createTime.indexOf(this.keywords) !== -1
        let content = item.content.indexOf(this.keywords) !== -1
        return time || content;
      })
    }
  },
};
</script>

<style scoped>
.update-question-container {
  padding: 0 20px 0 10px;
  min-height: 100vh;
}
</style>
