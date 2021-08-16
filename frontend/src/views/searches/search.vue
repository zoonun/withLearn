<template>
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
  <div class="card-body">
    <div v-for="(conference, idx) in state.conferences" :key="idx" @click="clickConference(idx)">
      <Conference
        :conference="conference"
      />
    </div>
  </div>

</template>
<style>

.filter-wrapper {
  margin-bottom:1%;
  padding-left: 4%;
}
.filter-list button {
  background-color: #b8b8b8;
  border-radius: 10px;
  color:white;
  font-weight: bold;
  margin-left: 1rem;
}
.filter-list {
  padding-inline-start: 0px;
  justify-content: flex-start;
}
@media (min-width: 1024px) {
  .card-body {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(25%, auto));
    grid-template-rows: repeat(5, minmax(50px,auto));
    grid-auto-flow: dense;
    padding-left:4%;
    padding-right:4%;
    }
}

@media (min-width: 768px) and (max-width: 1023px) {
  .card-body {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(50%, auto));
    grid-template-rows: repeat(5, minmax(50px,auto));
    grid-auto-flow: dense;
    padding-left:4%;
    padding-right:4%;
  }
}
@media (max-width: 767px) {
  .card-body{
    display: grid;
    padding-left:4%;
    padding-right:4%;

  }
}
.sort-wrapper {
  display: flex;
  justify-content: flex-end;
  padding-right: 5%;
}
.sort-button {
  font-size: 1.8rem;
  min-width: 90px;
  margin: 0 5px;
  padding: 5px;
  border-radius: 5px;
  background-color: var(--white-color);
  cursor: pointer;
  border-color: #dbdbdb;
}
.sort-order-button {
  font-size: 1.8rem;
  background-color: var(--white-color);
  border-color: #dbdbdb;
  cursor: pointer;
  border-radius: 5px;
}
.card-body {
  border-color: #dbdbdb;
}
.search-header {
  margin-bottom: 4rem;
}

</style>
<script>
import Conference from '../home/components/conference'
import { reactive, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useStore } from 'vuex'

export default {
  name: 'search',

  components: {
    Conference
  },

  setup () {
    const store = useStore()
    const router = useRouter()

    const state = reactive({
      conferences: computed(() =>store.getters['root/getConference']),
      recentSearchValue: computed(() => store.getters['root/getSearchValue']),

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
        title: null,
        sort: null,
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
      console.log(state.activeSortIndex)
      store.commit('root/setSortIndex')
      const payload = {
        title: state.recentSearchValue,
        sort:  [state.sortSelectValueItems[state.sortActiveSelectIndex],state.sortOrderValueItems[state.sortActiveOrderIndex]].join(','),
        order: state.sortOrderValueItems[state.sortActiveOrderIndex],
        page: null,
        size: 20,
        conference_category: state.conference_category,
      }
      console.log(payload)
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
        console.log(state.filterColorArray)
        filterItem.style.backgroundColor='#b8b8b8'
      } else {
        state.filterColorArray[index] = true
        console.log(state.filterColorArray)
        filterItem.style.backgroundColor='#1dc078'
      }
      var Newarr = [];
      state.filterColorArray.forEach((value, index, array) => {
        if (value) {
          Newarr.push(Number(index) + 1)
        }
      })
      state.conference_category = Newarr.join(',')
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
