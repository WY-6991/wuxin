import request from '@/utils/request'
/**
 * 我的内容下的评论列表
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
 */
export function delComment(id) {
  return request({
    url: `/admin/comment/del`,
    method: 'delete',
    params: {
      id
    }
  })
}

/**
 * 修改评论内容
 */
export function updateComment(data) {
  return request({
    url: `/admin/comment/update`,
    method: 'put',
    data
  })
}

/**
 * 删除全部评论
 */
export function delCommentAll() {
  return request({
    url: `/admin/comment/del/all`,
    method: 'delete'
  })
}

/**
 * 删除回复
 */
export function delReply(replyId) {
  return request({
    url: `/admin/comment/reply/del`,
    method: 'delete',
    params: {
      replyId
    }
  })
}

/**
 * 修改回复
 */
export function updateReply(data) {
  return request({
    url: `/admin/comment/reply/update`,
    method: 'put',
    data
  })
}

/**
 * 删除全部回复内容
 */
export function delReplyByCommentId(commentId) {
  return request({
    url: `/admin/del/reply/list/by/commentId`,
    method: 'delete',
    params: {
      commentId
    }
  })
}
