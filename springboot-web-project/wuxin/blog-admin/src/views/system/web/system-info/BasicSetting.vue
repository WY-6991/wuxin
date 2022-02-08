<template>
  <MyCard title="站点设置">

    <el-form :model="systemInfo" label-width="100px" size="small">
      <el-form-item label="图标" prop="adminIcon">
        <CustomUploadAvatar :image="systemInfo.adminIcon" @updateImage="updateAdminIcon" />
        <el-input v-model="systemInfo.adminIcon" prefix-icon="el-icon-s-opportunity" />
      </el-form-item>
      <el-form-item label="二维码图片">
        <CustomUploadAvatar :image="systemInfo.erCode" @updateImage="updateErCodeImage" />
        <el-input v-model="systemInfo.erCode" prefix-icon="el-icon-s-home" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="small" @click.prevent="updateData">确认</el-button>
      </el-form-item>
    </el-form>
  </MyCard>
</template>
<script>
import { validURL } from '@/utils/validate'
import { minix } from '@/views/system/web/system-info/minix'
import CustomUploadAvatar from '@/components/MyComponents/CustomUploadAvatar'

export default {
  name: 'BasicSetting',
  components: { CustomUploadAvatar },
  mixins: [minix],
  data() {
    const validAdminIcon = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('图标地址不能为空！'))
      } else if (!validURL(value)) {
        callback(new Error('图标地址错误！'))
      } else {
        callback()
      }
    }
    const validWebUrl = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('图标地址不能为空！'))
      } else if (!validURL(value)) {
        callback(new Error('图标地址错误！'))
      } else {
        callback()
      }
    }
    const validLoginUrl = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('图标地址不能为空！'))
      } else if (!validURL(value)) {
        callback(new Error('图标地址错误！'))
      } else {
        callback()
      }
    }

    return {
      rules: {
        adminIcon: [
          { validator: validAdminIcon, trigger: 'blur' }
        ],
        webUrl: [
          { validator: validWebUrl, trigger: 'blur' }
        ],
        loginUrl: [
          { validator: validLoginUrl, trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    updateErCodeImage(url) {
      this.systemInfo.erCode = url
      this.updateData()
    },
    updateAdminIcon(url) {
      this.systemInfo.adminIcon = url
      this.updateData()
    }
  }

}

</script>
