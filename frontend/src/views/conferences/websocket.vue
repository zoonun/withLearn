<template>
  <div id="app">
    <button @click="disconnect" v-if="state.status === 'connected'">연결끊기</button>
    <button @click="connect" v-if="state.status === 'disconnected'">연결</button>
      {{ state.status }}
    <div v-if="state.status === 'connected'">
      <form action="#" @submit.prevent="sendMessage">
        <input type="text" v-model="state.message">
        <button type="submit">메시지 전송</button>
      </form>
      <ul id="logs">
        <li
          v-for="(item, index) in state.logs"
          :key=index
          class="log">
          {{ item.event }} : {{ item.data }}
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import { reactive } from 'vue'
export default {
  name: 'websocket',
  setup() {
    const state = reactive({
      logs: [],
      status: 'disconnected',
      message: '',
      ws: null
    })

    const connect = function () {
      // 나중에 API로 제공해야 함
      // JWT 인증 토큰을 AccessToken에 전달해야 함
      state.ws = new WebSocket('wss://echo.websocket.org')
      state.ws.onopen = () => {
        state.status = 'connected'
        state.logs.push({ event: '연결 완료', data: 'wss://echo.websocket.org'})
        state.ws.onmessage = ({data}) => {
          state.logs.push({ event: '메시지 수신', data})
        }
      }
    }

    const disconnect = function () {
      state.ws.close()
      state.status = 'disconnected'
      state.logs = []
    }

    const sendMessage = function (e) {
      state.ws.send(state.message)
      state.logs.push({ event: '메시지 전송', data: state.message })
      console.log(JSON.stringify(state.logs))
      state.message = ''
    }
    return { state, connect, disconnect, sendMessage }
  }
}
</script>

<style>

</style>
