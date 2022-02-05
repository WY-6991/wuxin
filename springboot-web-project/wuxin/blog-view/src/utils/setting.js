
// 颜色配置默认选项列表
export const colors = [
    'default',
    'red',
    'orange',
    'yellow',
    'olive',
    'green',
    'teal',
    'blue',
    'violet',
    'pink',
    'brown',
    'grey'
]





// tv小电视列表
export const tvList = [
    require('@/assets/img/svg/tv/1.svg'),
    require('@/assets/img/svg/tv/2.svg'),
    require('@/assets/img/svg/tv/3.svg'),
    require('@/assets/img/svg/tv/4.svg'),
    require('@/assets/img/svg/tv/5.svg'),
    require('@/assets/img/svg/tv/6.svg'),
    require('@/assets/img/svg/tv/7.svg'),
    require('@/assets/img/svg/tv/8.svg'),
    require('@/assets/img/svg/tv/9.svg'),
    require('@/assets/img/svg/tv/10.svg'),
    require('@/assets/img/svg/tv/11.svg'),


]


// 图片列表
export const backgroundImageList = [
    'default',
    'https://cdn.jsdelivr.net/gh/WY-6991/wuxin/img/202112/20211217101725.png',
    require('@/assets/img/bg/bg1.jpg'),
    require('@/assets/img/bg/bg2.jpg'),
    require('@/assets/img/bg/bg3.jpg'),
]


// 页面默认配置
export const defaultSetting = {
    // 专注模式
    focusMode: true,
    // 导航栏颜色默认黑色
    inverted: true,
    // 菜单颜色 默认 无
    menuColor: '',
    // 是否显示名字 默认 显示
    showName: true,
    // 夜间模式 默认 不显示
    nightMode: false,
    tvImage: '',
    // 背景设置
    background: {
        // 背景透明度 默认 无
        opacity: 1,
        // 是否显示背景图 默认 不显示
        isShowImage: false,
        // 背景图 地址 默认地址
        image: "default",
        // 图片高斯化 默认10px
        filter: '10px',
        // 背景色
        color: null,
        // 图片透明度 默认 无
        imageOpacity: 1

    }

}


/**
 * 设置背景图
 * @param show
 * @param url
 * @returns {string|null}
 */
export function settingBackgroundImageUrl(show, url) {
    if ((show) && (url !== 'default')) {
        return 'url(' + `${url}` + ')'||"url('https://cdn.jsdelivr.net/gh/WY-6991/wuxin/img/202112/20211217101725.png')"
    } else {
        return null
    }

}

/**
 * 修改背景色配置
 * @param color
 * @param mode
 * @returns {string|*}
 */
export function settingBackgroundColor(color,mode) {
    console.log('经过默认认证了',color,mode)
    if (color && !mode) {
        return color
    } else {
        return mode ? 'rgb(18, 18, 18)' : '#fff'
    }
}

/**
 * 设置url
 * @param url
 * @returns {string}
 */
export  function settUrl(url){
    return 'url(' + `${url}` + ')'
}
