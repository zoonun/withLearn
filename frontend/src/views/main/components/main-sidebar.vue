<template>
  <div>

  </div>
</template>
<style>
</style>
<script>
import { reactive, computed } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
  name: 'main-header',

  props: {
    width: {
      type: String,
      default: '240px'
    }
  },
  setup() {
    const store = useStore()
    const router = useRouter()

    const state = reactive({
      searchValue: null,
      menuItems: computed(() => {
        // 로그인한 상태
        const MenuItems = store.getters['root/getMenus']
        if (store.getters['root/getIsLoggedIn']) {

          let keys = Object.keys(MenuItems)
          let menuArray = []
          for (let i = 0; i < keys.length; ++i) {
            let menuObject = {}
            menuObject.icon = MenuItems[keys[i]].icon
            menuObject.title = MenuItems[keys[i]].name
            menuArray.push(menuObject)
          }
          return menuArray
        }
        // 로그인 하지 않은 상태
        else {
          const MenuItems = store.getters['root/getMenus']
          let menuArray = []
          let homeObject = {}
          homeObject.icon = MenuItems['home'].icon
          homeObject.title = MenuItems['home'].name
          menuArray.push(homeObject)

          return menuArray
        }
      }),
      activeIndex: computed(() => store.getters['root/getActiveMenuIndex'])
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

    const clickLogout = function (param) {
      store.dispatch('root/requestLogout', param)
    }

    return { state, menuSelect, clickLogout }
  }
}
</script>
