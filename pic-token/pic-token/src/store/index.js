import Vue from 'vue'
import Vuex from 'vuex'
import {Message} from "element-ui";

import {
    getUserInfo
} from "../api/github";

import {
    getGiteeUserInfo
} from "../api/gitee";

import {
    SET_USER,
    TOKEN_TYPE
} from './mutations-type'

import {
    getLocalStore, removeLocal,
    setLocalStore
} from "../utils/localStore";

Vue.use(Vuex)

const store = new Vuex.Store({
    state: {
        user: {
            username: '',
            token: '',
            url: '',
            avatar: '',
        },
        tokenType: 'github'
    },
    mutations: {
        [SET_USER]: (state, data) => {
            const {user} = data
            state.user = user
        },
        [TOKEN_TYPE]: (state, type) => {
            state.tokenType = type
        },
    },

    getters: {
        tokenType() {
            return getLocalStore(TOKEN_TYPE) ? getLocalStore(TOKEN_TYPE) : 'github'
        }
    },

    actions: {

        getInfo({commit}, data) {
            return new Promise((resolve, reject) => {
                let user = {}
                if (data.tokenType === 'github') {
                    getUserInfo(data.token).then(res => {
                        const {login, avatar_url, html_url} = res
                        user = {username: login, avatar: avatar_url, token: data.token, url: html_url}
                        resolve(user)
                    }).catch(error => {
                        reject(error)
                    })
                } else {
                    getGiteeUserInfo(data.token).then(res => {
                        const {login, avatar_url, html_url} = res
                        user = {username: login, avatar: avatar_url, token: data.token, url: html_url}
                        resolve(user)
                    }).catch(error => {
                        reject(error)
                    })
                }
                commit(SET_USER, {'user': user, 'type': data.tokenType})
                commit(TOKEN_TYPE, data.tokenType)
            })
        },

        getUser({commit}, type) {
            return new Promise((resolve) => {
                const user = getLocalStore(SET_USER + type)
                const u = user && user.token && user.username && user.avatar ? user : {}
                commit(SET_USER, {'user': user, 'type': type})
                commit(TOKEN_TYPE, type)
                resolve(u)
            })
        },

        saveUser({commit}, data) {
            return new Promise((resolve) => {
                const {user, tokenType} = data
                setLocalStore(SET_USER + tokenType, user)
                setLocalStore(TOKEN_TYPE, tokenType)
                commit(SET_USER, {'user': user, 'type': tokenType})
                commit(TOKEN_TYPE, tokenType)
                Message.success('用户配置保存成功！')
                resolve()
            }).catch(() => {
                Message.error('保存失败获取不到用户信息！')
            })
        },

        removeUser({commit}, type) {
            return new Promise((resolve) => {
                commit(SET_USER, {'user': {}, 'type': type})
                commit(TOKEN_TYPE, type)
                removeLocal(SET_USER + type)
                removeLocal(type)
                Message.success('清空成功！')
                resolve()
            }).catch(() => {
                Message.error('清空失败获取不到用户信息！')
            })
        },
    },

})


export default store