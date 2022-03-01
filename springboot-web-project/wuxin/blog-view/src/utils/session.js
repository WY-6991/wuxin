
const ws = window.localStorage

export function setSetStore(key, value) {
  ws.setItem(key, JSON.stringify(value))
}

export function getStore(key) {

  try {
    return JSON.parse(ws.getItem(key))
  } catch (error) {
    return null;
  }

}

  export function removeStore(key) {
    ws.removeItem(key)
  }

  export function clearStore() {
    ws.clear()
  }