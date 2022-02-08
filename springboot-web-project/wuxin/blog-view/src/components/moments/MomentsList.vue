<template lang="html">
<div class="container typo" >
    <sui-comment-group threaded v-for="(item,index) in momentsList" :key="item.momentId">
        <sui-comment >
                <sui-comment-content >
                    <sui-comment-avatar :src="item.avatar" />
                    <a is="sui-comment-author" style="text-decoration: none;" :style="bgColor">&nbsp;&nbsp;{{ item.username }}</a>
                    <sui-comment-metadata>
                        <div :style="bgColor" >{{ item.createTime |formatDateTime}}</div>
                    </sui-comment-metadata>
                </sui-comment-content>
                <sui-comment-group v-viewer >
                    <sui-card class="link" :style="bgColor" >
                        <sui-card-content v-html="item.content" />
                    </sui-card>
                </sui-comment-group>
            </sui-comment>
    </sui-comment-group>
    <MyPagination :get-list="getList" :totalPage="totalPage" />
</div>
</template>

<script>
import {
    getDateDiff
} from "@/utils/validate.js";

export default {
    name: "MomentsList",

    filters: {
        formatDateTime: function (value) {
            return getDateDiff(value);
        },
    },
    data() {
        return {
            color: "white",
            visible: true,
        };
    },
    props: {
        momentsList: {
            type: Array,
            default: [],
        },
        getList: {
            type: Function,
            required: true,
        },
        totalPage: {
            type: Number,
            required: true,
            default: 0,
        },
    },

    methods: {
        unLine() {
            this.$message.warning("该功能还未上线哦！");
        },
    },
};
</script>

<style scoped>
.card {
    position: relative;
    width: 100%;
    height: 100%;
    margin: 15px 10px !important;
    padding: 10px;
}

.ui .comment>.avatar {
    width: 50px;
    height: 50px;
    border-radius: 50%;
}

.ui .comment>.avatar>img {
    width: 50px;
    height: 50px;
    border-radius: 50%;
}

.ui.comment>.avatar>img {
    width: 100px !important;
    height: 55px !important;
    border-radius: 50%;
}

img {
    width: 55px;
    height: 55px;
    border-radius: 50%;
}

.typo a {
    border-bottom: 1px solid #fff;
}

.typo a:hover {
    text-decoration: none;
}
</style>
