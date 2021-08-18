// 플랫폼 관련 정보 가져오기
export function getIsDesktopPlatform (state) {
  return state.isDesktopPlatform
}
// 메뉴 객체 가져오기
export function getMenus (state) {
	return state.menus
}
// Active된 메뉴 인덱스 가져오기
export function getActiveMenuIndex (state) {
	const keys = Object.keys(state.menus)
	return keys.findIndex(item => item === state.activeMenu)
}
// 로그인 상태 가져오기
export function getIsLoggedIn (state) {
  return state.isLoggedIn
}

// Spinning 상태 가져오기
export function getIsSpinning (state) {
  return state.loadingStatus
}

export function getIsAvailableId (state) {
  return state.isAvailableId
}

export function getConferenceId (state) {
  return state.conferenceId
}

export function getSortIndex (state) {
  return state.SortIndex
}

export function getConference (state) {
  return state.conferenceData
}

export function getSearchValue (state) {
  return state.recentSearchValue
}

export function getProfile (state) {
  return state.profile
}

export function getConferenceDetail (state) {
  return state.conferenceDetailData
}

export function getCommunityData (state) {
  return state.communityData
}

export function getUserName (state) {
  return state.userName
}

export function getUserId (state) {
  return state.userId
}
