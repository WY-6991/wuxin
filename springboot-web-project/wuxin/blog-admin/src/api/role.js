import request from '@/utils/request'


export function getRoleList(data) {
  return request({
    url: '/admin/role/list',
    method: 'post',
    data
  })
}


export function getUserRoleList(data) {
  return request({
    url: '/admin/user/role/list',
    method: 'post',
    data
  })
}


export function updateUserRole(data) {
  return request({
    url: '/admin/user/update/role',
    method: 'put',
    data
  })
}
