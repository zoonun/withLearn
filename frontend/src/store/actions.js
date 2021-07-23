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

export function saveJWT({ state }, user) {
  console.log('saveJWT', state, user)
  return localStorage.setItem('user', JSON.stringify(user))
}

export function checkDuplicate({ commit }, id) {
  const url = `/users/${id}`
  return $axios.get(url)
  .then(() => {
    commit('availableId')
  })
  .catch(() => {
    commit('unAvailableId')
  })
}

export function requestSearchTitle({commit}, payload) {
  const url = '/conference'
  const body = payload
  return $axios.get(url, body)
  .then((res) => {
    console.log(res.data.content)
    console.log('request setConferenceData')
    commit('setConferenceData', res.data.content)
  })
}
