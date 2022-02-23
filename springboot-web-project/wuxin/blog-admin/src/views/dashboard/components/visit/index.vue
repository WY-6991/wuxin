<template>
  <div class="app-container">
    <div class="visit-echarts-count" style="width: 100%; height: 400px" />
  </div>
</template>
<script>
import { getAccessLoginCount } from '@/api/dashboard'
export default {
  name: 'VisitorCount',
  data() {
    return {
      list: [],
      dateList: [],
      accessList: [],
      loginList: []
    }
  },
  mounted() {
    this.getData()
  },
  methods: {
    initData() {
      const chartDom = document.querySelector('.visit-echarts-count')
      const myChart = this.$echarts.init(chartDom)
      const _this = this
      const option = {
        title: {
          text: '访问统计'
        },
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          data: ['Email', 'Union Ads', 'Video Ads', 'Direct', 'Search Engine']
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        toolbox: {
          feature: {
            saveAsImage: {}
          }
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: _this.dateList
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            name: '访问',
            type: 'line',
            stack: 'Total',
            data: _this.accessList
          },
          {
            name: '登录',
            type: 'line',
            stack: 'Total',
            data: _this.loginList
          }
        ]
      }
      myChart.setOption(option)
    },

    getData() {
      getAccessLoginCount().then((res) => {
        if (res.code === 200) {
          this.list = res.result
          this.list.forEach((map) => {
            this.dateList.push(map['date'])
            this.accessList.push(map['access'])
            this.loginList.push(map['login'])
          })
          this.$nextTick(() => {
            this.initData()
          })
        }
      })
    }
  }
}
</script>
<style scoped>
.app-container {
  width: 100%;
  height: 100%;
}
</style>
