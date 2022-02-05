import request from "../network/request";


/**
 * 根据tagName获取该标签下的blogList
 * @param {pageVo} data 
 * @returns 
 */
export function getBlogListByTagName(data) {
    return request({
        url: '/tag/blog/list',
        method: 'post',
        data
    })
}



/**
 * 根据cateogryName获取该标签下的blogList
 * @param {pageVo} data 
 * @returns 
 */
export function getBlogByCategoryName(data) {
    return request({
        url: '/category/blog/list',
        method: 'post',
        data
    })
}