<template>
  <div class="small-banner">
    <p class="small-banner-text">
      클래스 상세 페이지
    </p>
  </div>
  <div class="detail-wrap">
    <div class="detail-banner">
      <img :src="state.images.detail_banner" alt="banner_image">
    </div>
    <div class="detail-control" v-if="state.conferenceDetail.owner.userId === state.userId">
      <div>
        스터디 개설자 {{ state.userId }}님, 안녕하세요! 👋
      </div>
      <div>
        <button class="live-button" @click="onClickControlGroupcall" v-if="!state.conferenceDetail._active">라이브 켜기</button>
        <button class="live-button" @click="onClickControlGroupcall" v-else>라이브 끄기</button>
      </div>
    </div>
    <div class="detail-body">
      <!-- 클래스 생성자만 조작 가능 -->
      <div class="detail-description">
        입문자를 위한
        <br>
        보컬 트레이닝 클래스입니다.
      </div>
      <div class="detail-description-live" v-if="state.conferenceDetail._active">
        현재 라이브 중이에요! 😁
        <br>
        <button class="live-button" @click="onClickLive" v-if="isEntered()">
          라이브 참여하기
        </button>
        <div v-else>
          <span style="font-size: 14px;">라이브에 참여하고 싶으시다면 먼저 👉</span>
          <button class="live-button" @click="onClickClassRegister">
            클래스 수강신청
          </button>
        </div>
      </div>
      <div class="detail-description-live" v-else>
        현재 라이브 중이 아니에요 😥
        <br>
        <div v-if="!isEntered()">
          <span style="font-size: 14px;">라이브에 참여하고 싶으시다면 먼저 👉</span>
          <button class="live-button" @click="onClickClassRegister" >
            클래스 수강신청
          </button>
        </div>
      </div>
      <!-- 스터디 개설자일 경우에는 그룹콜 켤수 있도록 한다 -->
    </div>
    <div class="detail-bottom">
      이전 라이브 영상
      <ul class="detail-list">
        <li class="detail-bottom-button" v-for="(idx) in 8" :key="idx">
          소주한잔 {{ idx }}키 올려 부르는 법
        </li>
      </ul>
    </div>
  </div>
</template>
<style>

</style>

<script>
import { reactive, onMounted, onUnmounted, computed, onBeforeMount } from 'vue'
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
      userName: computed(() => store.getters['root/getUserName']),
      userId: computed(() => store.getters['root/getUserId']),
      images: {
        detail_banner: require('@/assets/images/music.jpg'),
      },
    })

    onBeforeMount(() => {
      state.conferenceId = route.params.conferenceId
      store.dispatch('root/requestConferenceDetail', state.conferenceId)
    })
    // 페이지 진입시 불리는 훅
    onMounted(() => {

    })

    // 페이지 이탈시 불리는 훅
    onUnmounted(() => {
      state.conferenceId = ''
    })

    const onClickLive = function () {
      window.location = `/groupcall/${state.conferenceId}`
    }

    const isEntered = function () {
      for (let user of state.conferenceDetail.users) {
        if (JSON.stringify(user) === JSON.stringify({userId: state.userId, userName: state.userName})) {
          return true
        }
      }
      return false
    }

    const onClickClassRegister = function () {
      store.dispatch('root/requestConferenceJoin', state.conferenceId)
      .then(() => {
        alert('클래스 수강신청에 성공하셨습니다!')
        window.location.reload()
      })
      .catch((err) => console.log(err))
    }

    const onClickControlGroupcall = function () {
      store.dispatch('root/requestConferenceOnBoarding', state.conferenceId)
      .then((
        window.location.reload()
      ))
      .catch((err) => console.log(err))
    }

    return { state, onClickLive, isEntered, onClickClassRegister, onClickControlGroupcall }
  }
}
</script>
