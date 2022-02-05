import request from '@/utils/request'

/**
 * 统计首页category blog
 * @returns {AxiosPromise} List<Object>
 */
export function getDashboardCategory() {
  return request({
    url: '/dashboard/category',
    method: 'get',
  })
}

/**
 * 统计首页 tag blog
 * @returns {AxiosPromise} List<Object>
 */
export function getDashboardTag() {
  return request({
    url: '/dashboard/tag',
    method: 'get',
  })
}


/**
 * 统计文章
 * @returns {AxiosPromise}
 */
export function getDashboardCardCount() {
  return request({
    url: '/dashboard/count',
    method: 'get',
  })
}


/**
 * 统计评论
 * @returns {AxiosPromise}
 */
export function getCommentCount() {
  return request({
    url: '/dashboard/comment/count',
    method: 'get',
  })
}
