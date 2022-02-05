import Layout from '@/layout'

const countRouter = {
  path: '/count',
  component: Layout,
  redirect: '/count/access',
  meta: {title: '数据统计', noCache: true, icon: 'login'},
  children: [
    {
      path: 'assess',
      component: () => import('@/views/log/login/index.vue'),
      meta: {title: '访问统计', noCache: true, icon: 'login'}
    },


  ]
}

export default countRouter
