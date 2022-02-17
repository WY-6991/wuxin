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





}


export default {
    namespaced: true,
    state,
    getters,
    mutations,
    actions
}