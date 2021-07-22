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
  commit('setLogout')
}

export function saveJWT({ state }, user) {
  console.log('saveJWT', state, user)
  return localStorage.setItem('user', JSON.stringify(user))
}

export function checkDuplicate({ commit }, id) {
  const url = `/users/${id}`
  return $axios.get(url)
  .then((res) => {
    commit('availableId')
  })
  .catch((err) => {
    commit('unAvailableId')
  })
}

export function requestProfile( { commit }) {
  console.log('requestProfile')
  const url = '/users/me'
  return $axios.get(url)
  .then((res) => {
    console.log('requestProfile', res.data)
    commit('setProfile', res.data)
  })
}

export function requestUpdate({ commit }, payload) {
  const id = payload.id
  const url = `/users/${id}`
  let body = payload
  console.log('requestUpdate')
  return $axios.patch(url, body)
  .then((res) =>{
    console.log('requestUpdate', res.status)
    commit('setUpdate', body)
  })
}
