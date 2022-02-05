<template>
  <el-card :shadow="shadow">
    <div slot="header">
      <h3>{{ title }}</h3>
    </div>
    <slot></slot>
  </el-card>
</template>
<script>
export default {
  name:'MyCard',
  props:{
    shadow:{
      type:String,
      default:'hover'
    },
    title:{
      type:String,
      default:"MyCard"
    }
  }

}
</script>
