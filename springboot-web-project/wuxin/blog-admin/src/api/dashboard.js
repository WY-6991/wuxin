import request from '@/utils/request'

/**
 * 统计首页category blog
 */
export function getDashboardCategory() {
  return request({
    url: '/dashboard/category',
    method: 'get',
  })
}

/**
 * 统计首页 tag blog
 */
export function getDashboardTag() {
  return request({
    url: '/dashboard/tag',
    method: 'get',
  })
}


/**
 * 四个card 文章、访问量、浏览量、评论量
 */
export function getDashboardCardCount() {
  return request({
    url: '/dashboard/count',
    method: 'get',
  })
}


/**
 * 获取当天评论
 */
export function getCommentCount() {
  return request({
    url: '/dashboard/comment/count',
    method: 'get',
  })
}


/**
 * 获取七天访问量和浏览量
 */
export function getAccessLoginCount() {
  return request({
    url: '/dashboard/access/login/count',
    method: 'get',
  })
}
