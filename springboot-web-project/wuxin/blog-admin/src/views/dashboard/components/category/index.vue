<template>
  <div
    class="category-echarts-count m-margin-tb-large m-echarts-container"
  ></div>
</template>
<script>
import { getDashboardCategory } from "@/api/dashboard";

export default {
  name: "CategoryMap",
  data() {
    return {
      categoryList: [],
    };
  },
  methods: {
    initData() {
      let myChart = this.$echarts.init(
        document.querySelector(".category-echarts-count")
      );
      console.log("category json", JSON.stringify(this.categoryList));
      let option = {
        title: {
          text: "分类下的文章",
          subtext: "category",
          left: "center",
        },
        tooltip: {
          trigger: "item",
        },
        legend: {
          bottom: "bottom",
        },
        toolbox: {
          feature: {
            saveAsImage: {},
          },
        },
        series: [
          {
            name: "分类",
            type: "pie",
            radius: "50%",
            data: this.categoryList,
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: "rgba(0, 0, 0, 0.5)",
              },
            },
          },
        ],
      };
      myChart.setOption(option);
    },

    getData() {
      getDashboardCategory().then((res) => {
        if (res.code === 200) {
          this.categoryList = res.result;
          this.$nextTick(() => {
            this.initData();
          });
        }
      });
    },
  },
  mounted() {
    this.getData();
  },
};
</script>
