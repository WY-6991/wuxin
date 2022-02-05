<template>
  <div class="app-container">
    <div v-if="user">
      <el-row :gutter="20">
        <el-col :span="6" :xs="24">
          <user-card :user="user" :hobbies="hobbies" />
        </el-col>
        <el-col :span="18" :xs="24">
          <el-card>
            <div slot="header" class="clearfix">
              <span>资料卡</span>
            </div>
            <el-tabs v-model="basicTap">
              <el-tab-pane label="其他资料" name="other">
                <Basic :chatUrl="chatUrl" />
              </el-tab-pane>
              <el-tab-pane label="修改密码" name="account">
                <account :user="user" />
              </el-tab-pane>
            </el-tabs>
          </el-card>
        </el-col>

      </el-row>
    </div>
  </div>
</template>

<script>

import UserCard from './components/UserCard'
import Account from './components/Account'
import Basic from "@/views/profile/components/Basic";
import {findUserInfoDetail} from "@/api/user";

export default {
  name: 'Profile',
  components: { Basic, UserCard, Account},
  data() {
    return {
      basicTap: 'other',
      user: {},
      chatUrl:{},
      hobbies:[]
    }
  },

  created() {
    this.getUser()
  },
  methods: {
    getUser() {
      findUserInfoDetail().then(res=>{
        this.user = res.result.user;
        this.chatUrl = res.result.chatUrl
        this.hobbies  =res.result.hobbies
      })
    }
  }
}
</script>
