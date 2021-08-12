<template>
  <footer>
  </footer>
</template>
<style>
</style>
<script>
import { reactive } from 'vue'

export default {
  name: 'main-footer',

  props: {
    height: {
      type: String,
      default: '110px'
    }
  },

  setup() {
    const state = reactive({})

    return { state }
  }
}
</script>
<style>
</style>
