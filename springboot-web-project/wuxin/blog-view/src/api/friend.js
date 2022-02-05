import request from "../network/request";

/**
 * friendList
 * @returns friendList
 */
export function getFriendList() {
    return request({
        url: `/friend/list`,
        method: 'get',
    })
}


