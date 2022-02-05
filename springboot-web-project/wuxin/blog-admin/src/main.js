import Vue from 'vue'
import App from './App'
import store from './store'
import router from './router'
import Cookies from 'js-cookie'
import vueWaves from './directive/waves'
import Element from 'element-ui'
import './plugins/index'
import jQuery from 'jquery'


// 注册全局组件
import './plugins/register-components'

// 注册全局事件
import './utils/event'


// a modern alternative to CSS resets
// import 'normalize.css/normalize.css'


import './styles/element-variables.scss'
import enLang from 'element-ui/lib/locale/lang/zh-CN'

// 自定义样式
import '@/styles/index.scss'



import './icons'
import * as prismjs from 'prismjs'
// import 'prismjs/themes/prism-okaidia.css'
import "vditor/dist/index.css";


import * as filters from './filters'

import * as echarts from 'echarts';




/*全局导入echarts*/
Vue.prototype.$echarts = echarts
Vue.prototype.$prismjs = prismjs
// Vue.prototype.$ = jQuery



Vue.use(vueWaves)



// 中文包
Vue.use(Element, {
  size: Cookies.get('size') || 'medium', // set element-ui default size
  locale: enLang // 如果使用中文，无需设置，请删除
})

// 注册全部过滤器
Object.keys(filters).forEach(key => {
  Vue.filter(key, filters[key])
})


//关闭提示
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
