import axios from 'axios'
import { Message, MessageBox } from 'element-ui'
import store from '@/store'
import { getToken } from '@/utils/auth'
import errorCode from '@/utils/errorCode'
import router from '@/router'
// 端口号配置
const service = axios.create({
  // url = base url + request url
  baseURL: process.env.VUE_APP_BASE_API,
  timeout: 5000,
  headers: {
    'Content-Type': 'application/json;charset=utf-8'
  }
  // 是否带有凭证
  // withCredentials: true
})

// 跨域请求配置
service.defaults.withCredentials = true

// 请求拦截
service.interceptors.request.use(
  config => {
    if (store.getters.token) {
      // 是否带有token
      config.headers['Authentication'] = getToken()
    }
    return config
  },
  () => {
    return Promise.reject('获取不到用户令牌,请重新登录！')
  }
)

// 401 认证失败，请重新登录！
// 402 自定义错误！
// 403 禁止用户访问
// 404 请求路径找不到
// 500 服务器内部错误
service.interceptors.response.use(
  response => {
    const { code, message } = response.data
    console.log('请求状态码:', code)
    if (code === 401) {
      MessageBox.confirm(errorCode[code], '系统提示', {
        confirmButtonText: '重新登录',
        cancelButtonText: '取消',
        type: 'warning'
      }
      ).then(() => {
        router.push('/login')
      }).catch(() => {
      })
      return Promise.reject('无效的会话，或者会话已过期，请重新登录。')
    } else if (code === 402) {
      Message({
        message: message,
        type: 'error'

      })
      return Promise.reject(new Error(message))
    } else if (code === 403) {
      Message({
        message: '操作失败！没有权限执行该操作',
        type: 'error'

      })
      return Promise.reject(new Error('操作失败！没有权限执行该操作'))
    } else if (code === 500) {
      Message({
        message: message || '服务器开小差去了',
        type: 'error'

      })
      return Promise.reject(new Error(message || '服务器开小差去了'))
    } else if (code !== 200) {
      Message.error({
        type: 'error',
        title: message

      })
      return Promise.reject('error')
    } else {
      return response.data
    }
  },
  error => {
    let { message } = error
    if (message.includes('Network Error')) {
      message = '请求错误！'
    } else if (message.includes('timeout')) {
      message = '请求超时'
    } else if (message.includes('Request failed with status code')) {
      message = '系统接口' + message.substr(message.length - 3) + '异常'
    }
    Message({
      message: message,
      type: 'error',
      duration: 5 * 1000

    })
    return Promise.reject(error)
  }
)

export default service
