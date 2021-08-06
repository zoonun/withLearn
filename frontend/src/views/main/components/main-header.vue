<template>
  <el-row
    class="main-header"
    :gutter="10"
    :style="{ 'height': height }">
    <div class="hide-on-small">
      <div class="logo-wrapper" @click="clickLogo"><div class="ic ic-logo"/></div>
      <div class="tool-wrapper">
        <div class="search-field">
          <el-input
            placeholder="검색"
            prefix-icon="el-icon-search"
            v-model="state.searchValue">
          </el-input>
        </div>
        <!-- 로그인 메뉴헤더 분기 -->
        <div class="button-wrapper" v-if="!state.isLogin">
          <el-button class="search-button" @click="clickSearch" style= width:10%;>
            <i :class="['ic', 'el-icon-search']"/>
          </el-button>
          <el-button @click="clickSignup">
            <i :class="['ic', 'el-icon-circle-plus-outline']"/>
            <span>회원 가입</span>
          </el-button>
          <el-button type="primary" @click="clickLogin">
            <i :class="['ic', 'el-icon-key']"/>
            <span>로그인</span>
          </el-button>
        </div>
        <div class="button-wrapper" v-else>
          <el-button class="search-button" @click="clickSearch" style= width:10%;>
            <i :class="['ic', 'el-icon-search']"/>
          </el-button>
          <el-button type="primary" @click="clickConference">
            <i :class="['ic', 'el-icon-circle-plus-outline']"/>
            <span>컨퍼런스 생성</span>
          </el-button>
          <el-button @click="clickProfile">
            <i :class="['ic', 'el-icon-user-solid']"/>
            <span>프로필</span>
          </el-button>
        </div>
      </div>
    </div>
    <div class="hide-on-big">
      <div class="menu-icon-wrapper" @click="changeCollapse"><i class="el-icon-menu"></i></div>
      <div class="logo-wrapper" @click="clickLogo"><div class="ic ic-logo"/></div>
      <div class="menu-icon-wrapper" @click="clickMobileSearch"><i class="el-icon-search"></i></div>
      <div class="mobile-sidebar-wrapper" v-if="!state.isCollapse">
        <div class="mobile-sidebar">
          <div class="logo-wrapper"><div class="ic ic-logo"/></div>
          <div class="mobile-sidebar-tool-wrapper" v-if="!state.isLogin">
            <el-button type="primary" class="mobile-sidebar-btn login-btn" @click="clickLogin">
              <i :class="['ic', 'el-icon-key']"/>
              <span>로그인</span>
            </el-button>
            <el-button class="mobile-sidebar-btn register-btn" @click="clickSignup">
              <i :class="['ic', 'el-icon-circle-plus-outline']"/>
              <span>회원 가입</span>
            </el-button>
          </div>
          <div class="mobile-sidebar-tool-wrapper" v-else>
            <el-button type="primary" class="mobile-sidebar-btn" @click="clickConference">
              <i :class="['ic', 'el-icon-circle-plus-outline']"/>
              <span>컨퍼런스 생성</span>
            </el-button>
            <el-button class="mobile-sidebar-btn" @click="clickProfile">
              <i :class="['ic', 'el-icon-user-solid']"/>
              <span>프로필</span>
            </el-button>
          </div>
          <el-menu
            :default-active="String(state.activeIndex)"
            active-text-color="#ffd04b"
            class="el-menu-vertical-demo"
            @select="menuSelect">
            <el-menu-item v-for="(item, index) in state.menuItems" :key="index" :index="index.toString()">
              <i v-if="item.icon" :class="['ic', item.icon]"/>
              <span>{{ item.title }}</span>
            </el-menu-item>
          </el-menu>
        </div>
        <div class="mobile-sidebar-backdrop" @click="changeCollapse"></div>
      </div>
    </div>
  </el-row>
</template>
<script>
import { reactive, computed, onMounted } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
  name: 'main-header',
  props: {
    height: {
      type: String,
      default: '70px'
    }
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

    const clickSearch = () => {
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
    }

    const clickMobileSearch = () => {
      emit('openSearchDialog')
    }

    return { state, menuSelect, clickLogo, clickLogin, clickSignup, changeCollapse, clickSearch, clickMobileSearch, clickProfile, clickConference }
  }
}

