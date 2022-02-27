

/**
 * https://developer.mozilla.org/zh-CN/docs/Web/HTML/Element/img
 * @param {string} fileName
 * @returns {Boolean}
 */
export function isImgExt(fileName) {
  return /\.(apng|avif|bmp|gif|ico|cur|jpg|jpeg|jfif|pjpeg|pjp|png|svg|tif|tiff|webp)$/i.test(fileName)
}
/**
 * @param {string} path
 * @returns {Boolean}
 */
export function isExternal(path) {
  return /^(https?:|mailto:|tel:)/.test(path)
}

/**
 * @param {string} path
 * @returns {Boolean}
 */
export function validNumber(n) {
  const reg = /^\d+$/
  return reg.test(n)
}


/**
 * @param {string} str
 * @returns {Boolean}
 */
export function validUsername(str) {
  const valid_map = ['admin', 'editor']
  return valid_map.indexOf(str.trim()) >= 0
}

/**
 * @param {string} url
 * @returns {Boolean}
 */
export function validURL(url) {
  const reg = /^(https?|ftp):\/\/([a-zA-Z0-9.-]+(:[a-zA-Z0-9.&%$-]+)*@)*((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]?)(\.(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])){3}|([a-zA-Z0-9-]+\.)*[a-zA-Z0-9-]+\.(com|edu|gov|int|mil|net|org|biz|arpa|info|name|pro|aero|coop|museum|[a-zA-Z]{2}))(:[0-9]+)*(\/($|[a-zA-Z0-9.,?'\\+&%$#=~_-]+))*$/
  return reg.test(url)
}

/**
 * @param {string} str
 * @returns {Boolean}
 */
export function validLowerCase(str) {
  const reg = /^[a-z]+$/
  return reg.test(str)
}

/**
 * @param {string} str
 * @returns {Boolean}
 */
export function validUpperCase(str) {
  const reg = /^[A-Z]+$/
  return reg.test(str)
}

/**
 * @param {string} str
 * @returns {Boolean}
 */
export function validAlphabets(str) {
  const reg = /^[A-Za-z]+$/
  return reg.test(str)
}

/**
 * @param {string} email
 * @returns {Boolean}
 */
export function validEmail(email) {
  const reg = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
  return reg.test(email)
}

/**
 * @param {string} phone
 * @returns {Boolean}
 */
export function validPhone(phone) {
  const reg = /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/
  return reg.test(phone)
}

/**
 * @param {string} str
 * @returns {Boolean}
 */
export function isString(str) {
  if (typeof str === 'string' || str instanceof String) {
    return true
  }
  return false
}

/**
 * @param {Array} arg
 * @returns {Boolean}
 */
export function isArray(arg) {
  if (typeof Array.isArray === 'undefined') {
    return Object.prototype.toString.call(arg) === '[object Array]'
  }
  return Array.isArray(arg)
}


/**
 * 用户名基本验证
 * @param rule
 * @param value
 * @param callback
 */
export function validateUsername(){
  return {required:true,min:2,max:15,message: '用户名由2-15个字符组成', trigger: 'blur'}
}

/**
 * 用户名基本验证
 * @param rule
 * @param value
 * @param callback
 */
export const validateUrl = (rule, value, callback) => {
  if (!validURL(value)) {
    callback('链接格式不正确！');
  }
  callback()

}

/**
 * 手机号
 * @param rule
 * @param value
 * @param callback
 */
let validatePhone= (rule, value, callback) => {
  if (value === '') {
    callback()
  }
  if (!validPhone(value)) {
    callback(new Error('手机号格式不正确！'));
  }
  callback()

}

/**
 * 邮箱
 * @param rule
 * @param value
 * @param callback
 */
export const validateEmail= (rule, value, callback) => {
  if (!validEmail(value)) {
    callback(new Error('邮箱格式不正确！'));
  }
  callback()

}


/**
 * 密码基本验证
 * @param rule
 * @param value
 * @param callback
 */
export const  validatePassword= (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请输入密码！'));
  }
  if (value.length < 4 || value.length > 15) {
    callback(new Error('密码4~15位字符哦！'));
  }
  callback()

}


