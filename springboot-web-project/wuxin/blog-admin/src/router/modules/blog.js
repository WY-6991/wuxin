import Layout from '@/layout'

const blogRouter = {
  path: '/blog',
  component: Layout,
  redirect: '/blog/list',
  meta: {
    title: '博客管理',
    icon: 'blog'
  },
  children: [

    {
      path: 'list',
      name: 'BlogList',
      component: () => import('@/views/blog/blog/list/index'),
      meta: {
        title: '文章列表',
        noCache: true,
        icon: 'list'
      }
    },

    {
      path: 'add',
      name: 'BlogAdd',
      component: () => import('@/views/blog/blog/add/index.vue'),
      meta: {
        title: '发布文章',
        noCache: true,
        icon: 'blog-write'
      }
    },

    {
      path: 'edit/:blogId',
      name: 'BlogEdit',
      component: () => import('@/views/blog/blog/edit/index.vue'),
      hidden: true,
      meta: {
        title: '编辑文章',
        noCache: true
      }
    },

    {
      path: 'comment/:blogId',
      name: 'BlogComment',
      component: () => import('@/views/blog/blog/list/comment/index.vue'),
      hidden: true,
      meta: {
        title: '文章评论',
        noCache: true,
        icon: 'comment'
      }
    },
    {
      path: 'category',
      component: () => import('@/views/blog/label/category'),
      name: 'Category',
      meta: {
        title: '分类',
        noCache: true,
        icon: 'category'
      }
    },
    {
      path: 'tag',
      component: () => import('@/views/blog/label/tag'),
      name: 'Tag',
      meta: {
        title: '标签',
        noCache: true,
        icon: 'tag'
      }
    },

    {
      path: 'comment',
      name: 'Comment',
      component: () => import('@/views/blog/comment'),
      meta: {
        title: '评论',
        noCache: true,
        icon: 'comment'
      }
    },
  ]

}

export default blogRouter
