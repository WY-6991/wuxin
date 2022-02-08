import request from "../network/request";


/**
 * 归档
 * @param {current} current 
 * @returns 
 */
export function getArchiveList(data) {
    return request({
        url: '/archive/list',
        method: 'post',
        data

    })
}