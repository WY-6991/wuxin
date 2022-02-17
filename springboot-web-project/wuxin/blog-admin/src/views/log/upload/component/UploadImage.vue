<template>
  <div class="image_container">
    <div v-for="image in list" :key="image.id">
      <el-skeleton :loading="!image" animated>
        <template slot="template">
          <el-skeleton-item
            variant="image"
            style="width: 500px; height: 500px;"
          />
          <div style="padding: 14px;">
            <el-skeleton-item variant="h3" style="width: 50%;" />
            <div
              style="display: flex; align-items: center; justify-items: space-between; margin-top: 16px; height: 16px;"
            >
              <el-skeleton-item variant="text" style="margin-right: 16px;" />
              <el-skeleton-item variant="text" style="width: 30%;" />
            </div>
          </div>
        </template>
        <template>
          <el-popover placement="bottom" width="500" trigger="click">
            <ImageDetail :image="image" :has-root="hasRoot" @deletePic="deletePic" />
            <el-card slot="reference" style="cursor: pointer;margin-top: 20px;" shadow="hover">
              <el-image :src="image.url" lazy alt="点击修改图片" @click="parentId = image.id">
                <div slot="error">
                  <svg viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" width="100"
                       height="100">
                    <path
                      d="M849.408 6.656L411.648 140.8c-53.248 15.36-95.744 70.656-95.744 123.392v461.312S284.16 704 213.504 714.24C109.568 729.088 25.6 808.448 25.6 891.904s83.968 134.656 187.904 119.808c103.936-14.848 179.712-91.648 179.712-175.104v-445.44c0-36.864 44.544-52.736 44.544-52.736l387.072-121.344s43.008-14.336 43.008 25.088v367.616s-39.424-22.528-110.08-14.336c-103.936 12.8-187.904 90.624-187.904 174.08S653.824 905.728 757.76 893.44c103.936-12.8 187.904-90.624 187.904-174.08V74.752c-0.512-52.224-43.52-82.944-96.256-68.096z"
                      fill="#515151"
                    />
                  </svg>
                </div>
              </el-image>
            </el-card>
          </el-popover>
        </template>
      </el-skeleton>
    </div>
  </div>
</template>
<script>

import {minix} from './minix'
import ImageDetail from './ImageDetail'

export default {
  name: 'UploadImageList',
  components: {
    ImageDetail
  },
  mixins: [minix],
  data() {
    return {
      parentId: 0, // 用于展示el——popover
      check: 'image', // 默认显示图片
      loading: false
    }
  },
  created() {
    this.getList()
  },
  methods: {

    deletePic(id) {
      this.$message.warning(`该功能暂未上线!`)
    }
  }
}

</script>
<style scoped>

.image_container {
  margin-left: 20px;
  display: flex;
  flex-direction: column;
  align-items: center;
}

</style>

