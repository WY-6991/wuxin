import request from "../network/request";

/**
 * 
 * @returns 获取关于我的内容
 */
export function getAbout() {
    return request({
        url: '/about/detail',
        method: 'get'
    })
}





