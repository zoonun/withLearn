<template>
  <footer>
    <img class="footer-logo" :src="state.images.logo" alt="위드런 로고">
    <div class="footer-header">
      서로에게 배웁니다, 위드런
    </div>
    <br>
    <div class="footer-body">
      전화: 070-1234-5678
      <br>
      이메일: test@gmail.com
      <br>
      카카오톡 ID: test
    </div>
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
    const state = reactive({
      images: {
        logo: require('@/assets/images/logo_white.png'),
      },
    })

    return { state }
  }
}
</script>
<style>
</style>
