import Vditor from 'vditor'
// import request from '@/utils/request'
import { Message } from 'element-ui'
import store from '@/store'

const { token } = store.state.user.token

/**
 * 编辑器容器
 * @param element 标签className或者id
 * @param uploadFileUrl 文件上传地址
 * @param height 编辑器大小
 * @param token 图片上传需要携带token
 * @param cache 是否开启缓存
 * @returns {Vditor|*} 编辑器
 */
export function createVditor(element, height, cache) {
  let ready = false
  const vditorOptions = {
    height: height,
    width: '100%',
    toolbarConfig: {
      pin: true
    },
    mode: 'sv',
    // mode: "wysiwyg",
    // theme: "dark",
    theme: 'classic',
    cache: {
      enable: cache
    },
    upload: {
      accept: 'image/*,.mp3, .wav, .rar', // 上传文件类型
      url: process.env.VUE_APP_BASE_API + '/github/upload/blog/img',
      fieldName: 'file', // 后端接受参数
      linkToImgUrl: process.env.VUE_APP_BASE_API + '/github/upload/blog/img',
      filename(name) {
        return name.replace(/[^(a-zA-Z0-9\u4e00-\u9fa5\.)]/g, '').replace(/[\?\\/:|<>\*\[\]\(\)\$%\{\}@~]/g, '').replace('/\\s/g', '')
      },
      headers: {
        // 'Content-Type': 'multipart/form-data',
        'Authentication': token
      },
      // 是否支持多文件上传
      multiple: false,
      // 默认文10m 设置为10*10000m
      max: 100000 * 1024 * 1024,
      success(element, response) {
        let succFileText = ''
        const res = JSON.parse(response)
        let name
        try {
          name = res.data.name
        } catch (e) {
          name = 'wxuin-image-error'
        }
        const path = res.data.url
        const lastIndex = name.lastIndexOf('.')
        let type = name.substr(lastIndex)

        type = type.toLowerCase()
        if (type.indexOf('.wav') === 0 || type.indexOf('.mp3') === 0 || type.indexOf('.ogg') === 0) {
          if (vditorName.getCurrentMode() === 'wysiwyg') {
            succFileText += `<p><audio controls="controls" src="${path}"></audio></p>`
          } else if (vditorName.getCurrentMode() === 'ir') {
            succFileText += `<p><audio controls="controls" src="${path}"></audio></p>`
          } else {
            succFileText += `[${name}](${path})\n`
          }
        } else if (type.indexOf('.apng') === 0 ||
          type.indexOf('.bmp') === 0 ||
          type.indexOf('.gif') === 0 ||
          type.indexOf('.ico') === 0 || type.indexOf('.cur') === 0 ||
          type.indexOf('.jpg') === 0 || type.indexOf('.jpeg') === 0 || type.indexOf('.jfif') === 0 || type.indexOf('.pjp') === 0 || type.indexOf('.pjpeg') === 0 ||
          type.indexOf('.png') === 0 ||
          type.indexOf('.svg') === 0 ||
          type.indexOf('.webp') === 0) {
          if (vditorName.getCurrentMode() === 'wysiwyg') {
            succFileText += `<img alt="${name}" src="${path}" target="_blank">`
          } else {
            succFileText += `![${name}](${path})\n`
          }
        } else {
          if (vditorName.getCurrentMode() === 'wysiwyg') {
            succFileText += `<a href="${path}" target="_blank">${name}</a>`
          } else {
            succFileText += `![${name}](${path})\n`
          }
        }
        vditorName.insertValue(`${succFileText}`)
        Message.success('上传成功！')
      },
      error() {
        Message.error('上传失败！')
      }

    },
    typewriterMode: true,
    value: '',
    after: () => {
      if (!ready) {
        ready = true
      }
    }
  }

  const vditorName = new Vditor(element, vditorOptions)

  //  返回编辑器视图
  return vditorName
}

