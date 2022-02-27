import request from '@/utils/request'

/**
 * 获取全部tag
 * @returns {AxiosPromise}
 */
export function getAllTagList() {
  return request({
    url: '/tag/list',
    method: 'get',
  })
}


/**
 * 分页获取tagList
 * @returns {AxiosPromise}
 */
export function getTagList(data) {
  return request({
    url: '/admin/tag/list',
    method: 'post',
    data
  })
}

/**
 * 删除tag
 * @returns {AxiosPromise}
 */
export function delTag(tagId) {
  return request({
    url: `/admin/tag/del`,
    method: 'delete',
    params: {
      tagId
    }
  })
}

/**
 * 修改tag
 * @param data
 * @returns {AxiosPromise}
 */
export function updateTag(data) {
  return request({
    url: '/admin/tag/update',
    method: 'post',
    data
  })
}

/**
 * 添加tag
 * @param data
 * @returns {AxiosPromise}
 */
export function createTag(data) {
  return request({
    url: '/admin/tag/add',
    method: 'post',
    data
  })
}

/**
 * 修改tagColor
 * @param data
 * @returns {AxiosPromise}
 */
export function updateTagColor(data) {
  return request({
    url: '/admin/tag/update/color',
    method: 'post',
    data
  })
}









