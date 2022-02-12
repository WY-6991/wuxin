import {
    getCommentList,
    addReply,
    addComment
} from "@/api/comment";

import {
    SET_COMMENT_BLOG_ID,
    SET_COMMENT_USER,
    SET_COMMENT_CURRENT,
    SET_COMMENT_USER_ID,
    SET_COMMENT_TOTAL_PAGE,
    SET_COMMENT_LIST,
    SET_COMMENT_PARENT_ID,
    SET_COMMENT_COUNT,
    SET_LOADING,
} from "@/store/mutations-type";

import {
    setUser,
    getUser
} from "@/network/auth"
import {
    setTotalPage,
    setCommentCount
} from "@/utils/validate.js";
import {
    SET_CLEAN_CONTENT
} from "../mutations-type";


const state = {

    commentUser: {
        username: getUser().username ? getUser().username : '',
        email: getUser().email ? getUser().email : '',
        content: null,
        subscription: getUser().subscription ? getUser().subscription : true,
    },

    loading: false,
    parentCommentId: -1,
    totalPage: 0,
    current: 1,
    commentCount: 0,
    commentList: []


}


const mutations = {

    // 设置评论用户
    [SET_COMMENT_USER]: (state, user) => {
        state.commentUser.username = user.username
        state.commentUser.email = user.email
        state.commentUser.subscription = user.subscription
        // 将存储到cookie中
        setUser({'username': user.username, 'email': user.email, 'subscription': user.subscription});
    },

    // 设置评论用户
    [SET_CLEAN_CONTENT]: (state) => {
        // 将存储到cookie中
        state.commentUser.content = null
    },


    // 获取评论用户id，用户操作删除评论和回复
    [SET_COMMENT_USER_ID]: (state, userId) => {
        state.commentUser.userId = userId
    },

    // 获取current
    [SET_COMMENT_CURRENT]: (state, current) => {
        state.current = current
    },

    // 设置总页码
    [SET_COMMENT_TOTAL_PAGE]: (state, totalPage) => {
        state.totalPage = totalPage
    },

    // 设置评论列表
    [SET_COMMENT_LIST]: (state, commentList) => {
        state.commentList = commentList
    },

    // 设置parentID
    [SET_COMMENT_PARENT_ID]: (state, parentCommentId) => {
        state.parentCommentId = parentCommentId
    },

    // 统计评论数量
    [SET_COMMENT_COUNT]: (state, commentCount) => {
        state.commentCount = commentCount
    },

    // 统计评论数量
    [SET_LOADING]: (state, loading) => {
        state.loading = loading
    },

}

const getters = {
    commentUser: (state) => {
        return state.commentUser
    }
}

const actions = {

    /**
     * 设置评论用户
     * @param commit
     * @param user
     */
    setCommentUser({
                       commit
                   }, user) {
        commit(SET_COMMENT_USER, user)
    },

    /**
     * 设置当前页
     * @param commit
     * @param current 当前页
     */
    setCurrent({
                   commit
               }, current) {
        commit(SET_COMMENT_CURRENT, current)
    },

    /**
     * 设置页码
     * @param commit commit
     * @param totalPage 页码总数
     */
    setPage({
                commit
            }, totalPage) {
        commit(SET_COMMENT_TOTAL_PAGE, totalPage)
    },

    /**
     * 打开评论内容
     * @param commit commit
     * @param parentCommentId 评论楼层id
     */
    setParentCommentId({
                           commit
                       }, parentCommentId) {
        commit(SET_COMMENT_PARENT_ID, parentCommentId)
    },


    /**
     * 添加评论
     * @param commit commit
     * @param data 评论内容
     * @returns {Promise<res>}
     */
    addComment({
                   commit
               }, data) {
        return new Promise((resolve, reject) => {
            commit(SET_LOADING,true)
            addComment(data).then(res => {
                commit(SET_CLEAN_CONTENT, "")
                commit(SET_COMMENT_USER_ID, res.result)
                commit(SET_COMMENT_USER, {
                    'username': data.username,
                    'email': data.email,
                    'subscription': data.subscription,
                })
                commit(SET_LOADING,false)
                resolve(res)
            }).catch(error => {
                commit(SET_LOADING,false)
                reject(error)
            })
        })
    },

    /**
     * 添加回复
     * @param commit commit
     * @param data 回复内容参数信息
     * @returns {Promise<res>}
     */
    addReply({
                 commit
             }, data) {
        return new Promise((resolve, reject) => {
            commit(SET_LOADING,true)
            addReply(data).then(res => {
                commit(SET_CLEAN_CONTENT, "")
                commit(SET_COMMENT_USER, {
                    "username": data.replyUsername,
                    "email": data.replyEmail,
                    "subscription": data.subscription
                })
                commit(SET_LOADING,false)
                resolve(res)
            }).catch(error => {
                commit(SET_LOADING,false)
                reject(error)
            })
        })
    },


    /**
     * 获取评论列表
     * @param commit commit
     * @param query 查询条件
     * @returns {Promise<res>}
     */
    getCommentList({
                       commit
                   }, query) {
        return new Promise((resolve, reject) => {
            getCommentList(state.current, 5, query.type, query.blogId).then(res => {
                const {page, commentCount} = res
                const {records, pages} = page
                commit(SET_COMMENT_LIST, records)
                commit(SET_COMMENT_COUNT, commentCount)
                commit(SET_COMMENT_TOTAL_PAGE, pages)
                resolve(res)
            }).catch(error => {
                reject(error)
            })


        })
    },


}


export default {
    namespaced: true,
    state,
    getters,
    mutations,
    actions
}