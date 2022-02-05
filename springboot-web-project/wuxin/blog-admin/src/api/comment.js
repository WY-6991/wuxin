import request from '@/utils/request'


/**
 * 我的内容下的评论列表
 * @param data { current:1,limit:10,keywords:null,type=1||2||3}
 * @returns {AxiosPromise}
 */
export function getCommentList(data) {
  return request({
    url: '/admin/comment/list',
    method: 'post',
    data
  })
}

/**
 * 删除评论
 * @param id
 * @returns {AxiosPromise}
 */
export function delComment(id) {
  return request({
    url: `/admin/comment/del`,
    method: 'delete',
    params:{
      id
    }
  })
}


/**
 * 删除评论
 * @param id
 * @returns {AxiosPromise}
 */
export function updateComment(data) {
  return request({
    url: `/admin/comment/update`,
    method: 'put',
    data
  })
}

/**
 * 删除回复
 * @param id
 * @returns {AxiosPromise}
 */
export function delReply(replyId) {
  return request({
    url: `/admin/comment/reply/del`,
    method: 'delete',
    params:{
      replyId
    }

  })
}

/**
 * 删除评论
 * @param id
 * @returns {AxiosPromise}
 */
export function updateReply(data) {
  return request({
    url: `/admin/comment/replu/update`,
    method: 'put',
    data
  })
}
