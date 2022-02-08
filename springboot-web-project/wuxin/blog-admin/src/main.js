import Vue from 'vue'
import App from './App'
import store from './store'
import router from './router'
// 注册全局样式
import '@/styles/index.scss'
// 注册vditor 样式
import 'vditor/dist/index.css'
// 注册组件、插件
import './plugins/index'

Vue.config.productionTip = false

console.log('\n\n %c  gitee %c '
  .concat('https://gitee.com/wuxin0011', ''), 'background: rgb(199, 29, 35); padding: 1px; border-radius: 3px 0 0 3px; color: #fff', 'border-radius: 0 3px 3px 0; color:#fff')
console.log(
  '\n\n %c  github %c '
    .concat('https://github.com/WY-6991/wuxin', ''), 'background: rgb(36, 41, 47); padding: 1px; border-radius: 3px 0 0 3px; color: #fff', 'border-radius: 0 3px 3px 0; color: #fff')
new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
