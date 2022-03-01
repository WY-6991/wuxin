import Vue from 'vue'
import VueRouter from 'vue-router'
import Layout from '../layout/Index'

Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        component: Layout,
        redirect: '/index',
        children: [
            {
                path: '/index',
                name: 'Index',
                component: () => import('../views/Index.vue'),
                meta: {
                    title: '首页'
                }
            },
            {
                path: 'about',
                name: 'About',
                component: () => import('../views/About.vue'),
                meta: {
                    title: '关于'
                }
            },
        ]
    },

    {
        path: '/login',
        name: 'Login',
        component: () => import('../views/Login.vue')
    },
    {
        path: '*',
        name: 'Error',
        component: () => import('../views/Error.vue')
    },

]

const router = new VueRouter({
    routes
})

export default router
