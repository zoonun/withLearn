<template>
  <div class="home">
    <div class="home-banner">
      <div class="home-banner-text">
        <div data-aos="fade-right" data-aos-duration="1400">
          <h1>보고 듣기만 하는 인터넷 강의는 이제 그만!</h1>
          <br>
          <p>실시간 화상통화로 소통하는 ON택트 미팅 플랫폼 위드런에</p>
          <br>
          <p>지금 참여해 보세요.</p>
        </div>
        <img id="home-banner-image-1" :src="state.images.home_1" alt="banner_image">
      </div>
    </div>
    <div class="home-content">
      <img id="home-content-image-1" :src="state.images.home_2" alt="content_image">
      <div class="home-content-text">
        <h1>With</h1>
        <br>
        <p>보기만 하는 수동적 강의는 많습니다.</p>
        <p>위드런은 듣고 말하며 능동적으로 참여하는 웹 플랫폼입니다.</p>
        <br>
        <h1>Learn</h1>
        <br>
        <p>실제로 만나 스터디하는 듯한 즐거운 시간을 보내며,</p>
        <p>강사와 참여자 모두에게 배웁니다.</p>
      </div>
    </div>
    <div class="home-content" data-aos="zoom-in-up" data-aos-duration="1300">
      <img id="home-content-image-1" :src="state.images.home_2" alt="content_image">
      <div class="home-content-text">
        <h1>With</h1>
        <br>
        <p>보기만 하는 수동적 강의는 많습니다.</p>
        <p>위드런은 듣고 말하며 능동적으로 참여하는 웹 플랫폼입니다.</p>
        <br>
        <h1>Learn</h1>
        <br>
        <p>실제로 만나 스터디하는 듯한 즐거운 시간을 보내며,</p>
        <p>강사와 참여자 모두에게 배웁니다.</p>
      </div>
    </div>
    <div class="home-content" data-aos="zoom-in-down" data-aos-duration="1300">
      <img id="home-content-image-1" :src="state.images.home_2" alt="content_image">
      <div class="home-content-text">
        <h1>With</h1>
        <br>
        <p>보기만 하는 수동적 강의는 많습니다.</p>
        <p>위드런은 듣고 말하며 능동적으로 참여하는 웹 플랫폼입니다.</p>
        <br>
        <h1>Learn</h1>
        <br>
        <p>실제로 만나 스터디하는 듯한 즐거운 시간을 보내며,</p>
        <p>강사와 참여자 모두에게 배웁니다.</p>
      </div>
    </div>
  </div>
</template>

<style>

</style>
<script>
import { reactive, computed } from 'vue'
import { useRouter } from 'vue-router'
import { useStore } from 'vuex'

export default {
  name: 'Home',
  setup () {
    const store = useStore()
    const router = useRouter()

    const state = reactive({
      images: {
        home_1: require('@/assets/images/home_1.svg'),
        home_2: require('@/assets/images/home_2.svg'),
      },
      recentSearchValue: computed(() => store.getters['root/getSearchValue']),
      count: 12,
      sortCurrentText:'제목순',
      sortSelectLabelItems: ['제목순', '추천순'],
      sortActiveOrderIndex: computed(() => store.getters['root/getSortIndex']),
      sortOrderIconItems: ['el-icon-sort-up', 'el-icon-sort-down'],
      sortOrderValueItems: ['asc', 'desc'],
      sortOrderIconItem: computed(() => {
        return state.sortOrderIconItems[state.sortActiveOrderIndex]
      }),
      sortActiveSelectIndex: 0,
      sortSelectValueItems: ['title', 'recommend'],
      searchState: computed(() => store.getters['root/getSearchStatus'])
    })

    const load = function () {
      state.count += 4
    }

    const clickConference = async function (id) {

      await router.push({
        name: 'conference-detail',
        params: {
          conferenceId: id
        }
      })
    }
    const clickSortOrderIndex = () => {
      console.log(state.activeSortIndex)
      store.commit('root/setSortIndex')
      const payload = {
        title: state.recentSearchValue,
        sort: [state.sortSelectValueItems[state.sortActiveSelectIndex], state.sortOrderValueItems[state.sortActiveOrderIndex]],
        page: null,
        size: 20,
        conference_category: state.conference_category,
      }
      console.log(payload)
      store.dispatch('root/requestSearchTitle', payload)
  }

    const clickSortSelectItem = (index) => {
      state.sortCurrentText = state.sortSelectLabelItems[index]
      state.sortActiveSelectIndex = index
      const payload = {
        title: state.recentSearchValue,
        sort: [state.sortSelectValueItems[state.sortActiveSelectIndex], state.sortOrderValueItems[state.sortActiveOrderIndex]],
        page: null,
        size: 20,
        conference_category: state.conference_category,
      }
      console.log(payload)
      store.dispatch('root/requestSearchTitle', payload)
    }

    const clickGuideButton = async function () {

      await router.push({
        name: 'search',
        params: {
          searchValue: null,
        }
      })
    }

    return { state, load, clickConference, clickSortOrderIndex, clickSortSelectItem, clickGuideButton }
  }
}
</script>
