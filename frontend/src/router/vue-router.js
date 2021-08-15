import { createRouter, createWebHistory } from 'vue-router'
import Home from '@/views/home/home'
import ConferencesDetail from '@/views/conferences/conference-detail'
import History from '@/views/history/history'
import store from '@/api/store'
import Lobby from '@/views/conferences/lobby'
import Groupcall from '@/views/conferences/groupcall'
import Websocket from '@/views/conferences/websocket'
import Socketjs from '@/views/conferences/socketjs'
import fullMenu from '@/views/main/menu.json'
import Search from '@/views/searches/search'
import Dashboard from '@/views/dashboard/dashboard.vue'
import PageNotFound from '@/components/PageNotFound'

const beforeAuth = isAuth => (from, to, next) => {
  const isAuthenticated = store.getters['root/getIsLoggedIn']
  if ((isAuth && isAuthenticated) || (!isAuth && !isAuthenticated)) {
    return next()
  } else {
    alert('로그인이 필요한 페이지입니다.')
    setTimeout(() => {
      next('/')
    }, 1500)
  }
}

function makeRoutesFromMenu () {
  let routes = Object.keys(fullMenu).map((key) => {
    if (key === 'home') {
      return { path: fullMenu[key].path, name: key, component: Home  }
    } else if (key === 'history') {
      return { path: fullMenu[key].path, name: key, component: History, beforeEnter: beforeAuth(true)}
    } else if (key === 'logout'){
      return { path: fullMenu[key].path, name: key, component: Home, beforeEnter: beforeAuth(true) }
    } else if (key === 'lobby'){
      return { path: fullMenu[key].path, name: key, component: Lobby, beforeEnter: beforeAuth(true) }
    } else if (key === 'websocket'){
      return { path: fullMenu[key].path, name: key, component: Websocket, beforeEnter: beforeAuth(true) }
    } else if (key === 'socketjs'){
      return { path: fullMenu[key].path, name: key, component: Socketjs, beforeEnter: beforeAuth(true) }
    } else {
      return null
    }
  })
  // 로그아웃 파싱한 부분 제거
  routes = routes.filter(item => item)
  // menu 자체에는 나오지 않는 페이지 라우터에 추가(방 상세보기)
  routes.push(
  {
    path: '/conferences/:conferenceRoomId',
    name: 'confenrenceDetail',
    component: ConferencesDetail,
    beforeEnter: beforeAuth(true)
  },
  {
    path: '/groupcall/:groupcallRoomId',
    name: 'groupcall',
    component: Groupcall,
    beforeEnter: beforeAuth(true)
  },
  {
    path:'/search/:searchValue',
    name:'search',
    component: Search,
    beforeEnter: beforeAuth(true)
  },
  {
    path:'/dashboard',
    name:'dashboard',
    component: Dashboard,
    beforeEnter: beforeAuth(true)
  },
  {
    path: '/:pathMatch(.*)*',
    component: PageNotFound
  }
  )
  return routes
}

const routes = makeRoutesFromMenu()

const router = createRouter({
  history: createWebHistory(),
  routes
})

// router.beforeEach((to, from, next) => {
//   /**
//    * to: 이동할 url
//    * from: 현재 url
//    * next: to에서 지정한 url로 이동하기 위해 꼭 호출해야 하는 함수
//    * next()가 호출되기 전까진 화면 전환되지 않음
//    * refresh의 await 처리를 해야 한다면 async 사용할 것. 하지만 setTimeout과 겹치니 주의!
//    */
//   // refreshToken은 있고 accessToken이 없을 경우 토큰 재발급
//   // accessToken이 있을 경우
//   // 2개 토큰이 모두 없을 경우 로그인하라는 alert
//   const user = JSON.parse(localStorage.getItem('user'))

//   if (user && user.accessToken) {
//     store.commit('root/setSpinnerStart')
//     setTimeout(() => {
//       next()
//     }, 100)
//     console.log('routing success : \'' + to.path + '\'')
//   }
// })
// router.afterEach(() => {
//   store.commit('root/setSpinnerEnd')
// })

export default router
