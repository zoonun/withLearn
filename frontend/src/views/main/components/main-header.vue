<template>
  <nav>
    <!-- Website Logo -->
    <div id="start" class="nav-items">
      <img class="nav-logo" :src="state.images.logo" alt="">
    </div>
    <!-- Search Bar -->
    <div id="middle" class="nav-items">

    </div>
    <div id="end" class="nav-items">
      <button class="btn">강의</button>
      <button class="btn">커뮤니티</button>
      <button class="btn">위드런</button>
      <button class="btn btn-orange" @click="clickLogin">로그인</button>
      <button class="btn btn-transparent" @click="clickSignup">회원가입</button>
    </div>
  </nav>

</template>
<script>
import { reactive, computed, onMounted } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
  name: 'main-header',
  props: {
  },
  setup(props, { emit }) {
    const store = useStore()
    const router = useRouter()
    const state = reactive({
      sort: 'title',
      order: 'asc',
      conference_category:null,
      searchValue: null,
      isCollapse: true,
      images: {
        logo: require('@/assets/images/logo.png'),
      },
      menuItems: computed(() => {
        const MenuItems = store.getters['root/getMenus']
        let keys = Object.keys(MenuItems)
        let menuArray = []
        for (let i = 0; i < keys.length; ++i) {
          let menuObject = {}
          menuObject.icon = MenuItems[keys[i]].icon
          menuObject.title = MenuItems[keys[i]].name
          menuArray.push(menuObject)
        }
        return menuArray
      }),
      activeIndex: computed(() => store.getters['root/getActiveMenuIndex']),
      isLogin: computed(() => store.getters['root/getIsLoggedIn']),
    })

    if (state.activeIndex === -1) {
      state.activeIndex = 0
      store.commit('root/setMenuActive', 0)
    }

    const menuSelect = function (param) {
      store.commit('root/setMenuActive', param)
      const MenuItems = store.getters['root/getMenus']
      let keys = Object.keys(MenuItems)
      router.push({
        name: keys[param]
      })
    }

    const clickLogo = () => {
      store.commit('root/setMenuActive', 0)
      const MenuItems = store.getters['root/getMenus']
      let keys = Object.keys(MenuItems)
      router.push({
        name: keys[0]
      })
    }

    onMounted(() => {

    })

    const clickLogin = () => {
      emit('openLoginDialog')
    }

    const clickSignup = () => {
      emit('openSignupDialog')
    }

    const clickConference = () => {
      emit('openConferenceDialog')
    }

    const clickProfile = () => {
      emit('openProfileDialog')
      store.dispatch('root/requestProfile')
    }

    const changeCollapse = () => {
      state.isCollapse = !state.isCollapse
    }

    const clickSearch = async () => {
      console.log('clickSearch')
      const payload = {
        title: state.searchValue,
        sort: [state.sort, state.order],
        page: null,
        size: 10,
        conference_category: state.conference_category,
      }
      console.log(payload)
      store.dispatch('root/requestSearchTitle', payload)
      await router.push({
        name: 'search',
        params: {
          searchValue: state.searchValue
        }
      })
    }

    const clickMobileSearch = () => {
      emit('openSearchDialog')
    }

    return { state, menuSelect, clickLogo, clickLogin, clickSignup, changeCollapse, clickSearch, clickMobileSearch, clickProfile, clickConference }
  }
}

</script>
<style>
</style>
