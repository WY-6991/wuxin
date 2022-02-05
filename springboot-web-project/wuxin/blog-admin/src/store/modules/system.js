import {getMySystemInfo} from "@/api/system";

import {SET_MY_SYSTEM} from '@/store/mutations-type'

const state = {
  systemInfo: {},
}


const mutations = {

  [SET_MY_SYSTEM]: (state, systemInfo) => {
    state.systemInfo = systemInfo
  },

}

const actions = {
  getMySystemInfo({commit}) {
    return new Promise((resolve, reject) => {
      getMySystemInfo().then(res => {
        commit(SET_MY_SYSTEM, res.result)
        resolve(res)
      }).catch(error => {
        reject(error)
      })
    })
  },




}


export default {
  namespaced: true,
  state,
  mutations,
  actions
}
