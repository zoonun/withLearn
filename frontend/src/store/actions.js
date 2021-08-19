// API
import $axios from 'axios'
import Swal from 'sweetalert2'

export function requestLogin ({}, payload) {
  const url = '/auth/login'
  let body = payload
  return $axios.post(url, body)
}

export function requestSignup ({}, payload) {
  const url = '/users'
  let body = payload
  return $axios.post(url, body)
}

export function requestLogout ({ commit }) {
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

export function requestSaveJWT({}, user) {
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
  return $axios.get(url, { params: {
    title: body.title,
    sort: body.sort,
    conferenceCategory: body.conference_category
  }})
  .then((res) => {
    console.log(res.data)
    commit('setConferenceData', res.data.conferenceList)
  })
}

// 컨퍼런스 액션
export function requestConferenceCreate({}, payload) {
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

export function requestConferenceIdCreate({}, payload) {
  const url = '/conference-categories'
  let body = payload
  return $axios.post(url, body)
}

export function requestConferenceIdDelete({}, payload) {
  const url = '/conference-categories'
  let body = payload
  return $axios.delete(url, {data: body})
}

export function requestProfile( { commit }) {
  const url = '/users/me'
  return $axios.get(url)
  .then((res) => {
    commit('setProfile', res.data)
  })
}

export function requestUpdate({ commit }, payload) {
  const id = payload.id
  const url = `/users/${id}`
  let body = payload
  return $axios.patch(url, body)
  .then(() =>{
    commit('setUpdate', body)
  })
}

export function requestConferenceDetail({ commit }, conference_id) {
  const url = `/conferences/${conference_id}`
  return $axios.get(url)
  .then((res) => {
    commit('setConferenceDetail', res.data)
  })
}

export function requestCommunity({ commit }) {
  const url = '/community'
  return $axios.get(url)
  .then((res) => {
    commit('setCommunityData', res.data.communityList)
  })
}

export function requestCommunityCreate({}, payload) {
  const url = '/community'
  let body = payload
  let config = {
    headers: {'Content-Type': 'multipart/form-data'}
  }
  return $axios.post(url, body, config)
}

export function requestCommunityDetail({ commit }, payload) {
  return commit('setCommunityDetail', payload)
}


export function requestCommentCreate({}, payload) {
  const url = '/comment'
  return $axios.post(url, null,{params:{
    descript:payload.descript,
    communityId: payload.communityId
  }})
}

export function requestCommentList({ commit }, postId) {
  const url = '/comment'
  return $axios.get(url, {params:{
    communityId: postId
  }})
  .then((res) => {
    commit('setCommentList', res.data.commentList)
  })
}

export function requestCommentDelete({}, commentId) {
  const url = `/comment/${commentId}`
  return $axios.delete(url)
}

export function requestConferenceJoin({}, conference_id) {
  const url = '/conferences/join'
  return $axios.post(url, null, { params: {
    conferenceId: conference_id
  }})
}

export function requestConferenceOnBoarding({}, conference_id) {
  const url = '/conferences/onBoarding'
  return $axios.patch(url, null, { params: {
    conferenceId: conference_id
  }})
}
