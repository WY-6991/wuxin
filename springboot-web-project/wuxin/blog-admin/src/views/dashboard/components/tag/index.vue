<template>
  <div class="tag-echarts-count m-margin-tb-large m-echarts-container"></div>
</template>
<script>

import {getDashboardTag} from "@/api/dashboard";

export default {
  name: 'CategoryCount',
  data() {
    return {
      tagList: []
    }
  },
  methods: {
    initData() {
      let myChart = this.$echarts.init(document.querySelector('.tag-echarts-count'));
      let data;
      getDashboardTag().then(res => {
        if (res.code === 200) {
          data = res.result

        }
      })
      let option = {
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
            data: this.tagList,
          }
        ]
      };
      myChart.setOption(option);
    },

    getData(){
      getDashboardTag().then(res=>{
        if(res.code=== 200){
          console.log('res=.',JSON.stringify(res))
          this.tagList = res.result
          this.$nextTick(()=>{
            this.initData();
          })
        }
      })
    }

  },
  mounted() {
    this.getData()
  }

}
</script>
