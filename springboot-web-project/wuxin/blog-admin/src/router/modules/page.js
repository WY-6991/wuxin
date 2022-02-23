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
      path: 'blog',
      component: () => import('@/components/RouterView/index'),
      meta: { title: '博客', noCache: true, icon: 'blog' },
      redirect: '/page/blog/list',
      children: [
        {
          path: 'list',
          name: 'BlogList',
          component: () => import('@/views/pages/blog/list/index'),
          meta: { title: '文章列表', noCache: true, icon: 'list' }
        },

        {
          path: 'add',
          name: 'BlogAdd',
          component: () => import('@/views/pages/blog/add/index.vue'),
          meta: { title: '发布文章', noCache: true, icon: 'blog-write' }
        },

        {
          path: 'edit/:blogId',
          name: 'BlogEdit',
          component: () => import('@/views/pages/blog/edit/index.vue'),
          hidden: true,
          meta: { title: '编辑文章', noCache: true }
        },

        {
          path: 'comment/:blogId',
          name: 'BlogComment',
          component: () => import('@/views/pages/blog/list/comment/index.vue'),
          hidden: true,
          meta: { title: '文章评论', noCache: true, icon: 'comment' }
        }
      ]
    },

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
      path: 'label',
      component: () => import('@/components/RouterView/index'),
      meta: { title: '标签', noCache: true, icon: 'tree' },
      redirect: '/page/label/list',
      children: [

        {
          path: 'category',
          component: () => import('@/views/pages/label/category'),
          name: 'Category',
          meta: { title: '分类', noCache: true, icon: 'category' }
        },
        {
          path: 'tag',
          component: () => import('@/views/pages/label/tag'),
          name: 'Tag',
          meta: { title: '标签', noCache: true, icon: 'tag' }
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
      path: 'comment',
      name: 'Comment',
      component: () => import('@/views/pages/comment'),
      meta: { title: '评论', noCache: true, icon: 'comment' }
    },
    //
    {
      path: 'update',
      component: () => import('@/views/pages/update'),
      name: 'Update',
      meta: { title: '更新', noCache: true, icon: 'update' }
    }

  ]

}

export default pageRouter
