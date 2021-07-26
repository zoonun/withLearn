<template>
  <el-dropdown>
    <el-button type="primary" @click="clickDropdown">
      {{ state.currentText }}<i class="el-icon-arrow-down el-icon--right"></i>
    </el-button>
    <el-dropdown-menu slot="dropdown" v-if="state.dropDownCollapse">
      <el-dropdown-item v-for="(item, index) in state.dropDownArray" :key="index" :index="index.toString()" @click="clickDropdownItem(index)">
        {{ item }}
      </el-dropdown-item>
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
      currentText:'제목순',
      dropDownArray: ['제목순', '추천순'],
      dropDownCollapse:false,
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
      state.dropDownCollapse = !state.dropDownCollapse
    }

    const clickDropdownItem = (index) => {
      state.currentText = state.dropDownArray[index]
      state.dropDownCollapse=false
    }

    return { state, load, clickConference, clickTitleSort, clickDropdown, clickDropdownItem }
  }
}
</script>
