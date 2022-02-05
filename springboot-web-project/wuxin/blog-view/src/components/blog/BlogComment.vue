<template lang="html">
<div>
    <CommentForm :comment-type="commentType" :type="query.type" :id="blogId" @addComment="addMyComment" ref="commentForm" />
    <CommentList @addReply="addMyReply" @setParentId="setParentId" :type="query.type" :id="blogId" :comment-list="commentList" :commentCount="commentCount" :parentCommentId="parentCommentId" :user-id="commentUser.userId" />
    <MyPagination :current="current" :totalPage="commentTotalPage" @current-page="changePage" />
</div>
</template>

<script>
import CommentForm from "@/components/comment/CommentForm";
import CommentList from "@/components/comment/CommentList";
import {
    mapActions,
    mapState
} from "vuex";

export default {
    name: "BlogComment",
    components: {
        CommentList,
        CommentForm,
    },
    props: {
        blogId: {
            required: true,
        },

        commentEnabled: {
            type: Boolean,
            default: true
        },
    },
    data() {
        return {
            commentType: "comment",
            query: {
                type: 1,
                blogId: ''
            }
        };
    },

    computed: {
        ...mapState("comment", [
            "current",
            "commentList",
            "commentTotalPage",
            "parentCommentId",
            "commentCount",
            "commentUser",
        ]),
    },
    methods: {
        ...mapActions("comment", [
            "addComment",
            "addReply",
            "getCommentList",
            "setCurrent",
            "setParentCommentId",
        ]),

        addMyComment(comment) {
            this.addComment(comment).then((res) => {
                if (res.code === 200) {
                    this.$notify.success("发表成功！");
                    this.getData()
                }
            });
        },

        addMyReply(reply) {
            this.addReply(reply).then((res) => {
                if (res.code === 200) {
                    this.$notify.success("发表成功！");
                   this.getData()
                }
            });
        },

        setParentId(id) {
            this.setParentCommentId(id)
        },

        getData() {
            if (this.commentEnabled) {
                this.getCommentList(this.query)
            }
        },

        changePage(page) {
            // 设置页码 设置blogId
            this.setCurrent(page);
            // 加载commentList
            this.getData()
        },
    },

    mounted() {
        this.query.blogId = this.blogId
        this.getData()
        this.setParentCommentId(-1)
    },
};
</script>
