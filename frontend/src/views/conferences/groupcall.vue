<template>
  <div id="container">
    <div id="room">
      <h2 id="room-header"></h2>
      <div id="participants"></div>
      <div>
        <input type="text" v-model="state.name">
        <button @click="enterRoom">입장하기</button>
      </div>
      <div>
        <button @click="leaveRoom">나가기</button>
      </div>
    </div>
  </div>
</template>

<script>
import { reactive, onMounted, onBeforeMount, onBeforeUnmount } from 'vue'
import { onBeforeRouteLeave } from 'vue-router'
import { Participant } from './js/participant'
import kurentoUtils from 'kurento-utils'

export default {
  name: 'groupcall',

  setup() {
    const state = reactive({
      name: '',
      room: '',
      participants: {},
      ws: {}
    })
    // onMounted(() => {
      //   window.addEventListener('beforeunload', unloadEvent)
    // })
    // onBeforeRouteLeave((to, from, next) => {
      //   const answer = window.confirm('저장되지 않은 작업이 있습니다! 정말 이동할까요?')
    //   if (answer) {
      //     console.log('이동')
    //     next()
    //   } else {
      //     next(false)
    //   }
    // })
    // // conferenceroom.js
    // const unloadEvent = function (event) {
      //   ws.close()
    //   // event.preventDefault()
    //   // event.returnValue = ''
    // }

    state.ws = new WebSocket('wss://i5d106.p.ssafy.io:8080/groupcall')

    onBeforeUnmount(() => {
      // window.removeEventListener('beforeunload', unloadEvent)
      state.ws.close()
    })

    state.ws.onmessage = function (message) {
      var parsedMessage = JSON.parse(message.data)
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
    const enterRoom = function () {
      let curUrl = document.location.href.split('/').reverse()
      console.log('현재 url', curUrl)
      state.room = curUrl[1]
      // name = curUrl[0]
      const message = {
        id : 'joinRoom',
        name : state.name,
        room : state.room,
      }
      sendMessage(message)
    }

    const onNewParticipant = function (request) {
      console.log(request, '참여')
      receiveVideo(request.name)
    }

    const receiveVideo = function (sender) {
      var participant = new Participant(sender, sendMessage);
      state.participants[sender] = participant;
      var video = participant.getVideoElement();

      var options = {
        remoteVideo: video,
        onicecandidate: participant.onIceCandidate.bind(participant)
      }

      participant.rtcPeer = new kurentoUtils.WebRtcPeer.WebRtcPeerRecvonly(options, function (error) {
        if (error) return console.error(error)
        this.generateOffer(participant.offerToReceiveVideo.bind(participant));
      })
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
      var constraints = {
        audio: true,
        video: {
          mandatory: {
            maxWidth: 320,
            maxFrameRate: 15,
            minFrameRate: 15
          }
        }
      }
      console.log(state.name + ' registered in room ' + state.room)
      var participant = new Participant(state.name, sendMessage)
      state.participants[state.name] = participant
      var video = participant.getVideoElement()

      var options = {
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
      alert('화상채팅 종료')
      sendMessage({
        id: 'leaveRoom'
      })

      for (let key in state.participants) {
        state.participants[key].dispose()
      }

      state.ws.close();
      window.location = '/lobby'
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
      state.ws.send(jsonMessage);
    }

    return { state,
    // conferenceroom
    onNewParticipant, enterRoom, receiveVideoResponse, callResponse, onExistingParticipants, leaveRoom, receiveVideo, onParticipantLeft, sendMessage }
  }
}
</script>

<style>

</style>
