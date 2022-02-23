<template>
  <div ref="tag" class=" m-margin-tb-large m-echarts-container" />
</template>
<script>
import { getDashboardTag } from '@/api/dashboard'

export default {
  name: 'TagMap',
  data() {
    return {
      tagList: [],
      myChart: null
    }
  },
  mounted() {
    this.getData()
  },
  methods: {
    initData() {
      this.myChart = this.$echarts.init(this.$refs.tag)
      const option = {
        title: {
          text: '标签下的文章',
          subtext: 'tag',
          left: 'center'
        },
        legend: {
          top: 'bottom'
        },
        tooltip: {
          trigger: 'item'
        },
        toolbox: {
          feature: {
            saveAsImage: {}
          }
        },

        series: [
          {
            name: '标签分类下的文章',
            type: 'pie',
            radius: [50, 80],
            center: ['50%', '50%'],
            roseType: 'area',
            itemStyle: {
              borderRadius: 8
            },
            data: this.tagList
          }
        ]
      }
      this.myChart.setOption(option)
    },

    getData() {
      getDashboardTag().then((res) => {
        if (res.code === 200) {
          this.tagList = res.result
          this.$nextTick(() => {
            this.initData()
          })
        }
      })
    }
  }
}
</script>
