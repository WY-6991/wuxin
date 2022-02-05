import request from "../network/request";


/**
 * 归档
 * @param {current} current 
 * @returns 
 */
export function getArchiveList() {
    return request({
        url: '/archive/list',
        method: 'get'
    })
}