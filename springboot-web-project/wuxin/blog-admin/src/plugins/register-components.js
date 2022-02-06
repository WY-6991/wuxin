import Vue from 'vue'
import vueWaves from '../directive/waves'
import * as filters from '../filters'
import '../icons/index'
import MySearchHeader from '@/components/MyComponents/MySearchHeader'
import Pagination from '@/components/Pagination'

// todo 全局注册用户角色信息 减少服务器压力 这里后台服务器也是经过验证的 但是前台添加了判断，会减少后端验证
// todo 这里只是粗略使用权限来操作，后续或许会添加 具体操作权限来达到控制权限的目的
import store from '@/store'

// /*全局注册分页组件*/
Vue.component(Pagination.name, Pagination)
// /*全局注册表头搜索组件*/
Vue.component(MySearchHeader.name, MySearchHeader)
// 注册全局指令
Vue.use(vueWaves)
// 遍历过滤器并注册
Object.keys(filters).forEach(key => {
  Vue.filter(key, filters[key])
})
const roles = store.state.user.roles
// 全局混入
Vue.mixin({
  computed: {
    isRoot() {
      // 用户是否具备root权限
      return roles.indexOf('root') !== -1
    }
  }
})
