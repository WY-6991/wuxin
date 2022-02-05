<template>
<div class="ui segments m-toc toc-wrapper m-box" id="blog-content-container" v-if="$route.name==='Blog' &&isShowContents()">
    <div class="ui secondary segment"><i class="list ul icon"></i>本文目录</div>
    <div class="ui yellow segment">
        <div class="blog-js-toc"></div>
    </div>
</div>
</template>

<script>
import {
    mapState
} from "vuex";
export default {
    name: "Tocbot",
    data() {
        return {
            st: 0,
        };
    },

    watch: {
        //文章渲染完成时，生成目录
        loadingComplete() {
            if (this.loadingComplete && this.isShowContents) {
                this.initTocbot()
            }
        }
    },

    computed: {
        ...mapState("blog", ["loadingComplete"]),
    },

    mounted() {
        // 有可能组件创建比较慢，文章渲染已经完成，watch的时候，isBlogRenderComplete已经是true，监听不到 isBlogRenderComplete 的改变，也就不会执行watch中的方法
        // 就需要在 mounted 中init
        if (window.document.querySelector('.blog-js-toc-content')) {
            if (this.isShowContents) {
                this.initTocbot()
            }

        }

    },

    methods: {
        initTocbot() {
            console.log('object :>> 目录加载中。。。。');
            this.$tocbot.init({
                // Where to render the table of contents.
                tocSelector: '.blog-js-toc',
                // Where to grab the headings to build the table of contents.
                contentSelector: '.blog-js-toc-content',
                // Which headings to grab inside of the contentSelector element.
                headingSelector: 'h1,h2,h3,h4',
                // Element to add the positionFixedClass to.
                positionFixedSelector: '.m-toc',
                // Smooth scrolling enabled.
                scrollSmooth: true,
                // Smooth scroll duration.
                scrollSmoothDuration: 420,
                //到顶部导航条的距离
                scrollSmoothOffset: -55,
                // Headings offset between the headings and the top of the document (this is meant for minor adjustments).
                // Can also be used to account for scroll height discrepancies from the use of css scroll-padding-top
                headingsOffset: -18
            });

            // this.handleScroll()
        },

        handleScroll() {
            // 获取目录距离顶部高度 浏览器兼容问题
            let myTop = document.documentElement || document.body;
            // 获取目录
            let blog_contents = document.getElementById("blog-content-container");
            // 目录是否能够获取到
            if (blog_contents) {
                this.st = blog_contents.offsetTop;
                let _that = this;

                document.addEventListener("scroll", function () {
                    // 判断页面被卷去的高度是否超过目录距离浏览器顶部的距离
                    if (myTop.scrollTop >= _that.st) {
                        // 将目录位置固定
                        blog_contents.style.position = "fixed";
                        blog_contents.style.top = 0 + "px";
                    } else {
                        blog_contents.style.position = "relative";
                        blog_contents.style.top = _that.st + "px";
                    }
                });
            }

        },

        // 是否显示目录
        isShowContents() {
            let myBlog = document.querySelector('.m-pre-next-blog-container')
            console.log('object :>> ', window.innerHeight);
            console.log('myBlog. :>> ', myBlog.offsetTop);
            if (myBlog.offsetTop <= window.innerHeight) {
                return false;
            }
            return true;

        }
    },

    // 离开时候销毁这个组件
    // destroyed() {
    //     window.removeEventListener("scroll", this.handleScroll);
    //     this.$tocbot.destroy();
    // },
};
</script>

<style lang="scss" scoped>
#blog-content-container {
    position: relative;
    float: right;
    // width: 100% !important;
    right: 0;
    box-sizing: border-box;
    z-index: 2;

    .m-box {
        margin-top: 20px !important;
    }

    .m-toc {
        z-index: 10 !important;
    }

    .toc {
        overflow-y: auto;
    }

    .toc>ul {
        overflow: hidden;
        position: relative;
    }

    .toc>ul li {
        list-style: none;
    }

    .toc-list {
        list-style-type: none;
        margin: 0;
        padding-left: 10px;
    }

    .toc-list li a {
        display: block;
        padding: 4px 0;
        font-weight: 300;
    }

    .toc-list li a:hover {
        color: #46619b;
    }

    a.toc-link {
        color: currentColor;
        height: 100%;
    }

    .is-collapsible {
        max-height: 1000px;
        overflow: hidden;
        transition: all 300ms ease-in-out;
    }

    .is-collapsed {
        max-height: 0;
    }

    .is-position-fixed {
        position: sticky !important;
        top: 60px;
    }

    .is-active-link {
        font-weight: 700;
        color: #2f7fb4 !important;
    }

    .toc-link::before {
        background-color: #eee;
        content: " ";
        display: inline-block;
        height: 0;
        left: 0;
        margin-top: -1px;
        position: absolute;
        width: 2px;
    }

    .is-active-link::before {
        background-color: olive;
        position: relative;
    }
}

//@import 'tocbot/src/scss/tocbot';
</style>
