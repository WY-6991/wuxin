<template>
  <div class="app-container">
    <MySearchHeader :show-search-input="false" @handleSearch="handleFilter" :show-search-button="false" :show-create-button="false">
      <el-button  class="m-margin-left-mini" :type="compontentName === 'UploadImageTable'?'info':'success'" size="mini"
                  @click="compontentName === 'UploadImageTable' ?changeName('UploadImageList'):changeName('UploadImageTable')">
        {{ compontentName === 'UploadImageTable' ? '图片形式' : '表格形式' }}
      </el-button>
    </MySearchHeader>
    <component :is="compontentName" :list="filterList" :hasRoot="isRoot"></component>
    <MyPagination
      v-show="total > 0"
      :total="total"
      :page.sync="query.current"
      :limit.sync="query.limit"
      @pagination="getList"
    />

  </div>
</template>
<script>
import {getUploadFileList} from '@/api/system'
import UploadImageList from './component/UploadImage'
import UploadImageTable from './component/UploadTable'
import {query} from '@/mixin/query'

export default {
  name: 'FileUploadLog',
  components: {UploadImageTable, UploadImageList},
  mixins:[query],
  data() {
    return {
      compontentName: 'UploadImageTable',
      visiableDialog: false,
      log: {},
    }
  },
  computed: {

    filterList() {
      const list = this.list
      // 判断是否是root用户
      if (this.isRoot) {
        return this.list
      } else {
        let arr = []
        for (let i = 0; i < list.length; i++) {
          let obj = {}
          obj.name = list[i].name
          obj.url = list[i].url
          obj.createTime = list[i].createTime
          arr.push(obj)
        }
        return arr;
      }
    },
  },
  methods: {
    getList() {
      getUploadFileList(this.query).then(res => {
        if (res.code === 200) {
          this.list = res.result.records
          this.total = res.result.total
        }
      })
    },
    showForm(log) {
      this.log = log
      this.visiableDialog = true
    },
    changeName(name) {
      this.compontentName = name
    }
  },
  mounted() {
    this.getList()
  }

}
</script>
<style scoped>

</style>
