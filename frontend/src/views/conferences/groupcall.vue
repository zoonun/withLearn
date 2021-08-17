<template>
  <div class="small-banner">
    <p class="small-banner-text">
      화상채팅
    </p>
  </div>
  <div class="groupcall-wrap">
    <h2 id="room-header">{{ $route.params.roomId }}번 방</h2>
    <div id="room" class="container">
      <!-- 참가자 Video 추가되는 블럭 -->
      <div id="participants" class="row"></div>
    </div>
    <div>
      <button @click="leaveRoom">나가기</button>
      <button @click="checkState">참가자 체크</button>
      <button @click="stopMic">마이크 끄기</button>
      <button @click="stopVideo">비디오 끄기</button>
    </div>
  </div>
</template>

<script>
import { reactive, onBeforeUnmount, onMounted, computed } from 'vue'
import { Participant } from '@/api/participant'
import kurentoUtils from 'kurento-utils'
import { onBeforeRouteLeave, useRoute } from 'vue-router'
import { useStore } from 'vuex'

export default {
  name: 'groupcall',
  props: {
  },
  // TODO: 강의를 신청한 사용자가 아니라면 redirect해서 이전 페이지로 보내가
  setup() {
    const route = useRoute()
    const store = useStore()
    const state = reactive({
      name: computed(() => store.getters['root/getUserName']),
      userId: computed(() => store.getters['root/getUserId']),
      room: route.params.roomId,
      participants: {},
      ws: {}
    })
    state.ws = new WebSocket('wss://i5d106.p.ssafy.io:8080/groupcall')

    onBeforeUnmount(() => {
      // window.removeEventListener('beforeunload', state.ws.close())
      state.ws.close()
    })

    state.ws.onmessage = function (message) {
      var parsedMessage = JSON.parse(message.data)
      // console.info('Received msg:' + message.data)

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

    const onNewParticipant = function (request) {
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
            maxHeight: 240,
            maxFrameRate: 15,
            minFrameRate: 15
          }
        }
      }
      var participant = new Participant(state.name, sendMessage)
      state.participants[state.name] = participant
      var video = participant.getVideoElement()

      var options = {
        remoteVideo: video,
        mediaConstraints: constraints,
        onicecandidate: participant.onIceCandidate.bind(participant)
      }
      participant.rtcPeer = new kurentoUtils.WebRtcPeer.WebRtcPeerSendrecv(options, function (error) {
        if (error) return console.error(error)
        this.generateOffer(participant.offerToReceiveVideo.bind(participant))
      })
      message.data.forEach(receiveVideo)
    }

    const readyWsConnection = function () {
      setTimeout(function () {
        if (state.ws.readyState === 1) {
          console.log('접속 준비 완료!')
          enterRoom()
        } else {
          readyWsConnection()
        }
      }, 5)
    }

    const enterRoom = function () {
      const message = {
        id : 'joinRoom',
        name : state.name,
        room : state.room,
        image: 'images/1335496638255773.jpg'
      }
      sendMessage(message)
    }

    const leaveRoom = function () {
      alert('화상채팅 종료')
      sendMessage({
        id: 'leaveRoom'
      })
      for (let key in state.participants) {
        state.participants[key].dispose()
      }
      // state.ws.close();
      window.location = `/conferences/${state.room}`
    }

    const onParticipantLeft = function (request) {
      const participant = state.participants[request.name];
      participant.dispose();
      delete state.participants[request.name];
    }

    const sendMessage = function (message) {
      var jsonMessage = JSON.stringify(message);
      // console.log('Sending message: ' + jsonMessage);
      state.ws.send(jsonMessage);
    }

    // 페이지 진입 시 자동으로 화상채팅방에 참여하기
    onMounted(() => {
      readyWsConnection()
    })

    const checkState = function () {
      const participant = state.participants[state.name];
      console.log(participant.rtcPeer)
    }

    const stopMic = function () {
      const participant = state.participants[state.name];
      participant.rtcPeer.audioEnabled = participant.rtcPeer.audioEnabled ? false : true
    }

    const stopVideo = function () {
      const participant = state.participants[state.name];
      participant.rtcPeer.videoEnabled = participant.rtcPeer.videoEnabled ? false : true
    }

    return { state,
    // conferenceroom
    onNewParticipant, enterRoom, receiveVideoResponse, callResponse, onExistingParticipants, leaveRoom, receiveVideo, onParticipantLeft, sendMessage, readyWsConnection, checkState, stopMic, stopVideo }
  }
}
</script>

<style>

</style>
