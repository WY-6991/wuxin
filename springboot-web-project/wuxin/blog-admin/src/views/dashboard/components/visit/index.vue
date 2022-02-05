<template>
  <div class="app-container">
    <h2>访问统计</h2>
    <div class="visit-echarts-count" style="width: 100%;height:400px;"></div>
  </div>
</template>
<script>

export default {
  name:'VisitCount',
  methods: {
    initData() {
      let chartDom = document.querySelector('.visit-echarts-count');
      let myChart = this.$echarts.init(chartDom);
      const
        option = {
          title: {
            text: 'Stacked Line'
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
            data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
          },
          yAxis: {
            type: 'value'
          },
          series: [
            {
              name: '访问',
              type: 'line',
              stack: 'Total',
              data: [120, 132, 101, 134, 90, 230, 210]
            },
            {
              name: '登录',
              type: 'line',
              stack: 'Total',
              data: [220, 182, 191, 234, 290, 330, 310]
            },


          ]
        };
      myChart.setOption(option);
    }
  },
  mounted(){
    this.initData();
  },

}
</script>
<style scoped>

.app-container{
  width: 100%;
  height: 100%;
}

</style>
