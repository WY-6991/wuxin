<template>
  <div
    ref="category"
    class=" m-margin-tb-large m-echarts-container"
  />
</template>
<script>
import { getDashboardCategory } from '@/api/dashboard'

export default {
  name: 'CategoryMap',
  data() {
    return {
      categoryList: [],
      mychart: null
    }
  },
  mounted() {
    this.getData()
  },
  methods: {
    initData() {
      this.mychart = this.$echarts.init(this.$refs.category)
      const option = {
        title: {
          text: '分类下的文章',
          subtext: 'category',
          left: 'center'
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          bottom: 'bottom'
        },
        toolbox: {
          feature: {
            saveAsImage: {}
          }
        },
        series: [
          {
            name: '分类',
            type: 'pie',
            radius: '50%',
            data: this.categoryList,
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      }
      this.mychart.setOption(option)
    },

    getData() {
      getDashboardCategory().then((res) => {
        if (res.code === 200) {
          this.categoryList = res.result
          this.$nextTick(() => {
            this.initData()
          })
        }
      })
    }
  }
}
</script>
