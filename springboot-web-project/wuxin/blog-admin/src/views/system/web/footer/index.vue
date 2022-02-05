<template>
  <div class="m-margin-tb-small">
    <MyCard title="底部标签设置">
      <FooterContent
        v-for="(item,index) in footerLabel"
        :item="item"
        :key="index"
        @deleteItem="deleteItem"
        @updateItem="updateItem"
      />
      <el-button type="primary" icon="el-icon-plus" size="mini" @click.prevent="createItem()">添加</el-button>
    </MyCard>
  </div>
</template>
<script>
import footer from './index'

export default footer
</script>
<style scoped>

</style>
