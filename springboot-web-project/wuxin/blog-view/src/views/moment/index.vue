<template>
  <div class="ui attached segment " :style="$MyBg()" >
    <Title :title="`共发布了${total}个动态`" />{{ res }}
    <MomentsList :momentsList="momentsList" :getList="getList" :totalPage="totalPage" />
  </div>
</template>

<script>
import Title from "@/components/common/Title";
import MomentsList from "@/components/moments/MomentsList";
import {getMomentList} from "@/api/moment";
import {setTotalPage} from '@/utils/validate.js';
import Prism from 'prismjs'

export default {
  name: "Moments",
  components: {MomentsList, Title},
  data() {
    return {
      momentsList: [],
      current: 1,
      limit: 10,
      totalPage: 0,
      total: 0

    }

  },
  methods: {
    getList(current) {
      getMomentList(current, 5).then(res => {
        if (res.code === 200) {
          const {records, total, size} = res.result
          this.momentsList = records
          this.total = total
          this.totalPage = setTotalPage(total, size)
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
