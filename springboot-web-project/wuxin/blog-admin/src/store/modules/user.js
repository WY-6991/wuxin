import {getEmailCode, getInfo, login, loginToEmail, logout,} from '@/api/user'
import {getRoles, getToken, removeToken, setRoles, setToken,} from '@/utils/auth'
import router, {resetRouter} from '@/router'

import {
  SET_AVATAR,
  SET_INTRODUCTION,
  SET_NAME,
  SET_NICKNAME,
  SET_ROLES,
  SET_TOKEN,
  SET_USER_ID,
} from '../mutations-type'


const state = {
  token: getToken() || null,
  name: '',
  nickname: '',
  avatar: '',
  introduction: '',
  userId: '',
  roles: getRoles() || [],
}


const mutations = {
  [SET_TOKEN]: (state, token) => {
    state.token = token
  },

  [SET_USER_ID]: (state, userId) => {
    state.userId = userId
  },
  [SET_INTRODUCTION]: (state, introduction) => {
    state.introduction = introduction
  },
  [SET_NAME]: (state, name) => {
    state.name = name
  },

  [SET_NICKNAME]: (state, nickname) => {
    state.nickname = nickname
  },
  [SET_AVATAR]: (state, avatar) => {
    state.avatar = avatar
  },
  [SET_ROLES]: (state, roles) => {
    state.roles = roles
  },


}

const actions = {


  /**
   * 普通方式登录 通过用户名和密码方式登录
   * @param commit
   * @param userInfo
   * @returns {Promise<unknown>}
   */
  login({commit}, userInfo) {
    console.log('你输入的信息:', JSON.stringify(userInfo))
    const {username, password} = userInfo
    return new Promise((resolve, reject) => {
      login({username: username.trim(), password: password.trim()}).then(res => {
        const {result} = res
        console.log(result)
        commit(SET_TOKEN, result)
        setToken(result)
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  /**
   * 获取登录之后的用户信息角色
   * @param commit
   * @param state
   * @returns {Promise<unknown>}
   */
  getInfo({commit, state}) {
    return new Promise((resolve, reject) => {
      getInfo().then(res => {
        const {roles, userId, name, introduction, nickname} = res.result
        const avatar = res.result.avatar !== "" ? res.result.avatar : require("@/assets/avatar/default.png");
        if (!roles || roles.length <= 0) {
          reject('获取不到用户角色信息!')
        }
        commit(SET_ROLES, roles)
        commit(SET_USER_ID, userId)
        commit(SET_NICKNAME, decodeURIComponent(nickname))
        commit(SET_NAME, decodeURIComponent(name))
        commit(SET_AVATAR, avatar)
        commit(SET_INTRODUCTION, introduction)
        // window.sessionStorage.setItem('UserInfo', JSON.stringify({
        //   'roles': roles,
        //   'name': name,
        //   'userId': userId,
        //   'avatar': avatar,
        //   'introduction': introduction,
        //   'user': user,
        //   'nickname': nickname
        // }))
        setRoles(roles)
        resolve(res)

      }).catch(error => {
        reject(error)
      })
    })
  },



  /**
   * 通过邮箱获取验证码
   */
  getEmailCode({commit}, email) {
    console.log("通过邮箱方式登录 获取验证码",email)
    return new Promise((resolve, reject) => {
      getEmailCode(email).then(res => {
        resolve(res)
      }).catch(error => {
        reject(error)
      })
    })
  },



  /**
   * 通过邮箱和验证码方式登录
   */
  loginToEmail({commit}, userInfo) {
    console.log("通过邮箱方式登录！...",userInfo)
    return new Promise((resolve, reject) => {
      loginToEmail(userInfo).then(res => {
        const {result} = res
        commit(SET_TOKEN, result)
        // 将用户token信息保存到cookie中
        setToken(result)
        resolve(res)
      }).catch(error => {
        reject(error)
      })
    })
  },

  logout({commit, state, dispatch}) {
    console.log("执行退出登录操作....")
    return new Promise((resolve, reject) => {
      logout(state.token).then(() => {
        commit(SET_TOKEN, '')
        commit(SET_ROLES, [])
        removeToken()
        resetRouter()
        dispatch('tagsView/delAllViews', null, {root: true})
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  /**
   * 清空登录用户的token
   * @param commit
   * @returns {Promise<unknown>}
   */
  resetToken({commit}) {
    return new Promise(resolve => {
      commit(SET_TOKEN, '')
      commit(SET_ROLES, [])
      removeToken()
      resolve()
    })
  },
  async changeRoles({commit, dispatch}, role) {
    const token = role + '-token'
    commit(SET_TOKEN, token)
    setToken(token)
    const {roles} = await dispatch('getInfo')
    resetRouter()
    const accessRoutes = await dispatch('permission/generateRoutes', roles, {root: true})
    router.addRoutes(accessRoutes)
    dispatch('tagsView/delAllViews', null, {root: true})
  },


}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
