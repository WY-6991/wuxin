<template>
  <div class="ui attached segment " :style="bgColor">
    <Title :title="`共发布了${total}个动态`" />{{ res }}
    <MomentsList :momentsList="momentsList" :getList="getList" :totalPage="totalPage" />
  </div>
</template>

<script>
import Title from "@/components/common/Title";
import MomentsList from "@/components/moments/MomentsList";
import {getMomentList} from "@/api/moment";
import {formatLink} from "@/utils/link";

export default {
  name: "Moments",
  components: {MomentsList, Title},
  data() {
    return {
      momentsList: [],
      current: 1,
      limit: 5,
      totalPage: 0,
      total: 0
    }

  },
  methods: {
    getList(current) {
      getMomentList(current, this.limit).then(res => {
        if (res.code === 200) {
          const {records, pages, total} = res.result

          records.forEach(item => {
            item.content = formatLink(item.content)
          })
          console.log(records)
          this.momentsList = records
          this.total = total
          this.totalPage = pages
          // 加载插件
          this.$nextTick(() => {
            this.$primsjs.highlightAll()
          })
        }

      })

    }
  },
  mounted() {
    this.getList(this.current)
  }

};
</script>
