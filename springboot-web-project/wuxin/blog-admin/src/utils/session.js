export function setSetStore(key, value) {
  sessionStorage.setItem(key, JSON.stringify(value))
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


export function setLocalStore(key, value) {
  window.localStorage.setItem(key, JSON.stringify(value))
}

export function getLocalStore(key) {
  return JSON.parse(window.localStorage.getItem(key))
}

export function cleanLocalStore(key) {
  window.localStorage.clear(key)
}
