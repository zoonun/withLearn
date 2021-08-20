<template>
  <div class="search-wrap">
    <div class="search-header">
      <div class="sort-wrapper">
        <select class="sort-button" @change="clickSortSelectItem">
          <option v-for="(item, index) in state.sortSelectItems" :key="index" class="sort-button" :value="index"> {{ item.label }}</option>
        </select>
        <button @click="clickSortOrderIndex" class="sort-order-button">
          <i :class="['ic', state.sortOrderIconItem]"/>
        </button>
      </div>
      <div class="filter-wrapper">
        <ul class="filter-list">
          <button v-for="(item, index) in state.filterItems" :key="index" :index="index.toString()" @click="clickFilterItem(index)" class="btn">
            {{ item.name }}
          </button>
        </ul>
      </div>
    </div>
    <div class="conference-card-list container">
      <div class="row g-5">
        <div class="col-lg-3 col-md-4" v-for="(conference, idx) in state.conferences" :key="idx" @click="clickConference(conference.id)">
          <ConferenceCard
            :conference="conference"
          />
        </div>
      </div>
    </div>
  </div>
</template>
<style>

</style>
<script>
import ConferenceCard from '@/components/card/conference-card'
import { reactive, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useStore } from 'vuex'

export default {
  name: 'search',

  components: {
    ConferenceCard
  },

  setup () {
    const store = useStore()
    const router = useRouter()

    const state = reactive({
      conferences: computed(() =>store.getters['root/getConference']),
      recentSearchValue:computed(() => sessionStorage.getItem('recentSearch')),
      sortActiveOrderIndex: computed(() => store.getters['root/getSortIndex']),
      sortOrderIconItems: ['el-icon-sort-up', 'el-icon-sort-down'],
      sortOrderValueItems: ['asc', 'desc'],
      sortOrderIconItem: computed(() => {
        return state.sortOrderIconItems[state.sortActiveOrderIndex]
      }),
      sortActiveSelectIndex: 0,
      sortSelectValueItems: ['title', 'price'],
      filterItems: computed(() => store.getters['root/getConferenceId']),
      filterColorArray: Array(10),
      conference_category:null,
      sortSelectItems: [{label: '제목순', value: 'title'}, {label: '가격순', value: 'price'}]
    })


    onMounted(() => {
      const payload = {
        title: state.recentSearchValue,
        sort:  [state.sortSelectValueItems[state.sortActiveSelectIndex],state.sortOrderValueItems[state.sortActiveOrderIndex]].join(','),
        order: state.sortOrderValueItems[state.sortActiveOrderIndex],
        page: null,
        size: 20,
        conference_category: state.conference_category,
      }
      store.dispatch('root/requestSearchTitle', payload)
    })

    const load = function () {
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
      store.commit('root/setSortIndex')
      const payload = {
        title: state.recentSearchValue,
        sort:  [state.sortSelectValueItems[state.sortActiveSelectIndex],state.sortOrderValueItems[state.sortActiveOrderIndex]].join(','),
        order: state.sortOrderValueItems[state.sortActiveOrderIndex],
        page: null,
        size: 20,
        conference_category: state.conference_category,
      }
      store.dispatch('root/requestSearchTitle', payload)
  }

    const clickSortSelectItem = (event) => {
      const index = event.target.value
      state.sortActiveSelectIndex = index
      const payload = {
        title: state.recentSearchValue,
        sort: [state.sortSelectValueItems[state.sortActiveSelectIndex],state.sortOrderValueItems[state.sortActiveOrderIndex]].join(','),
        order:state.sortOrderValueItems[state.sortActiveOrderIndex],
        page: null,
        size: 20,
        conference_category: state.conference_category,
      }
      store.dispatch('root/requestSearchTitle', payload)
    }

    const clickFilterItem = (index) => {
      const filterList = document.querySelector('.filter-list')
      const filterItem = filterList.children[index]
      if (state.filterColorArray[index]) {
        state.filterColorArray[index] = false
        filterItem.style.backgroundColor='#b8b8b8'
      } else {
        state.filterColorArray[index] = true
        filterItem.style.backgroundColor='#1dc078'
      }
      var Newarr = [];
      state.filterColorArray.forEach((value, index, array) => {
        if (value) {
          Newarr.push(Number(index) + 1)
        }
      })
      if (Newarr.length ===0) {
        state.conference_category = null
      } else {
        state.conference_category = Newarr.join(',')
      }
      const payload = {
        title: state.recentSearchValue,
        sort: [state.sortSelectValueItems[state.sortActiveSelectIndex],state.sortOrderValueItems[state.sortActiveOrderIndex]].join(','),
        order:state.sortOrderValueItems[state.sortActiveOrderIndex],
        page: null,
        size: 20,
        conference_category: state.conference_category,
      }
      store.dispatch('root/requestSearchTitle', payload)
    }


    return { state, load, clickConference, clickSortOrderIndex, clickSortSelectItem, clickFilterItem }
  }
}
</script>
