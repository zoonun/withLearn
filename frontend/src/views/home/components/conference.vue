<template>
  <!-- card -->
  <div>

  </div>
</template>
<style>

</style>
<script>
export default {
  name: 'Home',

  props: {
    title: {
      type: String,
      default: '제목'
    },
    name: {
      type: String,
      default: '백준'
    },
    isactive: {
      type: Boolean,
      default:true
    },
    category: {
      type: String,
      default: '알고리즘'
    }
  },

  setup () {

  }
}
</script>
