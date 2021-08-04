<template>
  <div id="container">
    <div id="wrapper">
      <!-- join 부분을 컨퍼런스 목록쪽으로 빼면 된다 -->
      <div id="join" class="animate join">
        <h1>Join a Room</h1>
        <form @onsubmit="register" accept-charset="UTF-8">
          <p>
            <input type="text" name="name" value="" id="name"
              placeholder="Username" required>
          </p>
          <p>
            <input type="text" name="room" value="" id="roomName"
              placeholder="Room" required>
          </p>
          <p class="submit">
            <input type="submit" name="commit" value="Join!">
          </p>
        </form>
      </div>
      <div id="room" style="display: none;">
        <h2 id="room-header"></h2>
        <div id="participants"></div>
        <input type="button" id="button-leave" @onmouseup="leaveRoom"
          value="Leave room">
      </div>
    </div>
  </div>
</template>

<script>
import { reactive, onMounted, onBeforeUnmount } from 'vue'
import { onBeforeRouteLeave } from 'vue-router'
import Participant from '@/api/participant'
import kurentoUtils from 'kurento-utils'

export default {
  name: 'groupcall',

  setup() {
    const state = reactive({
      name: 'joonon',
      room: 'baekjoon',
      participants: {}
    })
    const ws = new WebSocket('wss://i5d106.p.ssafy.io/groupcall')

    onMounted(() => {
      window.addEventListener('beforeunload', unloadEvent)
    })

    onBeforeUnmount(() => {
      window.removeEventListener('beforeunload', unloadEvent)
    })

    onBeforeRouteLeave((to, from, next) => {
      const answer = window.confirm('저장되지 않은 작업이 있습니다! 정말 이동할까요?')
      if (answer) {
        console.log('이동')
        next()
      } else {
        next(false)
      }
    })

    // conferenceroom.js
    const unloadEvent = function (event) {
      ws.close()
      // event.preventDefault()
      // event.returnValue = ''
    }

    ws.onmessage = function (message) {
      const parsedMessage = JSON.parse(message.data)
      console.info('Received msg:' + message.data)

      switch (parsedMessage.id) {
        case 'existingParticipants':
          onExistingParticipants(parsedMessage)
          break
        case 'newParticipantArrived':
          onNewParticipant(parsedMessage)
          break
        case 'participantLeft':
          onParticipantLeft(parsedMessage)
          break
        case 'receiveVideoAnswer':
          receiveVideoResponse(parsedMessage)
          break
        case 'iceCandidate':
          state.participants[parsedMessage.name].rtcPeer.addIceCandidate(parsedMessage.candidate, function (error) {
            if (error) {
              console.error('참가자 추가 중 에러: ' + error)
              return
            }
          })
          break
        default:
          console.error('미등록 메시지: ', parsedMessage)
      }
    }

    const register = function () {
      document.getElementById('room-header').innerText = 'ROOM ' + state.room
      document.getElementById('join').style.display = 'none'
      document.getElementById('room').style.display = 'block'

      const message = {
        id: 'joinRoom',
        name: state.name,
        room: state.room
      }
      sendMessage(message)
    }

    const onNewParticipant = function (request) {
      receiveVideo(request.name)
    }

    const receiveVideoResponse = function (result) {
      state.participants[result.name].rtcPeer.processAnswer (result.sdpAnswer, function (error) {
        if (error) return console.error(error)
      })
    }

    const callResponse = function (message) {
      if (message.response != 'accepted') {
        console.info('Call not accepted by peer. Closing call');
        stop()
      } else {
        const webRtcPeer = kurentoUtils.webRtcPeer
        webRtcPeer.processAnswer(message.sdpAnswer, function (error) {
          if (error) return console.error (error);
        })
      }
    }

    const onExistingParticipants = function (message) {
      const constraints = {
        audio: true,
        video: {
          mandatory: {
            maxWidth: 320,
            maxFrameRate: 15,
            minFrameRate: 15
          }
        }
      }
      console.log(state.name + ' registered in room ' + 'sample')
      const participant = new Participant(state.name)
      state.participants[state.name] = participant
      const video = participant.getVideoElement()

      const options = {
        localVideo: video,
        mediaConstraints: constraints,
        onicecandidate: participant.onIceCandidate.bind(participant)
      }
      participant.rtcPeer = new kurentoUtils.WebRtcPeer.WebRtcPeerSendonly(options, function (error) {
        if (error) return console.error(error)
        this.generateOffer(participant.offerToReceiveVideo.bind(participant))
      })
      message.data.forEach(receiveVideo)
    }

    const leaveRoom = function () {
      sendMessage({
        id: 'leaveRoom'
      })

      for (let key in state.participants) {
        state.participants[key].dispose()
      }

      document.getElementById('join').style.display = 'block';
      document.getElementById('room').style.display = 'none';

      ws.close();
    }

    const receiveVideo = function (sender) {
      const participant = new Participant(sender);
      state.participants[sender] = participant;
      const video = participant.getVideoElement();

      const options = {
        remoteVideo: video,
        onicecandidate: participant.onIceCandidate.bind(participant)
      }

      participant.rtcPeer = new kurentoUtils.WebRtcPeer.WebRtcPeerRecvonly(options, function (error) {
        if (error) return console.error(error)
        this.generateOffer (participant.offerToReceiveVideo.bind(participant));
      })
    }

    const onParticipantLeft = function (request) {
      console.log('Participant ' + request.name + ' left');
      const participant = state.participants[request.name];
      participant.dispose();
      delete state.participants[request.name];
    }

    const sendMessage = function (message) {
      var jsonMessage = JSON.stringify(message);
      console.log('Sending message: ' + jsonMessage);
      ws.send(jsonMessage);
    }

    return { state, unloadEvent,
    // conferenceroom
    register, onNewParticipant, receiveVideoResponse, callResponse, onExistingParticipants, leaveRoom, receiveVideo, onParticipantLeft, sendMessage }
  }
}
</script>

<style>

</style>
