import request from '@/utils/request'


/*===================================backgroundMap=============================*/


/**
 * 获取动态列表
 * @returns {list}
 */
export function getBackgroundMap() {
  return request({
    url: '/background/list',
    method: 'get',
  })
}

/**
 * 获取动态列表
 * @param id
 * @returns {msg}
 */
export function delBackgroundMap(id) {
  return request({
    url: '/background/del',
    method: 'delete',
    params: {
      id
    }
  })
}


/**
 * 获取系统信息
 * @returns {info}
 */
export function getMySystemInfo() {
  return request({
    url: '/system/info',
    method: 'get',
  })
}

/**
 * 修改系统信息
 * @param data data
 * @returns {message} success message
 */
export function updateMySystemInfo(data) {
  return request({
    url: '/admin/system/update',
    method: 'put',
    data
  })
}

/**
 * 获取系统底部标签
 * @param data data
 * @returns {message} success message
 */
export function getWebFooterLabel() {
  return request({
    url: '/system/find/footer/label',
    method: 'get',

  })
}


/**
 * 删除系统底部标签
 * @param id id
 * @returns {message} success message
 */
export function delWebFooterLabel(id) {
  return request({
    url: '/admin/system/delete/footer/label',
    method: 'delete',
    params: {
      id
    }
  })
}



/**
 * 添加或者修改系统底部标签
 * @param data data
 * @returns {message} success message
 */
export function updateWebFooterLabel(data) {
  return request({
    url: '/admin/system/update/footer/label',
    method: 'put',
    data
  })
}


/**
 * 修改github配置
 * @param data data
 * @returns {message} success message
 */
export function updateGithubSetting(data) {
  return request({
    url: '/admin/system/update/github/setting',
    method: 'put',
    data
  })
}


/**
 * 查看github操作
 * @param data data
 * @returns {message} success message
 */
export function getGithubSetting() {
  return request({
    url: '/admin/system/find/github/setting',
    method: 'get',
  })
}


/**
 * 查看github操作
 * @param data data
 * @returns {message} success message
 */
export function getGithubRecords(url) {
  return request({
    url: '/github/records',
    method: 'get',
    params:{
      url
    }
  })
}
/**
 * 查看github操作
 * @param data data
 * @returns {message} success message
 */
export function deleteGithubRecords(data) {
  return request({
    url: '/github/delete/records',
    method: 'post',
    data
  })
}



export function getUploadFileList(data) {
  return request({
    url: '/admin/upload/picture/list',
    method: 'POST',
    data
  })
}


export function delPicture(id) {
  return request({
    url: '/admin/upload/picture/del/'+id,
    method: 'get',
  })
}
