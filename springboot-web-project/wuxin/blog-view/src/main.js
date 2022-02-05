import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";

// 插件注册
import "./plugins/index";

// 自定义样式
import "./assets/css/index.css";
// 文章样式
import "./assets/css/typo.css";

// 自定义动画样式文件
// 动画样式
import 'animate.css';
import "./assets/css/animation.css";


/**
 * 关闭生产消息提示
 */
Vue.config.productionTip = false;

/**
 * 捕获错误信息
 * @param {} err
 * @param {*} vm
 * @param {*} info
 */
Vue.config.errorHandler = function (err, vm, info) {
    console.log(err)
    console.log(vm)
    console.log(info)
}


/**
 * 捕获警告信息
 * @param {*} msg
 * @param {*} vm
 * @param {*} trace
 */
Vue.config.warnHandler = function (msg, vm, trace) {
    console.log(msg)
    console.log(vm)
    console.log(trace)
}
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
