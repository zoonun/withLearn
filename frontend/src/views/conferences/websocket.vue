<template>
  <div id="app">
    <button @click="disconnect" v-if="state.status === 'connected'">연결끊기</button>
    <button @click="connect" v-if="state.status === 'disconnected'">연결</button>
      {{ state.status }}
    <div v-if="state.status === 'connected'">
      <form action="#" @submit.prevent="sendMessage">
        <input type="text" v-model="message">
        <button type="submit">메시지 전송</button>
      </form>
      <ul id="logs">
        <li v-for="log in logs" class="log">
          {{ log.event }} : {{ log.data }}
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
    })
    // websocket :
    const ws = new WebSocket('wss://echo.websocket.org')

    const connect = function () {
      // 나중에 API로 제공해야 함
      // JWT 인증 토큰을 AccessToken에 전달해야 함
      ws.onopen = () => {
        state.status = 'connected'
        state.logs.push({ event: '연결 완료', data: 'wss://echo.websocket.org'})
        ws.onmessage = ({data}) => {
          state.logs.push({ event: '메시지 수신', data})
        }
      }
    }

    const disconnect = function () {
      ws.close()
      state.status = 'disconnected'
      state.logs = []
    }

    const sendMessage = function (e) {
      console.log(e)
      ws.send(state.message)
      state.logs.push({ event: '메시지 전송', data: state.message })
      state.message = ''
      console.log('sendMessage: ' + e)
    }
    return { state, ws, connect, disconnect, sendMessage }
  }
}
</script>

<style>

</style>
