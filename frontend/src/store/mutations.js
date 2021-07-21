export function setPlatform (state, isDesktop) {
  state.isDesktopPlatform = isDesktop
}

export function setMenuActive (state, index) {
	console.log('setMenuActive', state,index)
	const keys = Object.keys(state.menus)
	state.activeMenu = keys[index]
}

export function setMenuActiveMenuName (state, menuName) {
	state.activeMenu = menuName
}

// User management Mutations
export function setLogout (state) {
  state.isLoggedIn = false
  localStorage.removeItem('user')
  alert('로그아웃 되었습니다.')
  document.location.reload()
}

// Spinner Mutations
export function startSpinner (state) {
  state.loadingStatus = true
}

export function endSpinner (state) {
  state.loadingStatus = false
}

// Id Dupication Check
export function availableId (state) {
  state.isAvailable = true
}

export function unAvailableId (state) {
  state.isAvailable = false
}

export function setProfile (state, profileItem) {
  state.profile = profileItem
}
