/**
 * Created by PanJiaChen on 16/11/18.
 */

/**
 * @param {string} path
 * @returns {Boolean}
 */
export function isExternal(path) {
    return /^(https?:|mailto:|tel:)/.test(path)
}


/**
 * @param {string} url
 * @returns {Boolean}
 */
export function validURL(url) {
    const reg = /^(https?|ftp):\/\/([a-zA-Z0-9.-]+(:[a-zA-Z0-9.&%$-]+)*@)*((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]?)(\.(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])){3}|([a-zA-Z0-9-]+\.)*[a-zA-Z0-9-]+\.(com|edu|gov|int|mil|net|org|biz|arpa|info|name|pro|aero|coop|museum|[a-zA-Z]{2}))(:[0-9]+)*(\/($|[a-zA-Z0-9.,?'\\+&%$#=~_-]+))*$/
    return reg.test(url)
}


/**
 * @param {string} email
 * @returns {Boolean}
 */
export function validEmail(email) {
    const reg = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
    return reg.test(email)
}


/**
 * @param {string} str
 * @returns {Boolean}
 */
export function isString(str) {
    if (typeof str === 'string' || str instanceof String) {
        return true
    }
    return false
}

/**
 * @param {Array} arg
 * @returns {Boolean}
 */
export function isArray(arg) {
    if (typeof Array.isArray === 'undefined') {
        return Object.prototype.toString.call(arg) === '[object Array]'
    }
    return Array.isArray(arg)
}


/**
 * 计算页码
 * @param {total} total
 * @param {*} size
 * @returns 10/5 9/5
 */
export function setTotalPage(total, size) {

    // 计算页码 如果size === total 说明只有一页
    if (total <= size) {
        return 1;
    }
    // return parseInt(total / size) + 1 // 丢弃小数部分,保留整数部分
    return Math.ceil(total / size) // 向上取整,有小数就整数部分加1
}


/**
 * 计算评论数量
 * @param {评论列表} commentList
 * @returns count
 */
export function setCommentCount(commentList) {
    let replyCount = 0
    let commentCount = 0
    if (commentList && commentList.length !== 0) {
        commentCount = commentList.length
        for (let index = 0; index < commentList.length; index++) {
            if (commentList[index].replyList && commentList[index].replyList.length !== 0) {
                replyCount = replyCount + commentList[index].replyList.length
            }

        }
        return replyCount + commentCount
    } else {
        return 0
    }


}

/**
 * 日期函数过滤
 * @param {时间} dateTimeStamp
 * @returns
 */
export function getDateDiff(dateTimeStamp) {
    var lr = new Date(dateTimeStamp);
    var now = new Date;
    var dt = now - lr;
    var s = dt / 1000;
    var m = s / 60;
    var h = m / 60;
    // if (s < 60) {
    //     return '刚刚';
    // } else if (s < 3600) {
    //     return parseInt(s / 60) + '分钟前';
    // } else if (s < 86400) {
    //     return parseInt(s / 60 / 60) + '小时前';
    // } else if (s < 604800) { //在一周内
    //     return parseInt(s / 60 / 60 / 24) + '天前';
    // } else if (s < 2592000) {
    //     // return parseInt(s / 60 / 60 / 24 / 7) + "周前"
    //     return dateTimeStamp
    // } else if (s < 2592000 && s > 604800) { //超过一周
    //     // 超过一周写入日期
    //     return dateTimeStamp;
    // }

    return dateTimeStamp;
}

// 日期过滤

// function getDate(date){
//
//     获取当前日志
//
// }
//


/**
 * 评论时间过滤
 * @param {时间} dateTimeStamp
 * @returns
 */
// export function getDateDiff(dateTimeStamp) {
//     var lr = new Date(dateTimeStamp);
//     var now = new Date;
//     var dt = now - lr;
//     var s = dt / 1000;
//     var m = s / 60;
//     var h = m / 60;
//     if (s < 60) {
//         return '刚刚';
//     } else if (s < 3600) {
//         return parseInt(s / 60) + '分钟前';
//     } else if (s < 86400) {
//         return parseInt(s / 60 / 60) + '小时前';
//     } else if (s < 604800) { //在一周内
//         return parseInt(s / 60 / 60 / 24) + '天前';
//     } else if (s < 2592000) {
//         return parseInt(s / 60 / 60 / 24 / 7) + "周前"
//     } else if (s < 2592000 && s > 604800) { //超过一周
//         return parseInt(s / 60 / 60 / 24) + '天前';
//     } else if (s < 31104000) {
//         return parseInt(s / 60 / 60 / 24 / 30) + '月前';
//     } else if (s < 311040000) {
//         return parseInt(s / 60 / 60 / 24 / 30 / 12) + '年前';
//     }
//
// }


export function validKeywords(keywords) {
    if (
        keywords.indexOf('cnm') !== -1
        || keywords.indexOf('c n m') !== -1
        || keywords.indexOf('艹') !== -1

    ) {

        return true
    }
    return false
}
