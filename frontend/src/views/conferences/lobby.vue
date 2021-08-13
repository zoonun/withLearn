<template>
  <div>
    <input type="text" v-model="state.form.roomId">
    <button @click="join">join</button>
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
