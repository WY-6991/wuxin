import request from "@/network/request";


/**
 * 获取动态列表
 * @param {页码} current 
 * @returns
 */
export function getMomentList(current, number) {
    return request({
        url: `/moment/list/${current}/5`,
        method: 'get',

    })
}
