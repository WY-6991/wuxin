import {
    GET_SETTING, SAVE_SETTING,
    SET_NAV_COLOR,
    UPDATE_INVERTED,
    UPDATE_NIGTH_MODE
} from "@/store/mutations-type";
import {defaultSetting} from "@/utils/setting";

export const state = {
    // 将配置信息全部保存为一个对象
    // setting:JSON.parse(window.localStorage.getItem(SAVE_SETTING))||defaultSetting
    // 专注模式
    count: 1,
    focusMode: true,
    // 导航栏颜色默认黑色
    inverted: true,
    // 菜单颜色 默认 无
    menuColor: '',
    // 是否显示名字 默认 显示
    showName: true,
    // 夜间模式 默认 不显示
    nightMode: false,
    // 小电视背景图
    tvImage: '',
    // 背景设置
    background: {
        // 背景透明度 默认 无
        opacity: 1,
        // 是否显示背景图 默认 不显示
        isShowImage: false,
        // 背景图 地址 默认地址
        image: "default",
        // 图片模糊背景化
        filter: '10px',
        // 背景色 默认无
        color: 'default',
        // 图片透明度 默认 无
        imageOpacity: 1
    },

    // 默认页面窗口大小
    clientSize: {
        clientWidth: 768
    }

}

const mutations = {

    // 修改夜间模式
    [UPDATE_NIGTH_MODE]: (state) => {
        state.nightMode = !state.nightMode
    },

    //修改导航栏反转
    [UPDATE_INVERTED]: (state) => {
        state.inverted = !state.inverted
    },

    // 保存页面配置
    [SAVE_SETTING]: (state, commit) => {
        window.localStorage.setItem(SAVE_SETTING, JSON.stringify(state))
    },

    // 获取页面配置
    [GET_SETTING]: (state) => {
        // state = state
        state.setting = JSON.parse(window.localStorage.getItem(SAVE_SETTING)) || defaultSetting
    }

}

const actions = {


    /**
     * 保存页面设置
     * @param commit commit
     * @param state state
     * @returns {Promise<unknown>}
     */
    saveSetting({commit, state}) {
        return new Promise(resolve => {
            commit(SAVE_SETTING, state)
            resolve()
        })
    },

    /**
     * 获取页面设置
     * @param commit commit
     * @param state state
     * @returns {Promise<state>}
     */
    getSetting({commit, state}) {
        return new Promise(resolve => {
            commit(GET_SETTING)
            resolve(state)
        })
    },


}

const getters = {

    inverted: (state) => {
        return state.inverted
    },
    state: (state) => {
        return state
    },

}

export default {
    namespaced: true,
    state,
    getters,
    mutations,
    actions
}