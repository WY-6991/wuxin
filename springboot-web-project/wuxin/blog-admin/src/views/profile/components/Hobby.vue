<template>
  <div >
    <template v-if="hobbies&&hobbies.length!==0">
      <el-collapse v-model="activeName" accordion>
        <el-collapse-item :title="item.title" :name="`hobby-name-${index}`" v-for="(item,index) in hobbies "
                          :key="`hobby-${index}`">
          <div>{{ item.content }}</div>
          <el-button-group class="m-float-right m-float-right-10">
            <el-tooltip content="编辑" placement="bottom" :open-delay="2">
              <el-button type="text" size="medium" icon="el-icon-edit" class="m-margin-left-small"
                         @click="handleUpdate(item)"></el-button>
            </el-tooltip>

            <el-popconfirm
              confirm-button-text='确认'
              cancel-button-text='取消'
              icon="el-icon-info"
              icon-color="red"
              @onCancel="$message.info('取消操作')"
              @onConfirm="handleDelete(item.id,index)"
              title="确认删除,删除就无法恢复了哦？"
            >
              <el-button slot="reference" type="text" size="medium" icon="el-icon-delete" class="m-margin-left-small"
              ></el-button>
            </el-popconfirm>

          </el-button-group>
        </el-collapse-item>
      </el-collapse>

    </template>
    <template v-else>
      <span>~暂无爱好~</span>
    </template>

    <el-tooltip content="添加我的爱好" :open-delay="3" placement="bottom" class="m-float-right m-float-right-10">
      <el-button type="text" size="medium" icon="el-icon-plus" @click="handleCreate()"></el-button>
    </el-tooltip>

    <el-dialog :title="status==='create'?'添加爱好':'编辑爱好'" :visible.sync="dialogFormVisible" width="40%">
      <el-form :model="temp" :rules="rules" :label-width="formLabelWidth" size="small" ref="dataForm">
        <el-form-item label="标题" prop="title">
          <el-input
            v-model="temp.title"
            autocomplete="off"
            maxlength="20"
            show-word-limit></el-input>
        </el-form-item>
        <el-form-item label="内容" prop="content">
          <el-input type="textarea"
                    :rows="2"
                    maxlength="100"
                    show-word-limit
                    placeholder="请输入内容"
                    v-model="temp.content" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click.prevent="status==='create'?createData():updateData()">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>

import {createHobby, updateHobby, deleteHobby} from "@/api/user";

export default {
  props: {
    hobbies: {
      type: Array,
      default: () => {
        return {
          hobbies: []
        }
      }
    },
  },

  data() {
    return {
      activeName: '1',
      dialogFormVisible: false,
      visible: false,
      temp: {title: '', content: '', userId: ''},
      formLabelWidth: '50px',
      status: 'create',
      rules: {
        title: [
          {required: true, message: '请填写标题', trigger: 'blur'}
        ],
        content: [
          {required: true, message: '请填写内容', trigger: 'blur'}
        ]
      }
    };
  },
  methods: {
    handleCreate() {
      this.status = 'create'
      this.temp = {title: '', content: '', userId: this.$store.state.user.userId}
      this.dialogFormVisible = true
    },


    handleUpdate(obj) {
      this.status = 'update'
      this.temp = obj
      this.dialogFormVisible = true
    },

    createData() {
      this.$refs.dataForm.validate(valid => {
        if (valid) {
          if (this.temp.userId === "" || this.temp.userId === null) {
            return this.$message.error('添加失败获取不到用户Id，请重新登录试试吧~')
          }
          createHobby(this.temp).then(res => {
            if (res.code === 200) {
              this.$message.success("添加成功！")
              this.hobbies.push(this.temp)
            }
            this.dialogFormVisible = false
          })
        }
      })
    },

    updateData() {
      this.$refs.dataForm.validate(valid => {
        if (valid) {
          updateHobby(this.temp).then(res => {
            if (res.code === 200) {
              this.$message.success("修改成功！")
            }
            this.dialogFormVisible = false
          })
        }
      })
    },

    handleDelete(id, index) {
      deleteHobby(id).then(res => {
        if (res.code === 200) {
          this.hobbies.splice(index, 1)
          this.$message.success("删除成功！")
        }
      })

    }
  },
}
</script>
