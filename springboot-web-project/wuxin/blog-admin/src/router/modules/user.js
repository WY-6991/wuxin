import Layout from '@/layout'

const userRouter = {
  path: '/user',
  component: Layout,
  redirect: '/user/list',
  name: 'user',
  meta: {
    title: '用户管理',
    icon: 'peoples'
  },
  children: [
    {
      path: 'profile',
      component: () => import('@/views/profile/index'),
      hidden: true,
      meta: {
        title: '我的信息', noCache: true
      }
    },

    {
      path: 'list',
      component: () => import('@/views/user/user'),
      meta: { title: '用户列表', noCache: true, icon: 'list' }

    },

    {
      path: 'role',
      component: () => import('@/components/RouterView/index'),
      name: 'role',
      meta: { title: '权限', noCache: true, icon: 'role' },
      children: [
        {
          path: 'list',
          component: () => import('@/views/user/role/list'),
          name: 'list',
          meta: { title: '列表', noCache: true, icon: 'role-list' }

        },
        {
          path: 'user',
          component: () => import('@/views/user/role/user'),
          name: 'user',
          meta: { title: '用户', noCache: true, icon: 'user-role' }

        }
      ]

    }
  ]

}

export default userRouter