</script>
<style>
  .main-header {
    padding: 10px 20px;
  }
  /*Mobile, Tablet*/
  .menu-icon-wrapper {
    display: inline-block;
    vertical-align: top;
    position: relative;
    top: 14px;
  }

  .main-header .hide-on-big .logo-wrapper {
    display: inline-block;
    margin: 0 calc(50% - 51px)
  }
  .main-header .hide-on-big .logo-wrapper .ic.ic-logo {
    width: 70px;
    height: 50px;
    background-size: contain;
    background-repeat: no-repeat;
    background-image: url('../../../assets/images/ssafy-logo.png');
  }
  .mobile-sidebar-wrapper {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
  }
  .mobile-sidebar-wrapper .mobile-sidebar {
    width: 240px; height: calc(100vh - 1px);
    display: inline-block;
    background-color: white;
    padding: 0 10px;
    vertical-align: top;
  }
  .mobile-sidebar-wrapper .mobile-sidebar .mobile-sidebar-tool-wrapper {
    padding-bottom: 20px;
  }
  .mobile-sidebar-wrapper .mobile-sidebar .mobile-sidebar-btn {
    display: block;
    margin: 0 auto;
    margin-top: 25px;
    height: 30px;
    width: 100%;
  }
  .mobile-sidebar-wrapper .mobile-sidebar .mobile-sidebar-btn.login-btn {
    color: white;
  }
  .mobile-sidebar-wrapper .mobile-sidebar .logo-wrapper {
    display: block
  }
  .mobile-sidebar-wrapper .mobile-sidebar .logo-wrapper .ic.ic-logo {
    width: 70px;
    height: 50px;
    margin: 0 auto;
    margin-top: 30px;
    background-size: contain;
    background-repeat: no-repeat;
    background-image: url('../../../assets/images/ssafy-logo.png');
  }
  .mobile-sidebar-wrapper .mobile-sidebar-backdrop {
    width: calc(100% - 260px); height: calc(100vh - 1px);
    background-color: black;
    display: inline-block;
    opacity: 0.3;
  }
  .mobile-sidebar-wrapper .el-menu{
    margin-top: 0;
    padding-left: 0;
    height: calc(100% - 235px);
  }
  .mobile-sidebar-wrapper .el-menu .el-menu-item {
    cursor: pointer;
  }
  .mobile-sidebar-wrapper .el-menu .el-menu-item .ic {
    margin-right: 5px;
  }

  /*Desktop - Need to add Class if Need*/
  .main-header .hide-on-small .logo-wrapper {
    cursor: pointer;
    display: inline-block;
  }
  .main-header .hide-on-small .logo-wrapper .ic.ic-logo {
    width: 70px;
    height: 50px;
    background-size: contain;
    background-repeat: no-repeat;
    background-image: url('../../../assets/images/ssafy-logo.png');
  }
  .main-header .hide-on-small .tool-wrapper {
    width: 65%;
    float: right;
  }
  .main-header .hide-on-small .tool-wrapper .button-wrapper {
    width: 55%;
    float: right;
  }
  .main-header .hide-on-small .tool-wrapper .button-wrapper .el-button {
    width: 30%;
    height: 50px;
    cursor: pointer;
    margin-right: 1%;
  }
  .main-header .hide-on-small .tool-wrapper .search-field {
    width: 50%;
    height: 50px;
    max-width: 400px;
    margin-right: 2%;
    display: inline-block;
    background-color: white;
  }
  .main-header .hide-on-small .tool-wrapper .search-field .el-input {
    width: 100%;
    height: 100%;
  }
  .main-header .hide-on-small .tool-wrapper .search-field .el-input .el-input__inner {
    width: 88%;
    height: 50px;
    margin-right: 1%;
  }
  .main-header .hide-on-small .tool-wrapper .search-field .el-input .el-input__prefix {
    top: 5px;
  }

</style>
