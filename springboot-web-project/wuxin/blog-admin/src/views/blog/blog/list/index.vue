<template>
  <div class="app-container">
    <MySearchHeader
      :query="query"
      @handleSearch="handleFilter"
      @handleCreate="handleCreate"
    >
      <el-select
        slot="pre"
        v-model="query.id"
        style="width: 150px"
        size="small"
        @change="handleFilter"
      >
        <el-option :value="null" label="全部" />
        <el-option
          v-for="(item, index) in categoryList"
          :key="index"
          :value="item.cid"
          :label="item.name"
        />
      </el-select>
    </MySearchHeader>

    <el-table
      v-loading="listLoading"
      highlight-current-row
      class="m-table"
      size="mini"
      max-height="350"
      :data="list"
    >
      <el-table-column width="55" type="selection" />
      <el-table-column label="序号" prop="id" align="center" width="55">
        <template slot-scope="{ row, $index }">
          <span>{{ $index + 1 }}</span>
        </template>
      </el-table-column>

      <el-table-column label="作者" align="center" width="100">
        <template slot-scope="{ row }">
          <el-link :underline="false" icon="el-icon-user">{{ row.username }}</el-link>
        </template>
      </el-table-column>
      <el-table-column label="标题" align="center" width="auto">
        <template slot-scope="{ row }">
          <el-tooltip placement="bottom" trigger="hover" :content="row.title">
            <span class="m-message" @click="handleUpdate(row.blogId)">{{ row.title }}</span>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column label="权限" align="center" width="100">
        <template slot-scope="{ row }">
          <el-link
            icon="el-icon-edit"
            size="mini"
            :underline="false"
            :type="row.secrecy ? 'warning' : 'primary'"
            @click.native.prevent="showBlog(row)"
          >
            {{ row.secrecy ? "私密" : "公开" }}
          </el-link>
        </template>
      </el-table-column>
      <el-table-column label="评论" align="center" width="100">
        <template slot-scope="{ row }">
          <el-badge
            v-if="row.commentNum !== 0"
            :value="row.commentNum"
            style="margin-left: 10px; margin-top: 10px"
          >
            <el-icon
              style="font-size: 24px; cursor: pointer"
              name="chat-dot-round"
              :aria-disabled="true"
              @click.native.prevent="handleComment(row)"
            />
          </el-badge>
          <el-icon
            v-else
            style="
              margin-left: 10px;
              margin-top: 10px;
              font-size: 24px;
              cursor: pointer;
            "
            name="chat-dot-round"
            @click.native.prevent="handleComment(row)"
          />
        </template>
      </el-table-column>
      <el-table-column label="分类" align="center" width="100">
        <template slot-scope="{ row }">
          <span>{{ row.category.name }}</span>
        </template>
      </el-table-column>
      <el-table-column label="发布日期" align="center" width="150">
        <template slot-scope="{ row }">
          <span>{{ row.createTime }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="200" fixed="right">
        <template slot-scope="{ row, $index }">
          <el-button type="primary" size="mini" icon="el-icon-edit" @click="handleUpdate(row.blogId)">编辑</el-button>
          <el-button size="mini" type="danger" icon="el-icon-delete" @click="handleDelete(row.blogId, $index)">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页插件 -->
    <MyPagination
      v-show="total > 0"
      :total="total"
      :page.sync="query.current"
      :limit.sync="query.limit"
      @pagination="getList"
    />
    <!--权限设置-->
    <el-dialog title="权限设置" :visible.sync="dialogBlogVisible" width="350px">
      <el-form :model="blog" :inline="true">
        <el-form-item label="置顶">
          <el-switch v-model="blog.top" :active-value="1" :inactive-value="0" />
        </el-form-item>
        <el-form-item label="评论">
          <el-switch v-model="blog.commentEnabled" />
        </el-form-item>
        <el-form-item label="赞赏">
          <el-switch v-model="blog.appreciation" />
        </el-form-item>
        <el-form-item label="发布">
          <el-switch v-model="blog.publish" />
        </el-form-item>
        <el-form-item label="私密">
          <el-switch v-model="blog.secrecy" />
        </el-form-item>
        <el-form-item v-if="blog.secrecy" label="密码">
          <el-input v-if="blog.secrecy" v-model="blog.password" />
        </el-form-item>
      </el-form>
      <div slot="footer" >
        <el-button size="mini" type="info" @click.native.prevent="dialogBlogVisible = false">取消</el-button>
        <el-button size="mini" type="primary" @click.native.prevent="update(blog)">保存</el-button>
        <el-button size="mini" plain @click="handleArchive(blog)">归档</el-button>
      </div>
    </el-dialog>
    <!--    归档-->
    <el-dialog title="归档" :visible.sync="dialogFormArchive" width="40%">
      <AddArchive :archive="archive" @addArchive="addArchive" @cancelArchive="dialogFormArchive=false" />
    </el-dialog>
  </div>
</template>

<script>
import list from '@/views/blog/blog/list/blog'

export default list
</script>
