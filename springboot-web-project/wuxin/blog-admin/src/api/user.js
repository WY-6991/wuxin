import request from '@/utils/request'


/**
 * 登录
 */
export function login(data) {
  console.log('登录中...',JSON.stringify(data))
  return request({
    url: '/login',
    method: 'post',
    data
  })
}



/**
 * 获取登录用户信息
 */
export function getInfo() {
  return request({
    url: '/admin/user/find/login/user/info',
    method: 'get',
  })
}


/**
 * 注册用户获取验证码
 */
export function getRegisterCode(data) {
  return request({
    url: '/register/user/code',
    method: 'post',
    data
  })
}


/**
 * 用户注册
 */
export function registerUser(data) {
  return request({
    url: '/register/user',
    method: 'post',
    data
  })
}








/**
 * 修改关于我的内容
 * @param data
 * @returns {AxiosPromise}
 */
export function findUserInfoDetail() {
  return request({
    url: '/admin/user/detail',
    method: 'get',

  })
}


/**
 * 通过邮箱获取验证码
 */
export function getEmailCode(email) {
  return request({
    url: '/login/get/email/code',
    method: 'get',
    params: {
      email
    }
  })
}



/**
 * 通过邮箱和验证码登录
 */
export function loginToEmail(data) {
  return request({
    url: '/login/to/email/code',
    method: 'post',
    data
  })
}



/**
 * 分页查看用户信息
 * @param current
 * @param limit
 * @param keywords
 * @returns {list}
 */
export function getUserList(data) {
  return request({
    url: '/admin/user/list',
    method: 'post',
    data
  })
}


/**
 * 统计用户数量
 * @returns {int}
 */
export function getUserCount() {
  return request({
    url: '/user/count',
    method: 'get',
  })
}

/**
 * 删除用户信息
 * @param data
 * @returns {message}
 */
export function updateUser(data) {
  return request({
    url: '/admin/user/update',
    method: 'post',
    data
  })
}


/**
 * 删除用户id
 * @param userId
 * @returns {message}
 */
export function delUser(userId) {
  return request({
    url: '/admin/user/del',
    method: 'get',
    params: {
      userId
    }
  })
}


/**
 * 验证密码
 * @param password
 * @returns {message}
 */
export function repass(password) {
  return request({
    url: '/admin/user/repass',
    method: 'get',
    params: {
      password
    }
  })
}

/**
 * 修改密码
 */
export function updatePass(data) {
  return request({
    url: '/admin/user/update/pass',
    method: 'post',
    data
  })
}


// 退出
export function logout() {
  return request({
    url: '/logout',
    method: 'get'
  })
}



/**
 * 上传图片
 * @param data
 * @returns {result}
 */
export function uploadAvatar(data) {
  return request({
    url: '/github/upload/user/avatar',
    method: 'POST',
    headers: {
      'Content-Type': 'multipart/form-data'
    },
    data
  })
}

/*====================================================邮箱验证===================================*/

/**
 * 获取评论用户
 * @param data
 * @returns {message}
 */
export function getCommentUser(data) {
  return request({
    url: '/admin/comment/user/list',
    method: 'POST',
    data
  })
}


/**
 * 检验邮箱是否输入正确
 * @param email
 * @returns {message}
 */
export function validUserEmail(email) {
  return request({
    url: '/admin/user/update/pass/valid/email',
    method: 'get',
    params: {
      email
    }
  })
}

/**
 * 检验验证码是否输入正确
 * @param email
 * @returns {message}
 */
export function validUserCode(email, code) {
  return request({
    url: '/admin/user/update/pass/valid/code',
    method: 'get',
    params: {
      email, code
    }
  })
}


/**
 * 检验验证码是否输入正确
 * @param email
 * @returns {message}
 */
export function updatePassByEmail(data) {
  return request({
    url: '/admin/user/update/pass/by/email',
    method: 'post',
    data
  })
}



/*====================================================兴趣爱好===================================*/



/**
 * 添加兴趣爱好
 * @param data
 * @returns {message}
 */
export function createHobby(data) {
  return request({
    url: '/admin/hobby/add',
    method: 'post',
    data
  })
}

/**
 * 修改兴趣爱好
 * @param data
 * @returns {message}
 */
export function updateHobby(data) {
  return request({
    url: '/admin/hobby/update',
    method: 'post',
    data
  })
}




/**
 * 删除兴趣爱好
 * @param id
 * @returns {message}
 */
export function deleteHobby(id) {
  return request({
    url: '/admin/hobby/del',
    method: 'get',
    params:{
      id
    }
  })
}


/**
 * 查找兴趣爱好 list
 * @param userId
 * @returns {list}
 */
export function findHobby(userId) {
  return request({
    url: '/admin/hobby/list',
    method: 'get',
    params:{
      userId
    }
  })
}
/*======================================================================================*/
