<template>
  <div>

  </div>
</template>

<script>
import { reactive, computed, onMounted } from 'vue'
import { useStore } from 'vuex'

export default {
  name: 'search-dialog',

  props: {
    open: {
      type: Boolean,
      default: false
    }
  },

  setup(props, { emit }) {
    const store = useStore()

    const state = reactive({
      searchValue:'',
      dialogVisible: computed(() => props.open),
      formLabelWidth: '120px',
    })

    onMounted(() => {

    })
    const handleClose = function () {
      state.searchValue = ''
      emit('closeSearchDialog')
    }

    const clickSearch = () => {
      console.log('clickSearch')
      const payload = {
        title: state.searchValue,
        sort: state.sort,
        page: null,
        size: 10,
        conference_category: state.conference_category,
      }
      console.log(payload)
      store.dispatch('root/requestSearchTitle', payload)
    }

    return { state, handleClose, clickSearch}
  }
}
</script>

<style>

</style>
