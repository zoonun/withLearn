// API
import $axios from 'axios'
import Swal from 'sweetalert2'

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
  Swal.fire({
    icon:'warning',
    html: '로그아웃 되었습니다.',
    showConfirmButton: false,
    timer:1000,
  })
  setTimeout(function(){
    document.location.reload();
  }, 1000);
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
    console.log('action : 사용가능')
    commit('setIsAvailableId')
  })
  .catch(() => {
    commit('setIsUnavailableId')
    Swal.fire({
      icon: 'warning',
      html: '이미 사용 중인 아이디입니다.',
      showConfirmButton: false,
      timer: 1000
    })
  })
}

export function requestSearchTitle({ commit }, payload) {
  const url = '/conferences'
  const body = payload
  commit('setSearchValue', body.title)
  return $axios.get(url, body)
  .then((res) => {
    commit('setConferenceData', res.data)
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
