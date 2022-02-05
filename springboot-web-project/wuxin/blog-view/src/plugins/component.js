import Vue from "vue";
import MyPagination from "@/components/common/Pagination";
import store from "@/store";

/**
 * 注册全局分页组件
 */
Vue.component(MyPagination.name, MyPagination)


// 全局挂在背景色色设置函数
Vue.prototype.$nightMode = (color, mode) => {
    if (color !== 'default' && !mode) {
        return color
    } else {
        return mode ? 'rgb(18, 18, 18)' : '#fff'
    }
}


// 全局导出背景色配置函数
Vue.prototype.$setting = store.state.setting


// 全局导出背景色
Vue.prototype.$MyBg = () => {
    // if (
    //     store.state.setting.background.color !== 'default'
    //     && !store.state.setting.nightMode
    //     && !store.state.setting.background.isShowImage
    // ) {
    //     return {
    //         'backgroundColor': color, 'color': 'black'
    //     }
    // } else {
    //     return (store.state.setting.nightMode  && !store.state.setting.background.isShowImage) ? {
    //         'backgroundColor': 'rgb(18, 18, 18)',
    //         'color': 'white'
    //     } : {'backgroundColor': 'rgb(255, 255, 255)', 'color': 'black'}
    // }

    if (store.state.setting.nightMode) {
        return {
            'backgroundColor': 'rgb(40, 40, 35)',
            'color': 'rgb(212, 212, 212)',
            'border':0
        }
    } else {
        return {
            'backgroundColor': 'rgb(255, 255, 255)',
            'color': '#303133'
        }
    }

}
