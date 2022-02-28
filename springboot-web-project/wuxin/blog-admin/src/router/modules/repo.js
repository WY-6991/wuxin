import Layout from '@/layout'

const repoRouter = {
  path: '/repo',
  component: Layout,
  redirect: '/repo/token',
  meta: {
    title: '图床配置',
    icon: 'picture'
  },
  children: [
    {
      path: 'token',
      component: () => import('@/views/repo/token/index.vue'),
      meta: {
        title: '配置',
        noCache: true,
        icon: 'system'
      }
    },

    {
      path: 'user',
      component: () => import('@/views/repo/user/index.vue'),
      meta: {
        title: '图床',
        noCache: true,
        icon: 'picture'
      }
    }

  ]
}

export default repoRouter
