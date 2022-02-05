import request from "../network/request";



/**
 * list
 * @returns 获取更新列表
 */
export function getUpdateQuestion() {
    return request({
        url: '/question/list',
        method: 'get',
    })
}
