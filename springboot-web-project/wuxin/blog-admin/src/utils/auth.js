import Cookies from 'js-cookie'

const TokenKey = 'Authentication'
const Roles = 'Roles'

const UserInfo = 'userinfo'

export function getToken() {
  return Cookies.get(TokenKey)
}

export function setToken(token) {
  return Cookies.set(TokenKey, token)
}

export function removeToken() {
  return Cookies.remove(TokenKey)
}

export function getRoles() {
  return Cookies.get(Roles)
}

export function setRoles(roles) {
  return Cookies.set(Roles, roles)
}

export function removeRoles() {
  return Cookies.remove(Roles)
}
