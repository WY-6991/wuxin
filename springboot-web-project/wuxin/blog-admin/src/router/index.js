import Vue from 'vue'
import Router from 'vue-router'
import Layout from '@/layout'
import userRouter from '@/router/modules/user'
import pageRouter from '@/router/modules/page'
import logRouter from '@/router/modules/log'
import systemRouter from '@/router/modules/system'
import countRouter from '@/router/modules/count'

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
  pageRouter,
  userRouter,
  logRouter,
  systemRouter,
  countRouter,
  {
    path: '*',
    component: () => import('@/views/error-page/404'),
    hidden: true
  }
]

/**
 * 设置权限路由
 * @type {any}
 */
export const asyncRoutes = [
  {
    path: '/permission',
    component: Layout,
    redirect: '/permission/page',
    alwaysShow: true,
    name: 'Permission',
    meta: {
      title: '权限',
      icon: 'lock',
      roles: ['user', 'admin', 'root']
    },
    children: [
      {
        path: 'page',
        component: () => import('@/views/permission/page'),
        name: 'PagePermission',
        meta: {
          title: '页面指令',
          roles: ['admin', 'root']
        }
      },
      {
        path: 'directive',
        component: () => import('@/views/permission/directive'),
        name: 'DirectivePermission',
        meta: {
          title: '指令许可',
          roles: ['admin', 'root']
        }
      },
      {
        path: 'role',
        component: () => import('@/views/permission/role'),
        name: 'RolePermission',
        meta: {
          title: '角色权限',
          roles: ['root']
        }
      }
    ]
  },

  {
    path: '/icon',
    component: Layout,
    children: [
      {
        path: 'index',
        component: () => import('@/views/icons/index'),
        name: 'Icons',
        meta: { title: '图标', icon: 'icon', noCache: true }
      }
    ]
  },
  { path: '*', redirect: '/404', hidden: true }
]

window.sessionStorage.setItem('constantRoutes',JSON.stringify(constantRoutes))
// console.log('window.sessionStorage.getItem(\'constantRoutes\')========>',JSON.parse(window.sessionStorage.getItem('constantRoutes')))
const createRouter = () => new Router({
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes,
  mode: 'history'
})

const router = createRouter()

export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher
}

export default router
