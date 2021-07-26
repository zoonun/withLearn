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

export function setTitleSortIndex (state) {
  console.log('setTitleSortIndex')
  state.titleSortIndex = (state.titleSortIndex + 1) % 2
}

export function setConferenceData (state, conferenceData){
  state.conferenceData = conferenceData
}

