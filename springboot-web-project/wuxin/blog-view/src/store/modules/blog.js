import {
    getDetailBlog,
    getChatUrl,
    beforeBlog,
    nextBlog,
} from "@/api/blog";



import {
    SET_LOADING_COMPLETE,
} from "@/store/mutations-type";



const state = {
    loadingComplete:false,
}



const mutations = {




    // 加载完成
    [SET_LOADING_COMPLETE]: (state, load) => {
        state.loadingComplete = load
    },

}

const actions = {


    /**
     * 获取blogDetail
     * @param {commit} commit 
     * @param {blogID} blogId 
     * @returns 
     */
    getDetailBlog({
        commit
    }, blogId) {
        return new Promise((resolve, reject) => {
            getDetailBlog(blogId).then(res => {
                resolve(res)
            }).catch(error => {
                reject(error)
            })
        })
    },


    /**
     * 获取赞赏图片
     * @param {commit} commit 
     * @param {userId} userId 
     * @returns 
     */
    getChatUrl({
        commit
    }, userId) {
        return new Promise((resolve, reject) => {
            getChatUrl(userId).then(res => {
                resolve(res)
            }).catch(error => {
                reject(error)
            })
        })
    },




    /**
     * 上一篇blog
     * @param {commit} commit 
     * @param {blogId} blogId 
     * @returns 
     */
    beforeBlog({
        commit
    }, blogId) {
        return new Promise((resolve, reject) => {
            beforeBlog(blogId).then(res => {
                resolve(res)
            }).catch(error => {
                reject(error)
            })
        })
    },


    /**
     * 下一篇blog
     * @param {commit} commit 
     * @param {blogId} blogId 
     * @returns 
     */
    nextBlog({
        commit
    }, blogId) {
        return new Promise((resolve, reject) => {
            nextBlog(blogId).then(res => {
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
    mutations,
    actions
}