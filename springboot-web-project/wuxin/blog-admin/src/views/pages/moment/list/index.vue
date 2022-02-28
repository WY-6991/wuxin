<template>
  <div class="app-container">
    <MySearchHeader
      @handleCreate="handleCreate"
      @handleSearch="handleFilter"
      :query="query"
      :show-search-input="false"
      :show-search-button="false"
      :loading="listLoading"
    />
    <!-- 表格数据 -->
    <el-table
      v-loading="listLoading"
      :data="list"
      fix
      highlight-current-row
      class="m-table"
      max-height="350"
      size="mini"

    >
      <el-table-column
        label="序号"
        prop="id"
        align="center"
        width="160"
        fixed
      >
        <template slot-scope="{ row,$index }">
          <span>{{ $index + 1 }}</span>
        </template>
      </el-table-column>

      <el-table-column label="用户" width="100" align="center">
        <template slot-scope="{ row }">
          <MyUserLink :username="row.username" />
        </template>
      </el-table-column>

      <el-table-column label="头像" width="100" align="center">
        <template slot-scope="{ row }">
          <MyImage :image="row.avatar" />
        </template>
      </el-table-column>


      <!--      内容展示-->
      <el-table-column label="内容" align="center">
        <template slot-scope="{ row }">
          <span class="link-type m-message" @click="updateData(row.momentId)">{{ row.content }}</span>
        </template>
      </el-table-column>

      <el-table-column label="发布日期" width="160" align="center">
        <template slot-scope="{ row }">
          <span>{{ row.createTime }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        align="center"
        width="300"
        fixed="right"

      >
        <template slot-scope="{ row, $index }">
          <el-button type="primary" size="mini" icon="el-icon-edit" @click="updateData(row.momentId)">
            编辑
          </el-button>
          <el-button
            size="mini"
            icon="el-icon-delete"
            type="danger"
            @click="deleteData(row.momentId,$index)"

          >
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
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
import ImageCropper from '@/components/ImageCropper'
import PanThumb from '@/components/PanThumb'
import {delMoment, getMomentList} from "@/api/moment";
import MyImage from "@/components/MyComponents/MyImage";
import MyUserLink from "@/components/MyComponents/MyUsernameLink";
import {query} from "@/mixin/query";

export default {
  name: "MomentList",
  components: {MyUserLink, MyImage, ImageCropper, PanThumb},
  mixins: [query],
  mounted() {
    this.getList()
  },
  methods: {

    getData(query) {
      getMomentList(query).then(res => {
        this.list = res.result.records
        this.total = res.result.total
      })
    },

    getList() {
      this.getData(this.query)
    },
    updateData(momentId) {
      this.$router.push('update/' + momentId)
    },
    handleCreate() {
      this.$router.push('add')
    },
    deleteData(momentId, index) {
      delMoment(momentId).then(res => {
        this.list.splice(index, 1)
        this.$message.success("删除成功!")
      })
    },


  }
};
</script>

<style>
.el-form {
  margin-top: 55px !important;
}

.el-form > .el-form-item > button {
  padding-left: 20px !important;
}


</style>
