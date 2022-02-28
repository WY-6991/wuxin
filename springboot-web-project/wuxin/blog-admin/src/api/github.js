import axios from 'axios'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
import { Message } from 'element-ui'

const request = axios.create({
  baseURL: 'https://api.github.com',
  timeout: 30000
})

// request interceptor
request.interceptors.request.use(config => {
  NProgress.start()
  if (/get/i.test(config.method)) {
    // get请求添加时间戳防止响应缓存
    config.params = config.params || {}
    config.params.t = new Date().getTime()
  }
  const user = JSON.stringify(window.localStorage.getItem('user_repo_token'))
  const token = user && user.token ? user.token : ''
  if (token) {
    config.headers.Authorization = `token ${token}`
  }
  return config
},
error => {
  console.info(error)
  return Promise.reject(error)
}
)

request.interceptors.response.use(
  response => {
    NProgress.done()
    console.log(response)
    const { status } = response
    if (status === 400) {
      Message.error('数据获取失败！')
      return Promise.reject()
    }
    if (status === 401) {
      Message.error('认证失败！')
      return Promise.reject()
    }
    if (status === 403) {
      Message.error('禁止访问！')
      return Promise.reject()
    }
    if (status === 404) {
      Message.error('请求路径未找到！')
      return Promise.reject()
    }
    if (status !== 200) {
      Message.error('服务器响应失败！')
      return Promise.reject()
    }
    return response.data
  },
  error => {
    Message.error(error.message)
    return Promise.reject(error.message)
  }
)

// 获取用户信息
export function getUserInfo(token) {
  console.log(token)
  return request({
    url: `/user`,
    method: 'GET',
    headers: {
      Authorization: `token ${token}`
    }
  })
}

// 获取用户仓库列表
export function getUserRepos(name) {
  return request({
    url: `/users/${name}/repos`,
    method: 'GET'
  })
}

// 获取用户仓库下指定目录的文件列表
export function getReposContents(name, repos, path) {
  return request({
    url: `/repos/${name}/${repos}/contents/${path}`,
    method: 'GET'
  })
}

// 删除文件
export function delFile(name, repos, filePath, data) {
  return request({
    url: `/repos/${name}/${repos}/contents/${filePath}`,
    method: 'DELETE',
    data
  })
}

// 上传文件至仓库指定目录下
export function upload(name, repos, path, fileName, data) {
  return request({
    url: `/repos/${name}/${repos}/contents${path}/${fileName}`,
    method: 'PUT',
    data
  })
}
