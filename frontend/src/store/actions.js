// API
import $axios from 'axios'

export function requestLogin ({ state }, payload) {
  console.log('requestLogin', state, payload)
  const url = '/auth/login'
  let body = payload
  return $axios.post(url, body)
}

export function requestSignup ({ state }, payload) {
  console.log('requestSignup', state, payload)
  const url = '/users'
  let body = payload
  return $axios.post(url, body)
}

export function requestLogout ({ commit }) {
  console.log('requestLogout')
  localStorage.removeItem('user')
  alert('로그아웃 되었습니다.')
  document.location.reload()
  return commit('setLogout')
}

export function requestSaveJWT({ state }, user) {
  console.log('requestSaveJWT', state, user)
  return localStorage.setItem('user', JSON.stringify(user))
}

export function requestAvailableId({ commit }, id) {
  const url = `/users/${id}`
  return $axios.get(url)
  .then(() => {
    commit('setIsAvailableId')
  })
  .catch(() => {
    commit('setIsUnavailableId')
  })
}

// 컨퍼런스 액션
export function requestConferenceCreate({ state }, payload) {
  const url = '/conferences'
  let body = payload
  let config = {
    headers: {'Content-Type': 'multipart/form-data'}
  }
  return $axios.post(url, body, config)
}


export function requestConferenceId({ commit }) {
  const url = '/conference-categories'
  return $axios.get(url)
  .then((res) => {
    commit('setConferenceId', res.data.categoryList)
  })
  .catch(err => console.log(err))
}

export function requestConferenceIdCreate({ state }, payload) {
  const url = '/conference-categories'
  let body = payload
  return $axios.post(url, body)
}

export function requestConferenceIdDelete({ state }, payload) {
  const url = '/conference-categories'
  let body = payload
  console.log(body)
  return $axios.delete(url, {data: body})
}
