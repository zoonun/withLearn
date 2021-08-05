<template>
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
  <!-- <ul class="infinite-list" v-infinite-scroll="load" style="overflow:auto">
    <li v-for="i in state.count" @click="clickConference(i)" class="infinite-list-item" :key="i" >
      <Conference/>
    </li>
  </ul> -->
  <el-carousel
    trigger="click"
    :autoplay="false"
    :loop="false"
  >
    <el-carousel-item v-for="i in (state.count/3)" :key="i">
      <ul class="infinite-list">
        <li v-for="j in 3" @click="clickConference(j + (i - 1) * 3)" class="infinite-list-item" :key="j" >
          <Conference/>
        </li>
      </ul>
    </el-carousel-item>
  </el-carousel>
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
.el-carousel {
  height: 100%;
}

.is-animating {
  height:350px;
}


</style>
<script>
import Conference from './components/conference'
import { reactive, computed } from 'vue'
import { useRouter } from 'vue-router'
import { useStore } from 'vuex'

export default {
  name: 'Home',

  components: {
    Conference,
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
      sortSelectValueItems: ['title', 'recommend']
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

    return { state, load, clickConference, clickSortOrderIndex, clickSortSelectItem }
  }
}
</script>
