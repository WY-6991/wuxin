import Cookies from 'js-cookie'


const TokenKey = 'comment-user-token'
const User = 'user'



/**
 * 获取token
 * @returns token
 */
export function getToken() {
    return Cookies.get(TokenKey)
}

/**
 * 设置token
 * @param {token} token 
 * @returns 
 */
export function setToken(token) {
    return Cookies.set(TokenKey, token)
}



/**
 * 
 * @returns 清除token
 */
export function removeToken() {
    return Cookies.remove(TokenKey)
}

/**
 * 获取密码
 * @param {本地存储} key 
 * @returns 
 */
export function getBlogPassword(key) {
    return localStorage.getItem(key)
}


/**
 * 设置密码
 * @param {密码key} key 
 * @param {密码} value 
 * @returns 
 */
export function setBlogPassword(key, value) {
    return localStorage.setItem(key, value)
}


/**
 * 评论用户
 * @param {设置评论用户 user：username，email} user 
 */
export function setUser(user) {
    localStorage.setItem(TokenKey, JSON.stringify(user))
}

/**
 * 获取评论用户信息
 * @returns user
 */
export function getUser() {
    return JSON.parse(localStorage.getItem(TokenKey))
}