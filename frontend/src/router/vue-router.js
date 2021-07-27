import { createRouter, createWebHistory } from 'vue-router'
import Home from '@/views/home/home'
import ConferencesDetail from '@/views/conferences/conference-detail'
import History from '@/views/history/history'
import store from '@/api/store'
import fullMenu from '@/views/main/menu.json'

// const fullMenu = require('@/views/main/menu.json')
function makeRoutesFromMenu () {
  let routes = Object.keys(fullMenu).map((key) => {
    if (key === 'home') {
      return { path: fullMenu[key].path, name: key, component: Home  }
    } else if (key === 'history') {
      return { path: fullMenu[key].path, name: key, component: History }
    } else if (key === 'logout'){
      return { path: fullMenu[key].path, name: key, component: Home }
    }
  })
  // 로그아웃 파싱한 부분 제거
  routes = routes.filter(item => item)
  // menu 자체에는 나오지 않는 페이지 라우터에 추가(방 상세보기)
  routes.push({
    path: '/conferences/:conferenceId',
    name: 'conference-detail',
    component: ConferencesDetail
  })
  return routes
}

const routes = makeRoutesFromMenu()

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  /**
   * to: 이동할 url
   * from: 현재 url
   * next: to에서 지정한 url로 이동하기 위해 꼭 호출해야 하는 함수
   * next()가 호출되기 전까진 화면 전환되지 않음
   * refresh의 await 처리를 해야 한다면 async 사용할 것. 하지만 setTimeout과 겹치니 주의!
   */
  // refreshToken은 있고 accessToken이 없을 경우 토큰 재발급
  // accessToken이 있을 경우
  // 2개 토큰이 모두 없을 경우 로그인하라는 alert
  const user = JSON.parse(localStorage.getItem('user'))

  if (user && user.accessToken) {
    store.commit('root/setSpinnerStart')
    setTimeout(() => {
      next()
    }, 100)
    console.log('routing success : \'' + to.path + '\'')
  }
})

router.afterEach(() => {
  store.commit('root/setSpinnerEnd')
})

export default router
