<template>
  <div class="ui attached segment" :style="bgColor">
    <div class="container">
      <!-- 标题 -->
      <Title :title="title" />
      <!-- 友情来链接 -->
      <FriendList :friendList="friendList" />
      <!-- 评论 -->
      <Comment :type="3" />
    </div>
  </div>

</template>

<script>
import Title from "@/components/common/Title";
import FriendList from "@/components/friend/FrirendList";
import {
  getFriendList
} from "@/api/friend";
import Comment from "@/components/comment/Comment";

export default {
  name: 'Friend',
  data() {
    return {
      title: '友情链接',
      friendList: [],
    }
  },
  components: {
    Comment,
    FriendList,
    Title,
  },
  methods: {
    getList() {
      getFriendList().then(res => {
        this.friendList = res.result
      })
    },

  },
  created() {

    // 加载朋友列表
    this.getList()
  },

};
</script>

