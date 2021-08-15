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
              <a href="" class="btn-wrap">홈</a>
            </ul>
          <p class="sub-menu-label">강의</p>
          <p class="sub-menu-label">커뮤니티</p>
          <p class="sub-menu-label">대시보드</p>
          <p class="sub-menu-label">위시리스트</p>
          <p class="sub-menu-label">설정</p>
          <p class="sub-menu-label">로그아웃</p>
        </div>
        <div class="grid-wrapper container col-md-8 col-12">
          <div class="row">
            <div class="grid-box col-md-6">
              <p class="grid-box-header">
                프로필
              </p>
              <p class="grid-box-body">
                {{ state.user.name }}님, 안녕하세요! 👋
              </p>
            </div>
            <div class="grid-box col-md-6">
              <p class="grid-box-header">
                지금 듣는 강의
              </p>
              <p class="grid-box-body">
                {{ state.user.name }}님, 안녕하세요! 👋
              </p>
            </div>
            <div class="grid-box col-md-6">
              <p class="grid-box-header">
                위시리스트
              </p>
              <p class="grid-box-body">
                {{ state.user.name }}님, 안녕하세요! 👋
              </p>
            </div>
            <div class="grid-box col-md-6">
              <p class="grid-box-header">
                구매 내역
              </p>
              <p class="grid-box-body">
                {{ state.user.name }}님, 안녕하세요! 👋
              </p>
            </div>
            <div class="grid-box col-md-6">
              <p class="grid-box-header">
                내 강의
              </p>
              <p class="grid-box-body">
                {{ state.user.name }}님, 안녕하세요! 👋
              </p>
            </div>
            <div class="grid-box col-md-6">
              <p class="grid-box-header">
                즐겨찾기
              </p>
              <p class="grid-box-body">
                {{ state.user.name }}님, 안녕하세요! 👋
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { reactive } from 'vue'
export default {
  name: 'dashboard',
  setup() {
    const state = reactive({
      user: {
        name: '',
      }
    })
    return { state }
  }
}
</script>

<style>

</style>
