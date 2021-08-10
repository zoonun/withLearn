<template>
  <div>
    lobby
  </div>
</template>

<script>
import { reactive } from 'vue'

export default {
  name: 'lobby',

  setup(){
    const state = reactive({
      form:{
        roomId: ''
      }
    })

    const join = function(){
      window.location = `/conferences/${state.form.roomId}`
    }
    return {state, join}
  }
}
</script>

<style>

</style>
