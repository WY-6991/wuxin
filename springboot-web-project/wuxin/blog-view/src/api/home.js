import request from "@/network/request";




/**
 * 获取网站博主信息
 * @returns info
 */
export function getAdminUserInfo() {
    return request({
        url: '/user/blogger/info',
        method: 'get'
    })
}

/**
 * 随机blogList
 * @returnsrandomBlogList
 */
export function getRandomBlog() {
    return request({
        url: '/blog/random',
        method: 'get'
    })
}


export function webSite(){
    return request({
        url: '/web/site/info',
        method: 'get',
    })
}
