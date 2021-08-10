<template>
  <div class="guide">
    더 많은 강의들을 확인해보세요!
    <span>
      <el-button class="guide-button" @click="clickGuideButton">
        <i class="el-icon-arrow-right"></i>
      </el-button>
    </span>
  </div>
</template>

<style>

</style>
<script>
import Conference from './components/conference'
import { reactive, computed } from 'vue'
import { useRouter } from 'vue-router'
import { useStore } from 'vuex'

export default {
  name: 'Home',
  setup () {
    const store = useStore()
    const router = useRouter()

    const state = reactive({
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
