<template>
  <nav>
    <!-- Website Logo -->
    <div id="start" class="nav-items">
      <a href="/">
        <img class="nav-logo" :src="state.images.logo" alt="위드런 로고">
      </a>
    </div>
    <!-- Search Bar -->
    <div class="search-items">
      <input type="text" placeholder="검색" v-model="state.searchValue" @keyup.enter="clickSearch" class="search-bar">
      <button class="search-btn" @click="clickSearch">
        <i class="el-icon-search"></i>
      </button>
    </div>
    <div id="end" class="nav-items">
      <div class="nav-dropdown">
        <a class="btn nav-end-item dropbtn">강의
          <i class="fa fa-caret-down"></i>
        </a>
        <div class="nav-dropdown-content">
          <a @click="clickConferenceList">강의 목록으로</a>
          <a @click="clickConference">강의 개설하기</a>
          <a @click="clickConference">강의 참여하기</a>
        </div>
      </div>
      <router-link to="/community" class="btn nav-end-item">커뮤니티</router-link>
      <a class="btn nav-end-item">위드런</a>
      <router-link to="/dashboard" class="btn nav-end-item">대시보드
      </router-link>
      <div v-if="!state.isLogin">
        <button class="btn btn-orange" @click="clickLogin">
          로그인
        </button>
        <button class="btn btn-transparent" @click="clickSignup">
          회원가입
        </button>
      </div>
      <div v-else>
        <div class="nav-dropdown">
          <img class="nav-icon-dropbtn" :src="state.images.icon" alt="유저 아이콘">
          <div class="nav-dropdown-content-right">
            <a>설정</a>
            <a class="btn-icon" @click="clickLogout">
              로그아웃
              <i :class="['ic', 'el-icon-switch-button']"/>
            </a>
          </div>
        </div>
      </div>
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
      images: {
        logo: require('@/assets/images/logo.png'),
        icon: require('@/assets/images/user_icon.png')
      },
      isLogin: computed(() => store.getters['root/getIsLoggedIn']),
      searchValue:null,
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
      store.dispatch('root/requestProfile')
      emit('openProfileDialog')
    }

    const clickSearch = async () => {
      store.commit('root/setSpinnerStart')
      console.log('clickSearch')
      const payload = {
        title: state.searchValue,
        sort:null,
        order: null,
        page: null,
        size: 20,
        conference_category: null,
      }
      console.log(payload)
      await store.dispatch('root/requestSearchTitle', payload)
      .then(() => {
        router.push({
          name: 'search',
          params: {
            searchValue: state.searchValue
          }
        })
      })
      .finally(store.commit('root/setSpinnerEnd'))
    }

    const clickLogout = () => {
      store.dispatch('root/requestLogout')
    }

    const clickConferenceList = async function () {
      await router.push({
        name: 'search',
        params: {
          searchValue: 1,
        }
      })
    }


    return { state, clickLogin, clickSignup, clickProfile, clickConference, clickLogout, clickSearch, clickConferenceList }
  }
}

</script>
<style>
.search-bar {
  font-size: 1.8rem;
  font-weight: bold;
  min-width: 90px;
  border-radius: 5px;
  background-color: var(--white-color);
  height:100%;
  border:0;
}
.search-btn {
  font-size: 2rem;
  font-weight: bold;
  border-radius: 5px;
  background-color: var(--indigo-color);
  border: 0;
  cursor: pointer;
  color: white;
  height: 100%;
  width:4rem;
}
.search-items {
  height: 50px;
  align-items: center;
  display: flex;
  flex-wrap: wrap;
  border-radius: 5px;
  justify-content: center;
  border-width: 4px;
  border:0;
  border: 3px solid var(--indigo-color);
}
input[type="text"]:focus {
  border-color: var(--indigo-color);
  outline: 0 none;
}
</style>
