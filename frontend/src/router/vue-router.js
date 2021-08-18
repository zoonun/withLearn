import { createRouter, createWebHistory } from 'vue-router'
import Home from '@/views/home/home'
import ConferencesDetail from '@/views/conferences/conference-detail'
import History from '@/views/history/history'
import store from '@/api/store'
import Groupcall from '@/views/conferences/groupcall'
import fullMenu from '@/views/main/menu.json'
import Search from '@/views/searches/search'
import Dashboard from '@/views/dashboard/dashboard.vue'
import PageNotFound from '@/components/PageNotFound'
import Community from '@/views/home/components/community'
import PostDetail from '@/views/community/post-detail'

const beforeAuth = isAuth => (from, to, next) => {
  const isAuthenticated = store.getters['root/getIsLoggedIn']

  if ((isAuth && isAuthenticated) || (!isAuth && !isAuthenticated)) {
    store.dispatch('root/requestProfile')
    next()
  } else {
    alert('로그인이 필요한 페이지입니다.')
    setTimeout(() => {
      next('/')
    }, 1000)
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
    } else if (key == 'search'){
      return { path: fullMenu[key].path, name: key, component: Search }
    } else {
      return null
    }
  })
  // 로그아웃 파싱한 부분 제거
  routes = routes.filter(item => item)
  // menu 자체에는 나오지 않는 페이지 라우터에 추가(방 상세보기)
  routes.push({
    path: '/groupcall/:roomId',
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
    path: '/conferences/:conferenceId',
    name: 'conference-detail',
    component: ConferencesDetail,
    beforeEnter: beforeAuth(true)
  },
  {
    path: '/:pathMatch(.*)*',
    component: PageNotFound
  },
  {
    path:'/community',
    name:'community',
    component: Community
  },
  {
    path:'/community/:postId',
    name: 'post-detail',
    component: PostDetail,
    props: true,
    beforeEnter: beforeAuth(true)
  }
  )
  return routes
}

const routes = makeRoutesFromMenu()

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
