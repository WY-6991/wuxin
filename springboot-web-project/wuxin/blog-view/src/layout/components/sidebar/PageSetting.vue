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
          <!--          <el-switch v-model="isVisible" @click="$message.success('hello')"></el-switch>-->
        </div>
        <el-form size="small" label-position="top">
          <el-form-item label="导航栏">
            <el-row>
              <el-col :span="8" :xs="24">
                <el-switch v-model="settingState.inverted" class="m-margin-small" active-text="反转" />
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
                  <el-color-picker v-model="settingState.background.color" />
                </el-tooltip>

              </el-col>
              <el-col :span="8" :xs="24" class="m-mobile-hide">
                <el-switch v-model="settingState.focusMode" active-text="侧边栏" />
              </el-col>


            </el-row>

          </el-form-item>
          <el-form-item label="背景图设置">
            <el-switch v-model="settingState.background.isShowImage" active-text="开启"
                       class="m-margin-small" />
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
        </el-form>
      </el-card>


    </el-drawer>


  </div>
</template>
<script>
import {colors, backgroundImageList} from "@/utils/setting";
import {mapGetters} from 'vuex'
import {UPDATE_NIGTH_MODE} from "@/store/mutations-type";

export default {
  name: 'PageSetting',
  data() {
    return {
      drawer: false,
      colors: colors,
      backgroundImageList: backgroundImageList,
      visible: true
    }
  },
  computed: {
    ...mapGetters(['settingState']),
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


    }
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