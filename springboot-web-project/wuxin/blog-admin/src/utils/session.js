export function setSetStore(key,value) {
  sessionStorage.setItem(key,JSON.stringify(value))
}

export function getStore(key) {
  return JSON.parse(sessionStorage.getItem(key))
}

export function removeStore(key) {
  sessionStorage.removeItem(key)
}

export function clearStore() {
  sessionStorage.clear()
}
