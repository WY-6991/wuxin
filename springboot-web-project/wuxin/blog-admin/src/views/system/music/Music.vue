<template>
  <div>
    <div class="app-container">
      <h1>music</h1>

      <div class="filter-container">
        <el-button @click.native.prevent="handleCreate" type="primary">
          添加歌曲
        </el-button>
      </div>
      <div class="container">

      </div>
      <div>
        <el-dialog :title="dialogStatus==='create'?'添加歌曲':'修改歌曲'" :visible.sync="dialogVisible">
          <el-form :model="music">
            <el-form-item label="歌名" label-width="50px">
              <el-input placeholder="歌名" v-model="music.name"></el-input>
            </el-form-item>
            <el-form-item label="作者" label-width="50px">
              <el-input placeholder="作者" v-model="music.artist"></el-input>
            </el-form-item>
            <el-form-item label="图片" label-width="50px">
              <el-input placeholder="图片" v-model="music.cover"></el-input>
            </el-form-item>
            <el-form-item label="地址" label-width="50px">
              <el-input placeholder="歌曲地址" v-model="music.url"></el-input>
              <el-button>上传歌曲</el-button>
            </el-form-item>
            <el-form-item label="歌词" label-width="50px">
              <el-input placeholder="歌词地址" v-model="music.lrc"></el-input>
              <el-button>上传歌词</el-button>
            </el-form-item>
            <el-form-item label-width="50px">
             <el-button type="primary">确认</el-button>
             <el-button type="info" @click.native.prevent="cancel()">取消</el-button>
            </el-form-item>
          </el-form>
        </el-dialog>
      </div>
    </div>

  </div>
</template>
<script>
export default {
  name: 'Music',
  data() {
    return {
      dialogVisible: false,
      dialogStatus: 'create',
      music:{
        name: '',
        artist: '',
        url: '',
        cover: '', // prettier-ignore
        lrc: '',
      },
    }
  },
  methods: {
    handleCreate() {
      this.dialogVisible =  true
      this.dialogStatus =  'create'
    },

    handleUpdate() {
      this.dialogVisible =  true
      this.dialogStatus =  'update'

    },

    add() {
      this.dialogVisible =  true
    },


    update() {
      this.dialogVisible =  true
    },

    del(){

    },

    getList(){

    },
    cancel(){
      this.dialogVisible = false
    }
  },

}
</script>
<style scoped>

</style>
