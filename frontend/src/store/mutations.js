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
}

// Spinner Mutations
export function setSpinnerStart (state) {
  state.loadingStatus = true
}

export function setSpinnerEnd (state) {
  state.loadingStatus = false
}

// Id Dupication Check
export function setIsAvailableId (state) {
  state.isAvailableId = true
}

export function setIsUnavailableId (state) {
  state.isAvailableId = false
}
