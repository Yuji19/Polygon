import Cookies from 'js-cookie'

const TokenKey = 'vue_admin_template_token'

export function getToken() {
  return Cookies.get(TokenKey)
}

export function setToken(token) {
  return Cookies.set(TokenKey, token)
}

export function removeToken() {
  return Cookies.remove(TokenKey)
}

export function getLogin() {
	return Cookies.get("isLogin")
}

export function setLogin(isLogin) {
	return Cookies.set("isLogin",isLogin)
}

export function removeLogin(){
	return Cookies.remove("isLogin")
}