<template>
<div >
    <BlogList :getList="getBlogList" :blogList="blogList" :totalPage="totalPage" />
</div>
</template>

<script>
import BlogList from "@/components/blog/BlogList";
import Title from '@/components/common/Title'
import {
    getBlogListByTagName
} from "@/api/tag";
import {
    setTotalPage
} from '@/utils/validate.js'

export default {
    data() {
        return {
            blogList: '',
            getBlogList: () => {},
            getBlogListFinish: false,
            // 页面参数
            current: 1,
            totalPage: 1,
            name: 'vue'
        }
    },
    components: {
        BlogList,
        Title
    },

    methods: {
        // 获取基本blog参数
        getList(current) {
            getBlogListByTagName({
                'current': current,
                'limit': 5,
                'keywords': this.name
            }).then(res => {
                this.blogList = res.result
                if (res.result) {
                    this.totalPage = setTotalPage(res.result.length, 5)
                }

                this.$nextTick(() => {
                    this.$primsjs.highlightAll()
                })
            })
        },

    },

    beforeRouteEnter(to, from, next) {
        next(vm => {
            vm.name = to.params.name
            vm.getList(vm.current)
        })
    },

    beforeRouteUpdate(to, from, next) {
        this.name = to.params.name
        this.getList(this.current)
        next()
    },
    beforeRouteLeave(to, from, next) {
        next()
    }

}
</script>

