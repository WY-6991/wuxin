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

console.log(
  `https://gitee.com/wuxin0011`,
  'background:#35495e ; padding: 1px; border-radius: 3px 0 0 3px;  color: #fff',
  'background:#41b883 ; padding: 1px; border-radius: 0 3px 3px 0;  color: #fff',
  'background:transparent'
)

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
