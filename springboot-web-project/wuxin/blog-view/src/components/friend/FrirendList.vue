<template lang="html">
  <div class="container ">
    <sui-card-group :items-per-row="3" class="stackable">
      <sui-card class="raised link " :class="settingState.nightMode?'night-mode':colorObj[index%9]" v-for="(item,index) in randomFriendList" :key="`friend${index}`">
        <el-link :href="item.url" :underline="false" :title="'访问'+item.username+'的主页'" target="_blank">
          <sui-image shape="circular" :src="item.avatar"/>
        </el-link>
        <sui-card-content>
          <sui-card-header style="color: white">{{ item.username }}</sui-card-header>
          <sui-card-meta  style="color: white">{{ item.introduction }}</sui-card-meta>
        </sui-card-content>
      </sui-card>
    </sui-card-group>

    <el-divider></el-divider>

    <div class="ui attached positive message" :style="bgColor">
      <p>需要添加网址的在评论区留言就行了 , 排名不分先后哦！ 基本格式 :</p>
      <ul class="list">
        <li> 昵称 : xxx</li>
        <li> 头像地址（可不填）</li>
        <li> 网址 : https://www.xxx.com</li>
        <li> 简单介绍 : 人不能做一条咸鱼吧🙂！</li>
      </ul>
    </div>
  </div>
</template>

<script>
import {mapGetters} from "vuex";

export default {
  name: "FriendList",

  data() {
    return {
      colorObj: {
        0: 'bg-1',
        1: 'bg-2',
        2: 'bg-3',
        3: 'bg-4',
        4: 'bg-5',
        5: 'bg-6 ',
        6: 'bg-7',
        7: 'bg-8',
        8: 'bg-9',
      }
    };
  },
  props: {
    friendList: {
      type: Array,
      default: [],
    }
  },

  computed: {

    ...mapGetters(['settingState']),

    // 随机背景色
    color() {
      let index = Math.floor(Math.random() * this.colorList.length);
      return this.colorList[index];
    },

    // 随机将友情链接排序
    randomFriendList() {

      return this.randomList(this.friendList)

    },

    styleObj() {
      return {
        'backgroundColor': this.settingState.nightMode ? 'rgb(200, 200, 200)' : this.randomColor(),
        'color':this.settingState.nightMode ? 'rgb(10, 10, 10)' : this.randomColor()
      }
    }
  },
  methods: {
    // 标签背景颜色随机
    randomColor() {
      let index = Math.floor(Math.random() * this.colorList.length);
      return this.colorList[index];
    },

    randomList(arr) {
      let len = arr.length;
      for (let i = 0; i < len - 1; i++) {
        let index = parseInt(Math.random() * (len - i));
        let temp = arr[index];
        arr[index] = arr[len - i - 1];
        arr[len - i - 1] = temp;
      }
      return arr;
    }

  },
};
</script>

<style scoped>
.container {
  margin: 20px 0;
}

.ui >.card {
  text-align: center;
  height: 170px;
  display: flex;
  justify-content: center;
  align-items: center;
  overflow: hidden;
  padding: 20px;
}

/* 头像设置为原型图片 */
img {
  width: 80px !important;
  height: 80px !important;
  border-radius: 50% !important;
}


.bg-1 {
  background-color: rgb(238, 90, 36) !important;
}

.bg-2{
  background-color: rgb(100,100,0) !important;
}

.bg-3 {
  background-color: rgb(50, 191, 255) !important;
}

.bg-4 {
  background-color: rgb(0, 128, 128) !important;
}

.bg-5 {
  background-color: rgb(100,100,130) !important;
}

.bg-6 {
  background-color: rgb(100,100,232) !important;
}

.bg-7 {
  background-color: rgb(255, 165, 0) !important;
}

.bg-8 {
  background-color: rgb(139, 69, 19) !important;
}


.bg-9{
  background-color: rgb(200,255,0) !important;
}

.night-mode{
  background-color: rgb(96, 98, 102) !important;
  border: rgb(30,10,10) 1px solid;
}



</style>
