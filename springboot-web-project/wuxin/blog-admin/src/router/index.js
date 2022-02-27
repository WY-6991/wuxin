import Vue from 'vue'
import Router from 'vue-router'
import Layout from '@/layout'
import userRouter from '@/router/modules/user'
import blogRouter from '@/router/modules/blog'
import pageRouter from '@/router/modules/page'
import logRouter from '@/router/modules/log'
import systemRouter from '@/router/modules/system'
import countRouter from '@/router/modules/count'
import repoRouter from '@/router/modules/repo'
import store from '@/store/index'
import { MessageBox } from 'element-ui'

Vue.use(Router)

export const constantRoutes = [
  {
    path: '/redirect',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/redirect/:path(.*)',
        component: () => import('@/views/redirect/index')
      }
    ]
  },
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true,
    meta: {
      title: '登录'
    }
  },

  {
    path: '/auth-redirect',
    component: () => import('@/views/login/auth-redirect'),
    hidden: true
  },

  {
    path: '/401',
    component: () => import('@/views/error-page/401'),
    hidden: true
  },
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [
      {
        path: 'dashboard',
        name: 'Dashboard',
        component: () => import('@/views/dashboard/index'),
        meta: { title: '仪表盘', icon: 'dashboard', affix: true }
      }
    ]
  },
  blogRouter,
  pageRouter,
  userRouter,
  logRouter,
  systemRouter,
  repoRouter,
  countRouter,
  {
    path: '*',
    component: () => import('@/views/error-page/404'),
    hidden: true
  }
]

window.sessionStorage.setItem('constantRoutes', JSON.stringify(constantRoutes))
// const router = new Router({
//   scrollBehavior: () => ({ y: 0 }),
//   routes: constantRoutes,
//   mode: 'history'
// })
const router = new Router({
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes,
  mode: 'history'
})

// 挂载路由守卫
router.beforeEach((to, from, next) => {
  if (to.path !== '/login') {
    // 获取token
    const token = store.getters.token ? store.getters.token : ''
    if (!token) {
      MessageBox.confirm('获取不到用户令牌', '是否重新登录？', {
        distinguishCancelAndClose: true,
        type: 'warning',
        confirmButtonText: '确认',
        showCancelButton: false
      })
        .then(() => {
          return next('/login')
        })
        .catch(() => {
          return next('/login')
        })
    }
  }
  next()
})

export default router
