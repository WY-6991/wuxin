import Vue from 'vue'

import {Message, MessageBox} from "element-ui";



/**
 * 全局操作成功事件注册
 * @param res
 * @param message
 */
Vue.prototype.$SuccessMessage = (res, message,list,index) => {
  console.log("index",index)
  if (res.code === 200) {
    if (message) {
      Message.success(message)
    } else {
      Message.success("操作成功！")
    }
    if(list&&index){
      list.splice(index,1)
    }
  }


}


/**
 * 全局操作成功事件注册
 * @param fun
 * @param params
 * @param message
 */
Vue.prototype.$SuccessMessage = (res, message) => {
  if (res.code === 200) {
    if (message) {
      Message.success(message)
    } else {
      Message.success("操作成功！")
    }
  }


}


/**
 *
 * @param fun 事件
 * @param params 参数
 * @param type 1 表示修改 2表示删除 3表示添加
 * @param msg
 * @msg 消息
 */
Vue.prototype.$SuccessMessageBox = (fun, params, type, msg) => {
  console.log("fun type===========>", typeof (fun))
  let tipMessage = "确认执行该操作";
  if (msg) {
    tipMessage = msg
  }
  let tipSuccess = "操作成功";
  if (type === 1) {
    tipSuccess = "修改成功~"
    tipMessage = "确认修改?"
  }
  if (type === 2) {
    tipMessage = "确认删除?"
    tipSuccess = "删除成功~"
  }
  if (type === 3) {
    tipMessage = "确认添加?"
    tipSuccess = "添加成功~"
  }
  MessageBox.confirm(tipMessage, '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    fun(params).then(res => {
      if (res.code === 200) {
        Message.success(tipSuccess);
      }
    })

  }).catch(() => {
    Message.info("已取消该操作");
  });
}
