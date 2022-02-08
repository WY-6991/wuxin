import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import "./plugins/index"; // 插件 注册 全局组件注册
import "./assets/css/index.css"; // 样式
import 'animate.css'; // 动画样式

/**
 * 关闭生产消息提示
 */
Vue.config.productionTip = false;

/**
 * 捕获错误信息
 */
Vue.config.errorHandler = function (err, vm, info) {
    console.log(err)
    console.log(vm)
    console.log(info)
}


/**
 * 捕获警告信息
 */
Vue.config.warnHandler = function (msg, vm, trace) {
    console.log(msg)
    console.log(vm)
    console.log(trace)
}



Vue.mixin({
    computed: {
        bgColor() {
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
    },
})



console.log("\n\n %c  gitee %c "
    .concat("https://gitee.com/wuxin0011", ""), 'background: rgb(199, 29, 35); padding: 1px; border-radius: 3px 0 0 3px; color: #fff', 'border-radius: 0 3px 3px 0; color:#fff');
console.log(
    "\n\n %c  github %c "
        .concat("https://github.com/WY-6991/wuxin", ""), 'background: rgb(36, 41, 47); padding: 1px; border-radius: 3px 0 0 3px; color: #fff', 'border-radius: 0 3px 3px 0; color: #fff');


new Vue({
    router,
    store,
    render: (h) => h(App),
}).$mount("#app");
