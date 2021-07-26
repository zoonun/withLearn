<template>
  <el-dropdown>
    <el-button type="primary" @click="clickDropdown">
      {{ state.dropDowntext }}<i class="el-icon-arrow-down el-icon--right"></i>
    </el-button>
    <el-dropdown-menu slot="dropdown" v-if="state.dropDowncollapse">
      <el-dropdown-item>Action 1</el-dropdown-item>
      <el-dropdown-item>Action 2</el-dropdown-item>
      <el-dropdown-item>Action 3</el-dropdown-item>
      <el-dropdown-item>Action 4</el-dropdown-item>
      <el-dropdown-item>Action 5</el-dropdown-item>
    </el-dropdown-menu>
  </el-dropdown>
  <el-button @click="clickTitleSort">
    <i :class="['ic', state.sortItem]"/>
  </el-button>
  <ul class="infinite-list" v-infinite-scroll="load" style="overflow:auto">
    <li v-for="i in state.count" @click="clickConference(i)" class="infinite-list-item" :key="i" >
      <conference />
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
</style>
<script>
import Conference from './components/conference'
import { reactive, computed } from 'vue'
import { useRouter } from 'vue-router'
import { useStore } from 'vuex'

export default {
  name: 'Home',

  components: {
    Conference
  },

  setup () {
    const store = useStore()
    const router = useRouter()

    const state = reactive({
      count: 12,
      dropDowntext:'제목순',
      dropDowncollapse:false,
      activeSortIndex: computed(() => store.getters['root/getTitleSortIndex']),
      sortItems:['el-icon-sort-up', 'el-icon-sort-down'],
      sortItem: computed(() => {
        return state.sortItems[state.activeSortIndex]
      })
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
    const clickTitleSort = () => {
      console.log(state.activeSortIndex)
      store.commit('root/setTitleSortIndex')
  }

    const clickDropdown = () => {
      state.dropDowncollapse = !state.dropDowncollapse
    }

    return { state, load, clickConference, clickTitleSort, clickDropdown }
  }
}
</script>
