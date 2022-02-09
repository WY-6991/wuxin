<template>
  <div class="app-container">
    <MySearchHeader :show-create-button="false" @handleSearch="handleSearch">
      <slot></slot>
      <el-button
        slot="end"
        type="danger"
        icon="el-icon-delete"
        class="m-margin-left-small"
        size="mini"
        @click="NotOnline"
      >
        全部删除
      </el-button>
    </MySearchHeader>

    <el-table
      ref="multipleTable"
      v-loading="listLoading"
      :data="list"
      highlight-current-row
      class="m-table"
      max-height="350"
      size="mini"
      fit
      @selection-change="handleSelectionChange"
    >
      <el-table-column width="55" type="selection"></el-table-column>

      <el-table-column label="序号" width="55" align="center">
        <template slot-scope="{ row, $index }">
          <span>{{ $index + 1 }}</span>
        </template>
      </el-table-column>

      <el-table-column label="头像" width="150" align="center">
        <template slot-scope="{ row }">
          <MyImage :image="row.avatar" />
        </template>
      </el-table-column>
      <el-table-column label="用户" width="150" align="center">
        <template slot-scope="{ row }">
          <MyUserLink :username="row.username" />
        </template>
      </el-table-column>
      <el-table-column label="评论内容" width="auto" align="center">
        <template slot-scope="{ row }">
          <!--如果没有回复不展示这个组件-->
          <ReplyList
            :row="row"
            v-if="row.replyList && row.replyList.length !== 0"
            @delReply="delReply"
          />
          <!--评论内容-->
          <span v-else class="m-message">{{ row.content }}</span>
        </template>
      </el-table-column>

      <el-table-column label="文章" width="160" align="center">
        <template slot-scope="{ row }">
          <span class="m-message link-type"
            >《 {{ row.title ? row.title : "未知" }}》</span
          >
        </template>
      </el-table-column>

      <el-table-column label="评论日期" width="160" align="center">
        <template slot-scope="{ row }">
          <span>{{ row.createTime }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="200">
        <template slot-scope="{ row, $index }">
          <el-tooltip
            :content="row.status ? '显示评论' : '隐藏评论'"
            placement="bottom"
            :open-delay="1000"
          >
            <el-button
              :type="row.status ? 'info' : 'success'"
              circle
              :icon="
                row.status
                  ? 'el-icon-turn-off-microphone'
                  : 'el-icon-microphone'
              "
              @click="updateComment(row)"
            />
          </el-tooltip>
          <el-tooltip content="删除评论" placement="bottom" :open-delay="1000">
            <el-button
              type="danger"
              circle
              icon="el-icon-delete"
              @click="delComment(row.commentId, $index)"
            />
          </el-tooltip>
        </template>
      </el-table-column>
    </el-table>

    <div style="margin-top: 20px">
      <el-button
        type="warning"
        icon="el-icon-delete"
        plain
        size="small"
        class="m-margin-left-small"
        @click="NotOnline"
      >
        删除
      </el-button>
      <el-button @click="toggleSelection" size="small">取消选择</el-button>
    </div>

    <!-- 分页插件 -->
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
import ReplyList from "@/views/pages/comment/ReplyList";
import MyImage from "@/components/MyComponents/MyImage";
import UsernameLink from "@/components/MyComponents/MyUsernameLink";
import MyUserLink from "@/components/MyComponents/MyUsernameLink";

export default {
  name: "CommentList",
  components: { MyUserLink, UsernameLink, MyImage, ReplyList },
  props: {
    list: {
      type: Array,
      default: [],
    },
    listLoading: {
      type: Boolean,
      default: false,
    },
    total: {
      type: Number,
      default: 0,
    },
    query: {
      type: Object,
      default: { current: 1, limit: 10, keywords: "" },
    },
  },

  data() {
    return {
      ids: [],
      multipleSelection: [],
    };
  },

  mounted() {
    this.getList();
  },
  methods: {
    handleSearch(query) {
      this.$emit("handleSearch", query);
    },
    getList(query) {
      this.$emit("getList", this.query);
    },
    delComment(cid, index) {
      if (!this.isRoot) {
        this.$message.error("操作失败，无权限执行该操作！");
        return;
      }
      this.$emit("delComment", cid);
      this.list.splice(index, 1);
    },
    delReply(id, index) {
      if (!this.isRoot) {
        this.$message.error("操作失败，无权限执行该操作！");
        return;
      }
      this.$emit("delReply", id);
      // this.list.splice(index,1)
      console.log("删除了评论回复内容...");
    },

    delAllComment() {
      if (!this.isRoot) {
        this.$message.error("操作失败，无权限执行该操作！");
        return;
      }
      this.$emit("delAllComment");
    },

    delPartComment(ids) {
      if (!this.isRoot) {
        this.$message.error("操作失败，无权限执行该操作！");
        return;
      }
      this.$emit("delPartComment", ids);
    },

    updateComment(comment) {
      if (!this.isRoot) {
        this.$message.error("操作失败，无权限执行该操作！");
        return;
      }
      this.$emit("updateComment", {
        commentId: comment.commentId,
        commentUserId: comment.commentUserId,
        status: comment.status,
        content: comment.content,
      });
    },

    toggleSelection() {
      this.$refs.multipleTable.clearSelection();
    },

    handleSelectionChange(val) {
      this.multipleSelection = val;
    },

    NotOnline() {
      this.$notify.warning("该操作还未上线哦！");
    },
  },
};
</script>
