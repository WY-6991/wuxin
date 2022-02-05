<template>
  <div>
    <el-card style="margin-bottom:20px;">
      <div slot="header" class="clearfix">
        <span>我的信息</span>
      </div>
      <div class="user-profile">
        <div class="box-center">
          <CustomUploadAvatar :border-radius="true"
            :image="user.avatar || require('@/assets/image/huaji.jpg')"
            @updateImage="updateImage"
          />
        </div>
      </div>

      <div class="user-bio">
        <EditUserInfo :user="user"></EditUserInfo>
      </div>

      <div class="user-bio">
        <MyHobby :hobbies="hobbies" ></MyHobby>
      </div>

    </el-card>


  </div>

</template>

<script>
import PanThumb from '@/components/PanThumb'
import MyHobby from "@/views/profile/components/Hobby";
import CustomUploadAvatar from "@/components/MyComponents/CustomUploadAvatar";
import {updateUser} from "@/api/user";
import EditUserInfo from "@/views/profile/components/edit-user-info";


export default {
  components: {EditUserInfo, CustomUploadAvatar, MyHobby, PanThumb},
  props: {
    user: {
      type: Object,
      default: () => {
        return {
          name: '',
          email: '',
          avatar: '',
          role: ''
        }
      }
    },
    hobbies: {
      type: Array,
      default: () => {
        return {
          hobbies: []
        }
      }
    }
  },
  data() {
    return {
      showMotto: true,
      showName: true,
      showPhone: true,
      showEmail: true,
    }
  },
  methods: {
    updateImage(url) {
      console.log('imageUrl=>' + url)
      updateUser({userId: this.user.userId, 'avatar': url}).then(res => {
        if (res.code === 200) {
          this.$store.commit('user/SET_AVATAR', url)
          this.user.avatar = url
        }
      })
    },

    updateUserInfo() {
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.showMotto = true
        this.showName = true
        this.showEmail = true
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });

    }
  },
}
</script>

<style lang="scss" scoped>
.box-center {
  margin: 0 auto;
  display: table;
}

.text-muted {
  color: #777;
}

.user-profile {
  .user-name {
    font-weight: bold;
  }

  .box-center {
    padding-top: 10px;
  }

  .user-role {
    padding-top: 10px;
    font-weight: 400;
    font-size: 14px;
  }

  .box-social {
    padding-top: 30px;

    .el-table {
      border-top: 1px solid #dfe6ec;
    }
  }

  .user-follow {
    padding-top: 20px;
  }
}

.user-bio {
  margin-top: 20px;
  color: #606266;

  span {
    padding-left: 4px;
  }

  .user-bio-section {
    font-size: 14px;
    padding: 15px 0;

    .user-bio-section-header {
      border-bottom: 1px solid #dfe6ec;
      padding-bottom: 10px;
      margin-bottom: 10px;
      font-weight: bold;
    }
  }
}
</style>
