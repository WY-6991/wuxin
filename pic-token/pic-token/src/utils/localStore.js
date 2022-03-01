const wx = window.localStorage

export function setLocalStore(key,value){
    console.log('set localstore',key,JSON.stringify(value))
    wx.setItem(key,JSON.stringify(value))
}

export function getLocalStore(key){
    console.log('get key',key)
    return JSON.parse(wx.getItem(key))
}

export function removeLocal(key){
    return wx.removeItem(key)
}

