<template>
  <MyCard title="站点设置">
    <el-form :model="systemInfo" label-width="80px" size="small">
      <el-form-item label="图标" prop="adminIcon">
        <el-input prefix-icon="el-icon-s-opportunity" v-model="systemInfo.adminIcon" />
      </el-form-item>
      <el-form-item label="网站地址" prop="webUrl">
        <el-input prefix-icon="el-icon-s-home" v-model="systemInfo.webUrl" />
      </el-form-item>
      <el-form-item label="登录地址" prop="loginUrl">
        <el-input prefix-icon="el-icon-s-platform" v-model="systemInfo.loginUrl" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="small" @click.prevent="updateData">确认</el-button>
      </el-form-item>
    </el-form>
  </MyCard>
</template>
<script>
import {validURL} from "@/utils/validate";
import {minix} from "@/views/system/web/system-info/minix";

export default {
  name: 'BasicSetting',
  mixins: [minix],
  data() {
    const validAdminIcon = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('图标地址不能为空！'));
      } else if (!validURL(value)) {
        callback(new Error('图标地址错误！'));
      } else {
        callback();
      }
    };
    const validWebUrl = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('图标地址不能为空！'));
      } else if (!validURL(value)) {
        callback(new Error('图标地址错误！'));
      } else {
        callback();
      }
    };
    const validLoginUrl = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('图标地址不能为空！'));
      } else if (!validURL(value)) {
        callback(new Error('图标地址错误！'));
      } else {
        callback();
      }
    };
    const validAdminBackroundImage = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('图标地址不能为空！'));
      } else if (!validURL(value)) {
        callback(new Error('图标地址错误！'));
      } else {
        callback();
      }
    };
    const validUploadAvatarUrl = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('图标地址不能为空！'));
      } else if (!validURL(value)) {
        callback(new Error('图标地址错误！'));
      } else {
        callback();
      }
    };
    const validUploadImageUrl = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('图标地址不能为空！'));
      } else if (!validURL(value)) {
        callback(new Error('图标地址错误！'));
      } else {
        callback();
      }
    };
    return {
      rules: {
        adminIcon: [
          {validator: validAdminIcon, trigger: 'blur'}
        ],
        webUrl: [
          {validator: validWebUrl, trigger: 'blur'}
        ],
        loginUrl: [
          {validator: validLoginUrl, trigger: 'blur'}
        ],
        adminBackgroundImage: [
          {validator: validAdminBackroundImage, trigger: 'blur'}
        ],
        uploadAvatarUrl: [
          {validator: validUploadAvatarUrl, trigger: 'blur'}
        ],
        uploadImageUrl: [
          {validator: validUploadImageUrl, trigger: 'blur'}
        ]
      }
    }
  },

}

</script>
