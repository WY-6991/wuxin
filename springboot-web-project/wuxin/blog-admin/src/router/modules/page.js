import Layout from '@/layout'

const pageRouter = {
  path: '/page',
  component: Layout,
  redirect: '/page/about',
  meta: {
    title: '页面管理',
    icon: 'page'
  },
  children: [
    {
      path: 'moment',
      component: () => import('@/components/RouterView/index'),
      meta: { title: '动态', noCache: true, icon: 'guide' },
      redirect: '/page/moment/list',
      children: [

        {
          path: 'list',
          name: 'MomentList',
          component: () => import('@/views/pages/moment/list/index.vue'),
          meta: { title: '动态列表', noCache: true, icon: 'list' }
        },

        {
          path: 'add',
          name: 'MomentAdd',
          component: () => import('@/views/pages/moment/add'),
          meta: { title: '发布动态', noCache: true, icon: 'blog-write' }
        },

        {
          path: 'update/:momentId',
          name: 'MomentEdit',
          component: () => import('@/views/pages/moment/edit'),
          hidden: true,
          meta: { title: '编辑', noCache: true }
        }
      ]
    },

    {
      path: 'about',
      component: () => import('@/views/pages/about'),
      name: 'About',
      meta: { title: '关于', noCache: true, icon: 'about-me' }
    },
    {
      path: 'friend',
      component: () => import('@/views/pages/friend'),
      name: 'FriendList',
      meta: { title: '友情链接', noCache: true, icon: 'friend' }
    },
    //
    {
      path: 'archive',
      component: () => import('@/views/pages/archive'),
      name: 'ArchiveList',
      meta: { title: '归档', noCache: true, icon: 'archive' }
    },
    {
      path: 'update',
      component: () => import('@/views/pages/update'),
      name: 'Update',
      meta: { title: '更新', noCache: true, icon: 'update' }
    }

  ]

}

export default pageRouter
