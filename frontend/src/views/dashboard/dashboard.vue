<template>
  <div class="small-banner">
    <p class="small-banner-text">
      대시보드
    </p>
  </div>
  <div class="sub-wrap">
    <div class="sub-body container">
      <div class="row">
        <div class="sub-menu col-md-4 col-12">
          <p class="sub-menu-label">Home</p>
            <ul class="sub-menu-list">
              <li>
                <a href="" class="btn-wrap">대시보드</a>
              </li>
              <li>
                <a href="" class="btn-wrap">알림</a>
              </li>
            </ul>
          <p class="sub-menu-label">스터디에서 멘티</p>
            <ul class="sub-menu-list">
              <li>
                <a href="" class="btn-wrap">참여한 스터디</a>
              </li>
              <li>
                <a href="" class="btn-wrap">구매 내역</a>
              </li>
              <li>
                <a href="" class="btn-wrap">위시리스트</a>
              </li>
            </ul>
          <p class="sub-menu-label">스터디에서 멘토</p>
            <ul class="sub-menu-list">
              <li>
                <a href="" class="btn-wrap">스터디 관리</a>
              </li>
            </ul>
          <p class="sub-menu-label">설정</p>
            <ul class="sub-menu-list">
              <li>
                <a href="" class="btn-wrap">설정</a>
              </li>
              <li>
                <a href="" class="btn-wrap" @click="clickLogout">로그아웃</a>
              </li>
            </ul>
        </div>
        <div class="grid-wrapper container col-md-8 col-12">
          <div class="row">
            <div class="grid-box col-md-6">
              <p class="grid-box-header">
                🤩 프로필
              </p>
              <p class="grid-box-body">
                {{ state.userName }}님, 안녕하세요! 👋
              </p>
            </div>
            <div class="grid-box col-md-6">
              <p class="grid-box-header">
                👩‍💻 최근 참여한 스터디
              </p>
              <p class="grid-box-body">
              </p>
            </div>
            <div class="grid-box col-md-6">
              <p class="grid-box-header">
                👏 나의 스터디 한줄평
              </p>
              <p class="grid-box-body">

              </p>
            </div>
            <div class="grid-box col-md-6">
              <p class="grid-box-header">
                📊 스터디 통계
              </p>
              <p class="grid-box-body">

              </p>
            </div>
            <div class="grid-box col-md-6">
              <p class="grid-box-header">
                🙆 이 팀장님이 좋아요
              </p>
              <p class="grid-box-body">

              </p>
            </div>
            <div class="grid-box col-md-6">
              <p class="grid-box-header">
                🌟 스터디 즐겨찾기
              </p>
              <p class="grid-box-body">

              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { onMounted, computed, reactive, onUpdated } from 'vue'
import { useStore } from 'vuex'

export default {
  name: 'dashboard',
  setup() {
    const store = useStore()
    const state = reactive({
      userName: computed(() => store.getters['root/getUserName']),
      userId: computed(() => store.getters['root/getUserId']),
    })


    onMounted(() => {
      // console.log(state.userInfo)
    })

    onUpdated(() => {
    })

    const clickLogout = function () {
      store.dispatch('root/requestLogout')
    }

    return { state, clickLogout }
  }
}
</script>

<style>

</style>
