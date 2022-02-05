import request from '@/utils/request'

/**
 * 添加archive
 * @param data
 * @returns {AxiosPromise}
 */
export function addArchive(data) {
  return request({
    url: '/admin/archive/add',
    method: 'post',
    data
  })
}


/**
 * 获取archiveList
 * @param data
 * @returns {list}
 */
export function getArchiveList(data) {
  return request({
    url: '/admin/archive/list',
    method: 'post',
    data
  })
}


/**
 * updateArchive
 * @param data
 * @returns {message}
 */
export function updateArchive(data) {
  return request({
    url: '/admin/archive/list',
    method: 'post',
    data
  })
}


/**
 * 删除
 * @param id archiveID
 * @returns {message}
 */
export function delArchive(id) {
  return request({
    url: `/admin/archive/del/${id}`,
    method: 'delete',
  })
}


/**
 * 获取全部文章列表
 * @returns {message}
 */
export function getAllBlog() {
  return request({
    url: `/admin/blog/all/list`,
    method: 'get',
  })
}
