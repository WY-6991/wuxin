import Vue from "vue";
import VueRouter from "vue-router";

import Layout from "@/layout/index.vue";

Vue.use(VueRouter);


// 重复点击路由报错问题
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
    return originalPush.call(this, location).catch(err => err)
}

// 路由注册
const routes = [

    {
        path: '/',
        component: Layout,
        redirect: '/welcome',
        children: [{
                path: "index",
                component: () => import("@/views/home/index.vue"),
                name: 'Index',
                meta: {
                    title: "首页",
                },
            },


            {
                path: "about",
                name: 'About',
                component: () => import("@/views/about/index.vue"),
                meta: {
                    title: "关于",
                },
            },
            {
                path: "category/:name",
                name: 'Category',
                component: () => import("@/views/category/index.vue"),
                meta: {
                    title: "分类",
                },
            },
            {
                path: "blog/:blogId",
                name: 'Blog',
                component: () => import("@/views/blog/index.vue"),
                meta: {
                    title: "详情",
                },
            },


            {
                path: "tag/:name",
                name: 'Tag',
                component: () => import("@/views/tag/index.vue"),
                meta: {
                    title: "标签",
                },
            },
            {
                path: "moments",
                name: 'Moment',
                component: () => import("@/views/moment/index.vue"),
                meta: {
                    title: "动态",
                },
            },
            {
                path: "friends",
                name: 'Friend',
                component: () => import("@/views/friend/index.vue"),
                meta: {
                    title: "友链",
                },
            },
            {
                path: "update",
                name: 'Update',
                component: () => import("@/views/timeline/index.vue"),
                meta: {
                    title: "通知",
                },
            },
            {
                path: "archive",
                name: 'Archive',
                component: () => import("@/views/archive/index.vue"),
                meta: {
                    title: "我的收藏",
                },
            }
        ]
    },

    {
        path: '/welcome',
        component: () => import('@/views/welcome/index.vue'),
        meta: {
            title: '欢迎'
        }
    },


    {
        path: '*',
        component: () => import('@/views/error/index.vue'),
        meta: {
            title: '404'
        }
    }

]

const router = new VueRouter({
    routes,
    mode: "history",
});


router.beforeEach((to, from, next) => {
    document.title = to.meta.title
    next()
})


export default router;