<template>
  <div>
    <el-popover placement="top" width="auto" trigger="hover">
      <el-badge :value="row.replyList.length" style="margin: 20px 0px 0px 0px;" slot="reference">
        <span class="m-message" style="margin-top: 20px;" >{{ row.content }}</span>
      </el-badge>
      <el-table :data="row.replyList" highlight-current-row class="m-table" max-height="200" fit size="mini">
        <el-table-column label="序号" min-width="55" align="center">
          <template slot-scope="{ row ,$index}">
            <span>{{ $index + 1 }}</span>
          </template>
        </el-table-column>
        <el-table-column label="用户" align="center" width="200">
          <template slot-scope="{ row }">
            <MyUserLink :username="row.replyUsername" />
            @
            <span>{{ row.commentUsername }}</span>
          </template>
        </el-table-column>
        <el-table-column label="内容" width="200" align="center">
          <template slot-scope="{ row }">
            <el-tooltip placement="top" trigger="hover" :content="row.replyContent">
              <span class="link-type m-message">{{ row.replyContent }}</span>
            </el-tooltip>
          </template>
        </el-table-column>
        <el-table-column label="日期" width="160" align="center">
          <template slot-scope="{ row }">
            <span>{{ row.createTime }}</span>
          </template>
        </el-table-column>

        <el-table-column label="操作" align="center" width="200">
          <template slot-scope="{ row, $index }">
            <el-tooltip content="隐藏该回复" placement="bottom" open-delay="1000">
              <el-button type="info" circle icon="el-icon-turn-off-microphone" @click="NotOnline" />
            </el-tooltip>
            <el-tooltip content="禁言" placement="bottom" open-delay="1000">
              <el-button type="warning" circle icon="el-icon-chat-round" @click="NotOnline" />
            </el-tooltip>
            <el-tooltip content="删除回复" placement="bottom" open-delay="1000">
              <el-button type="danger" circle icon="el-icon-delete" @click="deleteReply(row.replyId)" />
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
    </el-popover>


  </div>
</template>
<script>
import MyUserLink from "@/components/MyComponents/MyUsernameLink";
import MySearchHeader from "@/components/MyComponents/MySearchHeader";

export default {
  name: 'ReplyList',
  components: {MySearchHeader, MyUserLink},
  props: ['row', 'content'],
  methods: {
    deleteReply(id) {
      if (!this.isRoot) {
        this.$message.error('操作失败，无权限执行该操作！')
        return;
      }
      this.$emit('deleteReply', id)
    },
    NotOnline() {
      this.$notify.warning('该操作还未上线哦！')
    }
  },

}
</script>
<style scoped>

.message {
  max-width: 300px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;

}


</style>
