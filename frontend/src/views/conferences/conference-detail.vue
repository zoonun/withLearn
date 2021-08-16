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
    <div class="detail-isactive">
      현재 라이브 진행 중!
      <button class="live-button">
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
.detail-header {
  background-color:#5a4ae3;
  height: 4rem;
  color:white;
  padding-left: 10rem;
  padding-right: 10rem;
  padding-top: 1.5rem;
  font-size: 2.25rem;
  line-height: 2.5rem;
  font-weight: bold;
}
.detail-thumbnail {
  /* object-fit:cover; */
  width:200px;
}
.detail-information {
  display: flex;
  justify-content: space-between;
  margin-right:30rem;
  margin-left:30rem;
  font-size: 2.25rem;
  margin-top:8rem;
}
.detail-isactive {
  display: flex;
  flex-direction: column;
  font-weight: bold;
}
.live-button {
  margin-top:1rem;
  font-size: 2.25rem;
  color: white;
  background-color: #5a4ae3;
  padding: 5px;
  border-radius: 5px;
  border:0;
  cursor: pointer;
}
.detail-bottom {
  margin-top:2rem;
  border-width:1rem;
  margin-right:20rem;
  margin-left:20rem;
  font-size: 2.25rem;
}
.detail-thumbnail-wrapper {
  /* background-size: cover; */
  display: flex;
  justify-content: center;
}
.detail-bottom-button {
  margin-top:1rem;
  font-size: 2.25rem;
  color: black;
  background-color: #e5e7eb;
  padding: 5px;
  border-radius: 5px;
  border:0;
  cursor: pointer;
  display: flex;
  justify-content: center;
}
.detail-list {
  list-style:none;
  border: 2px solid #e5e7eb;
  padding:2rem;

}
.detail-description {
  width:60%;
  font-weight:bold;
}

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
      images: {
        image: require('@/assets/images/music.png'),
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

    return { state }
  }
}
</script>

