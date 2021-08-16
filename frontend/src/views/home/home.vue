<template>
  <div class="home-wrap">
    <div class="banner">
      <div class="banner-text">
        <div data-aos="fade-right" data-aos-duration="1400">
          <p class="banner-big">보고 듣기만 하는 인터넷 강의는 이제 그만!</p>
          <p class="banner-small">실시간 화상통화로 소통하는 ON택트 스터디 플랫폼 위드런에</p>
          <p class="banner-small">지금 참여해 보세요.</p>
        </div>
        <img id="banner-image-1" :src="state.images.home_1" alt="banner_image">
      </div>
    </div>
    <div class="content">
      <img id="content-image-1" :src="state.images.home_2" alt="content_image">
      <div class="content-text">
        <p class="content-big">With</p>
        <p class="content-small">보기만 하는 수동적 강의는 많습니다.</p>
        <p class="content-small">위드런은 듣고 말하며 능동적으로 참여하는 웹 플랫폼입니다.</p>
        <br>
        <p class="content-big">Learn</p>
        <p class="content-small">실제로 만나 스터디하는 듯한 즐거운 시간을 보내며,</p>
        <p class="content-small">강사와 참여자 모두에게 배웁니다.</p>
      </div>
    </div>
    <!-- 팀원 소개 -->
    <div class="content" data-aos="zoom-in-up" data-aos-duration="1300">
      <img id="content-image-1" :src="state.images.home_2" alt="content_image">
      <div class="content-text">
        <p class="content-big">With</p>
        <p class="content-small">보기만 하는 수동적 강의는 많습니다.</p>
        <p class="content-small">위드런은 듣고 말하며 능동적으로 참여하는 웹 플랫폼입니다.</p>
        <br>
        <p class="content-big">Learn</p>
        <p class="content-small">실제로 만나 스터디하는 듯한 즐거운 시간을 보내며,</p>
        <p class="content-small">강사와 참여자 모두에게 배웁니다.</p>
      </div>
    </div>
    <!-- 문서 링크 -->
    <div class="content" data-aos="zoom-in-down" data-aos-duration="1300">
      <img id="content-image-1" :src="state.images.home_2" alt="content_image">
      <div class="content-text">
        <p class="content-big">With</p>
        <p class="content-small">보기만 하는 수동적 강의는 많습니다.</p>
        <p class="content-small">위드런은 듣고 말하며 능동적으로 참여하는 웹 플랫폼입니다.</p>
        <br>
        <p class="content-big">Learn</p>
        <p class="content-small">실제로 만나 스터디하는 듯한 즐거운 시간을 보내며,</p>
        <p class="content-small">강사와 참여자 모두에게 배웁니다.</p>
      </div>
    </div>
  </div>
</template>

<style>

</style>
<script>
import { reactive } from 'vue'

export default {
  name: 'Home',
  setup () {
    const state = reactive({
      images: {
        home_1: require('@/assets/images/home_1.svg'),
        home_2: require('@/assets/images/home_2.svg'),
      }
    })

    return { state }
  }
}
</script>
