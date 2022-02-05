import request from "../network/request";


/**
 * 添加评论
 * @param {comment} data
 * @returns
 */
export function addComment(data) {
    return request({
        url: '/comment/add',
        method: 'post',
        data
    })
}

/**
 * 添加评论
 * @param {reply} data
 * @returns
 */
export function addReply(data) {
    return request({
        url: '/comment/reply/add',
        method: 'post',
        data
    })
}

/**
 * 获取文章评论
 * @param current 页码
 * @param limit 大小
 * @param type 文章级别
 * @param blogId 文章id
 * @returns
 */
export function getCommentList(current,limit,type,blogId) {
    return request({
        url: '/comment/list',
        method: 'get',
        params:{
            current,limit,type,blogId
        }
    })
}