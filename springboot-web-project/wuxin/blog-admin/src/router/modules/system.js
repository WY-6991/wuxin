
import Layout from '@/layout'

const systemRouter = {
  path: '/web',
  component: Layout,
  redirect: '/web/setting',
  meta: {
    title: '网站设置',
    icon: 'system'
  },
  children: [

    {
      path: 'setting',
      name: 'WebSetting',
      component: () => import('@/views/system/web/index.vue'),
      meta: { title: '基本设置', noCache: true ,icon:'web'}
    },

    {
      path: 'github',
      name: 'Github',
      component: () => import('@/views/system/github-repo'),
      meta: { title: '图床', noCache: true ,icon:'clipboard'}
    },
    {
      path: 'background',
      name: 'Background',
      component: () => import('@/views/system/background/index.vue'),
      meta: { title: '背景',noCache: true,icon:'picture' },

    },

    {
      path: 'music',
      name: 'Music',
      component: () => import('@/views/system/music/Music'),
      meta: { title: '音乐', noCache: true ,icon:'music'}
    }
  ]
}
export default systemRouter
