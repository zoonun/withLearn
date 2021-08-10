<template>
  <div class="filter-wrapper">
    <ul class="filter-list">
      <el-button v-for="(item, index) in state.filterItems" :key="index" :index="index.toString()" @click="clickFilterItem(index)">
        {{ item.name }}
      </el-button>
    </ul>
    <div class="sort-wrapper">
      <el-select
        v-model="state.sortCurrentText">
        <el-option
          v-for="(item, index) in state.sortSelectLabelItems"
          :key="index"
          :index="index.toString()"
          :label="item"
          @click="clickSortSelectItem(index)">
        </el-option>
      </el-select>
      <el-button @click="clickSortOrderIndex">
        <i :class="['ic', state.sortOrderIconItem]"/>
      </el-button>
    </div>
  </div>
  <ul class="infinite-list" v-infinite-scroll="load" style="overflow:auto">
    <li v-for="i in state.count" @click="clickConference(i)" class="infinite-list-item" :key="i" >
      <Conference/>
    </li>
  </ul>
</template>
<style>
.infinite-list {
  padding-left: 0;
  max-height: calc(100% - 35px);
}

@media (min-width: 701px) and (max-width: 1269px) {
  .infinite-list {
    min-width: 700px;
  }
}

@media (min-width: 1270px) {
  .infinite-list {
    min-width: 1021px;
  }
}

.infinite-list .infinite-list-item {
  min-width: 335px;
  max-width: 25%;
  display: inline-block;
  cursor: pointer;
}
.filter-wrapper {
  display: flex;
  justify-content: space-between;
  padding-right:11%;
  padding-left:10%;
}
.filter-list .el-button {
  display: block;
  background-color: #b8b8b8;
  border-radius: 10px;
  color:white;
  font-weight: bold;
}
.filter-list {
  display: flex;
  flex-flow: row wrap;
  padding-inline-start: 0px;
  margin:0px;
}
.el-input__inner {
  font-weight: bold;
}
.el-select-dropdown__item {
  font-weight: bold;
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
      filterItems: computed(() => store.getters['root/getConferenceId']),
      filterColorArray: Array(10)
    })

    onMounted(() => {
      store.dispatch('root/requestConferenceId')
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
    }

    return { state, load, clickConference, clickSortOrderIndex, clickSortSelectItem, clickFilterItem }
  }
}
</script>
