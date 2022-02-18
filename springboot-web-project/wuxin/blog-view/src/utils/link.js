/**
 * 给所有a标签添加target='_blank'属性
 * @param ment doument
 */


export function formatLink(str) {
    var regex = /<a(.*?)target='[^']*'(.*?)>/ig;
    str = str.replace(regex, "<a$1target='_blank '$2>");
    var regex1 = /<a(((?!target).)*?)>/ig;
    str = str.replace(regex1, "<a target='_blank '$1>");

    return str

}