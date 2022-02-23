import Layout from '@/layout'

const logRouter = {
  path: '/log',
  component: Layout,
  redirect: '/log/login',
  meta: {
    title: '日志管理',
    icon: 'log'
  },
  children: [
    {
      path: 'login',
      component: () => import('@/views/log/login/index.vue'),
      meta: { title: '登录日志', noCache: true, icon: 'login' }
    },
    {
      path: 'access',
      component: () => import('@/views/log/access/index.vue'),
      meta: { title: '访问日志', noCache: true, icon: 'access' }
    },
    {
      path: 'operation',
      component: () => import('@/views/log/operation/index.vue'),
      meta: { title: '操作日志', noCache: true, icon: 'operation' }
    },

    {
      path: 'exception',
      component: () => import('@/views/log/exception/index.vue'),
      meta: { title: '异常日志', noCache: true, icon: 'bug' }
    },

    {
      path: 'file',
      component: () => import('@/views/log/upload/index.vue'),
      meta: { title: '文件日志', icon: 'picture', noCache: true }
    }

  ]
}

export default logRouter
