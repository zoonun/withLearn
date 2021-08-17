<template>
  <div class="detail-header">
    강의 상세 페이지
  </div>
  <div class="detail-thumbnail-wrapper">
    <img :src="state.images.image" alt="" class="datail-thumbnail">
  </div>
  <div class="detail-information">
    <div class="detail-description">
      입문자들을 위한
      <br>
      보컬 트레이닝 클래스입니다.
    </div>
    <!-- 스터디 개설자일 경우에는 그룹콜 켤수 있도록 한다 -->
    <div class="detail-isactive">
      현재 라이브 진행 중!
      <button class="live-button" @click="onClickLive">
        라이브 참여하기
      </button>
    </div>
  </div>
  <div class="detail-bottom">
    시청하기
    <ul class="detail-list">
      <li class="detail-bottom-button" v-for="(idx) in 3" :key="idx">
        소주한잔 원키 올리기
      </li>
    </ul>
  </div>
</template>
<style>

</style>

<script>
import { reactive, onMounted, onUnmounted, computed } from 'vue'
import { useStore } from 'vuex'
import { useRoute } from 'vue-router'

export default {
  name: 'conference-detail',

  setup () {
    const route = useRoute()
    const store = useStore()
    const state = reactive({
      conferenceId: '',
      conferenceDetail: computed(() => store.getters['root/getConferenceDetail']),
      userInfo: computed(() => store.getters['root/getProfile']),
      images: {
        image: require('@/assets/images/music.jpg'),
      },
    })

    // 페이지 진입시 불리는 훅
    onMounted(() => {
      state.conferenceId = route.params.conferenceId
      store.dispatch('root/requestConferenceDetail', state.conferenceId)
    })

    // 페이지 이탈시 불리는 훅
    onUnmounted(() => {
      state.conferenceId = ''
    })

    const onClickLive = function () {
      window.location = `/groupcall/${state.conferenceId}`
    }
    const onClickLive2 = function () {
      window.location = `/groupcall2/${state.conferenceId}`
    }

    return { state, onClickLive, onClickLive2 }
  }
}
</script>
