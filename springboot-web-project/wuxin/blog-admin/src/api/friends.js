import request from '@/utils/request'

/**
 * 添加友情链接
 * @param data
 * @returns {AxiosPromise}
 */
export function createFriend(data) {
  return request({
    url: '/admin/friend/add',
    method: 'post',
    data
  })
}

/**
 * 修改
 * @param data
 * @returns {AxiosPromise}
 */
export function updateFriend(data) {
  return request({
    url: '/admin/friend/update',
    method: 'post',
    data
  })
}

/**
 * 删除
 * @param friendId
 * @returns {AxiosPromise}
 */
export function delFriend(friendId){
  return request({
    url: '/admin/friend/del',
    method: 'get',
    params:{
      friendId
    }
  })
}

/**
 * 显示友情链接
 * @param data
 * @returns {AxiosPromise}
 */
export function getFriendList(data) {
  return request({
    url: '/admin/friend/list',
    method: 'post',
    data
  })
}

/**
 * 删除回复
 * @param id
 * @returns {AxiosPromise}
 */
export function getFriendMessage() {
  return request({
    url: '/admin/friend/find/message',
    method: 'get',
  })
}

/**
 * 删除回复
 * @param id
 * @returns {AxiosPromise}
 */
export function updateFriendMessage(data) {
  return request({
    url: '/admin/friend/update/message',
    method: 'post',
    data
  })
}

