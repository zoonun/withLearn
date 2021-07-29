<template>
  <div id="app">
    <div class="form-group" @submit.prevent>
      <button id="connect" @click="connect" type="submit">Connect</button>
      <button id="idsconnect" @click="disconnect" type="submit" disabled="disabled">Disconnect</button>
    </div>
  </div>
  <div class="form-inline">
    <div class="form-group">
      <label for="name">이름을 입력하세요</label>
      <input type="text" id="name" placeholder="name here">
    </div>
    <div class="form-group">
      <label for="message">메시지를 입력하세요</label>
      <input type="text" id="chatMessage" placeholder="message here">
    </div>
    <button id="chatSend" @click="sendChat">전송</button>
    <table id="conversation">
      <thead>
        <tr>
          <th>Messages</th>
        </tr>
        <tbody id="greetings">
        </tbody>
      </thead>
    </table>
  </div>
</template>

<script>
import { reactive } from 'vue'
export default {
  name: 'websocket',
  setup() {
    const stompClient = null

    const state = reactive({
      logs: [],
      connected: false,
      message: '',
    })

    const setConnected = function (connected) {
      $("#connect").prop('disabled', connected)
      $("#disconnect").prop('disabled', !connected)
      if (connected) {
        $("#conversation").show()
      } else {
        $("#conversation").hide()
      }
      $("#greetings").html("")
    }

    const connect = function () {
      const socket = new SockJS('/')
      stompClient = Stomp.over(socket)
      stompClient.connect({}, function (frame) {
        state.connected = true
        console.log('Connected: ' + frame)
        // Stomp path: https://localhost:8443/chat/join/{conferenceId}
        stompClient.subscribe('/chat/join/1', function (greeting) {
          showGreeting(JSON.parse(greeting.message).content)
        })
        stompClient.subscribe('/chat/message/1', function (chat) {
          showChat(JSON.parse(chat.message))
        })
      })
    }

    const disconnect = function () {
      if (stompClient !== null) {
        stompClient.disconnect()
      }
      state.connected = false
      console.log('Disconnected')
    }

    const showGreeting = function (message) {
      const greetings = document.getElementById('greetings')
      greetings.append('<tr><td>' + message + '</td></tr>')
    }

    const sendChat = function () {
      stompClient.send("/chat/message/1", {}, JSON.stringfy({'name': $('#name').val(), 'message': $('#chatMessage').val()}))
    }

    const showChat = function (chat) {
      const greetings = document.getElementById('greetings')
      greetings.append("<tr><td>" + chat.name + " : " + chat.message + "</td></tr>")
    }

    return { state, setConnected, stompClient, connect, disconnect, sendChat, showGreeting, showChat }
  }
}
</script>

<style>

</style>
