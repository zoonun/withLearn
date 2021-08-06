<template>
  <el-container>
    <el-form class="lobby-form" :model="state.form" label-width="100px" label-position="left">
        <h1 class="form-title">Meeting</h1>
        <el-form-item label="Room">
            <el-input v-model="state.form.roomId"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button @click="join">join</el-button>
        </el-form-item>
    </el-form>
  </el-container>
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
