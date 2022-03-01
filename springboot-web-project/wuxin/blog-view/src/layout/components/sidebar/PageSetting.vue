<template>
  <div class="setting">
    <el-button size="mini" type="primary" icon="el-icon-setting" @click.prevent="drawer=!drawer">
    </el-button>
    <el-drawer
        title="我是标题"
        :visible.sync="drawer"
        :modal="false"
        :with-header="false">
      <el-card>
        <div slot="header">
          <h3>页面设置</h3>
        </div>
        <el-form size="small" label-position="top">
          <el-form-item label="导航栏">
            <el-row>
              <el-col :span="8" :xs="24">
                <el-switch v-model="settingState.inverted" class="m-margin-small" active-text="反转"/>
              </el-col>
              <el-col :span="12" :xs="24">
                <el-select v-model="settingState.menuColor"
                           placeholder="请选择" class="m-margin-small">
                  <el-option
                      v-for="(item,index) in colors"
                      :key="`${item}${index}`"
                      :label="item"
                      :value="item">
                    <span style="float: left">{{ item !== 'default' ? item : '默认' }}</span>
                    <span style="float: right;width: 60px;height: 24px;"
                          :style="{'background':item!=='default'?item:''}"></span>
                  </el-option>
                </el-select>
              </el-col>
            </el-row>
          </el-form-item>

          <el-form-item label="基本设置">
            <el-row>
              <el-col :span="3" :xs="24">
                <el-tooltip content="开启夜间模式前请关闭背景图🙂" :disabled="!settingState.background.isShowImage">
                  <i class="m-setting-icon" :class="settingState.nightMode?'el-icon-sunny':'el-icon-moon'"
                     @click="updateNightMode"
                     style="cursor: pointer;font-size: 30px;"></i>
                </el-tooltip>
              </el-col>
              <el-col :span="4" :xs="24">
                <el-tooltip
                    content="请先关闭 夜间模式 和 背景图 自定义背景才会生效"
                    :disabled="!settingState.background.isShowImage&&!settingState.nightMode"
                >
                  <el-color-picker v-model="settingState.background.color"/>
                </el-tooltip>

              </el-col>
              <el-col :span="8" :xs="24" class="m-mobile-hide">
                <el-switch v-model="settingState.focusMode" active-text="侧边栏"/>
              </el-col>
            </el-row>
          </el-form-item>
          <el-form-item label="背景图设置">
            <el-switch v-model="settingState.background.isShowImage" active-text="开启"
                       class="m-margin-small"/>
            <el-tooltip content="请打开背景图开关" :disabled="settingState.background.isShowImage">
              <el-select v-model="settingState.background.image" placeholder="请选择背景图" class="m-margin-small">
                <el-option
                    v-for="(item,index) in backgroundImageList"
                    :key="`image${index}`"
                    :label="`${item==='default'?'无(默认)':'背景图'+index}`"
                    :value="item">
                </el-option>
              </el-select>
            </el-tooltip>
          </el-form-item>

          <el-form-item label="背景图设置">
            <el-button type="text" @click="dialogVisible = true">自定义背景</el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </el-drawer>

    <el-drawer :visible.sync="dialogVisible" :modal="false" size="40%" :before-close="handleClose">
      <component :is="componentName" @error="componentName='Token'"></component>
      <el-button type="text" @click="loadCompontentName" class="m-float-r m-margin-lr">查看仓库</el-button>
    </el-drawer>
  </div>
</template>
<script>
import {colors, backgroundImageList} from "@/utils/setting";
import {setSetStore, getStore, removeStore} from "@/utils/session";
import {mapGetters} from 'vuex'
import {UPDATE_NIGTH_MODE} from "@/store/mutations-type";
import {getUserInfo, getUserRepos, getReposContents, delFile, upload, repoKey} from "@/api/github";
import Token from "@/layout/components/sidebar/Token";
import RepoUpload from "@/layout/components/sidebar/Upload";

export default {
  name: 'PageSetting',
  data() {
    return {
      drawer: false,
      dialogVisible: false,
      colors: colors,
      backgroundImageList: backgroundImageList,
      visible: true,
      user: {
        token: '',
        username: '',
        avatar: '',
      },
      isSave: false,
      componentName: 'Token'
    }
  },
  components: {
    Token,
    RepoUpload
  },
  computed: {
    ...mapGetters(['settingState']),
  },
  created() {
    const user = getStore(repoKey)
    if (user && user.token && user.username && user.avatar) {
      this.user = user
    } else {
      this.isSave = true
    }

  },
  methods: {
    updateNightMode() {
      let nightMode = this.settingState.nightMode
      this.settingState.nightMode = !nightMode
      if (this.settingState.nightMode) {
        // 如果开启了夜间模式，导航栏需要开启夜间颜色反转
        this.settingState.inverted = true
      } else {
        this.settingState.inverted = false
      }
    },

    loadCompontentName() {
      if (this.componentName === 'Token') {
        this.componentName = 'RepoUpload'
      } else {
        this.componentName = 'Token'
      }
    },

    handleClose() {
      this.$confirm('确认关闭？')
          .then(_ => {
            this.timer = setTimeout(() => {
              done();
              // 动画关闭需要一定的时间
              setTimeout(() => {
              }, 400);
            }, 2000);
          })
          .catch(_ => {
          });
    },


  },


}
</script>
<style scoped>
.setting {
  position: fixed;
  top: 300px;
  right: 0;
  z-index: 1000;
}

.el-drawer {
  display: flex !important;
  justify-content: center !important;
  align-items: center !important;
  text-align: center;
}

.el-row {
  margin: 20px !important;
}

.el-form {
  width: 100% !important;
  height: 100% !important;
  /*border: skyblue 10px solid;*/
}

.m-setting-icon {
  cursor: pointer !important;
  font-size: 30px !important;
}

</style>