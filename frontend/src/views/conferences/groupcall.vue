<template>
  <div class="small-banner">
    <p class="small-banner-text">
      화상채팅
    </p>
  </div>
  <div class="groupcall-wrap">
    <h2 id="room-header">{{ $route.params.roomId }}번 방</h2>
    <div id="room" class="container groupcall-body">
      <!-- 참가자 Video 추가되는 블럭 -->
      <div id="participants" class="row groupcall-content">
        <div class="groupcall-panels"></div>
      </div>
      <!-- 채팅! -->
      <div class="groupcall-chatting">

      </div>
    </div>
    <div class="groupcall-control-bar">
      <button @click="leaveRoom" class="groupcall-control-button groupcall-control-button-red">
        <span class="groupcall-control-button-info">나가기</span>
        <img :src="state.images.close" alt="나가기">
      </button>
      <button @click="controlMic" class="groupcall-control-button groupcall-control-button-green" v-if="state.control.mic">
        <p class="groupcall-control-button-info">마이크 끄기</p>
        <img :src="state.images.mic_on" alt="마이크 끄기">
      </button>
      <button @click="controlMic" class="groupcall-control-button groupcall-control-button-red" v-else>
        <p class="groupcall-control-button-info">마이크 켜기</p>
        <img :src="state.images.mic_off" alt="마이크 켜기">
      </button>
      <button @click="controlVideo" class="groupcall-control-button groupcall-control-button-green" v-if="state.control.video">
        <p class="groupcall-control-button-info">비디오 끄기</p>
        <img :src="state.images.videocam_on" alt="비디오 끄기">
      </button>
      <button @click="controlVideo" class="groupcall-control-button groupcall-control-button-red" v-else>
        <p class="groupcall-control-button-info">비디오 켜기</p>
        <img :src="state.images.videocam_off" alt="비디오 켜기">
      </button>
      <button @click="onOpenChromaDialog" class="groupcall-control-button groupcall-control-button-green">
        <p class="groupcall-control-button-info">크로마 설정</p>
        <img :src="state.images.videocam_chroma" alt="크로마 설정">
      </button>
      <button @click="controlShare" class="groupcall-control-button groupcall-control-button-green" v-if="!state.control.isSharing">
        <span class="groupcall-control-button-info">화면 공유하기</span>
        <img :src="state.images.screenshare_on" alt="화면공유">
      </button>
      <button @click="controlShare" class="groupcall-control-button groupcall-control-button-red" v-else>
        <span class="groupcall-control-button-info">화면공유 종료</span>
        <img :src="state.images.screenshare_off" alt="화면공유">
      </button>
    </div>
    <ChromaDialog
    v-if="state.userId"
    :open="state.chromaDialogOpen"
    :userId="state.userId"
    @changeChroma="onChangeChroma($event)"
    @closeChromaDialog="onCloseChromaDialog()"/>
  </div>
</template>

<script>
import { reactive, onBeforeUnmount, onMounted, computed } from 'vue'
import { Participant } from '@/api/participant'
import kurentoUtils from 'kurento-utils'
import { onBeforeRouteLeave, useRoute } from 'vue-router'
import { useStore } from 'vuex'
import ChromaDialog from '@/components/dialog/chroma-dialog'
import '@/api/screen.js'

export default {
  name: 'groupcall',
  props: {
  },
  components: {
    ChromaDialog
  },
  // TODO: 강의를 신청한 사용자가 아니라면 redirect해서 이전 페이지로 보내가
  setup() {
    const route = useRoute()
    const store = useStore()
    const state = reactive({
      images: {
        close: require('@/assets/images/groupcall/close.png'),
        mic_on: require('@/assets/images/groupcall/mic_on.png'),
        mic_off: require('@/assets/images/groupcall/mic_off.png'),
        videocam_on: require('@/assets/images/groupcall/videocam_on.png'),
        videocam_off: require('@/assets/images/groupcall/videocam_off.png'),
        screenshare_on: require('@/assets/images/groupcall/screenshare_on.png'),
        screenshare_off: require('@/assets/images/groupcall/screenshare_off.png'),
        videocam_chroma: require('@/assets/images/groupcall/videocam_chroma.png'),
        chroma: '',
      },
      name: computed(() => store.getters['root/getUserName']),
      userId: computed(() => store.getters['root/getUserId']),
      room: route.params.roomId,
      participants: {},
      ws: {},
      control: {
        mic: true,
        video: true,
        isSharing: false,
      },
      chromaDialogOpen: false,
    })
    state.ws = new WebSocket('wss://i5d106.p.ssafy.io:8080/groupcall')

    onBeforeUnmount(() => {
      // window.removeEventListener('beforeunload', state.ws.close())
      sendMessage({
        id: 'leaveRoom'
      })
      for (let key in state.participants) {
        state.participants[key].dispose()
      }
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
          width: 480,
          height: 360,
          frameRate: 15
        }
      }
      var participant = new Participant(state.name, sendMessage)
      state.participants[state.name] = participant
      participant.isSharing = state.control.isSharing
      var video = participant.getVideoElement()

      if (!participant.isSharing) {
        console.log('화면공유 안하는중!')
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
      } else {
        console.log('화면공유 하는중')
        if (navigator.getDisplayMedia || navigator.mediaDevices.getDisplayMedia) {
          if (navigator.mediaDevices.getDisplayMedia) {
            navigator.mediaDevices.getDisplayMedia({video: true, audio: true}).then(stream => {
              video.srcObject = stream;

              var options = {
                videoStream : stream,
                mediaConstraints: constraints,
                sendSource: 'screen',
                onicecandidate: participant.onIceCandidate.bind(participant)
              }
              participant.rtcPeer = new kurentoUtils.WebRtcPeer.WebRtcPeerSendrecv(options, function (error) {
                if (error) return console.error(error);
                this.generateOffer(participant.offerToReceiveVideo.bind(participant));
              });
              message.data.forEach(receiveVideo);
            });
          }
        }
      }
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
        image: ''
      }
      sendMessage(message)
    }

    const leaveRoom = function () {
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

    const controlMic = function () {
      const participant = state.participants[state.name];
      participant.rtcPeer.audioEnabled = participant.rtcPeer.audioEnabled ? false : true
      state.control.mic = state.control.mic ? false : true
    }

    const controlVideo = function () {
      const participant = state.participants[state.name];
      participant.rtcPeer.videoEnabled = participant.rtcPeer.videoEnabled ? false : true
      state.control.video = state.control.video ? false : true
    }

    const controlShare = function () {
      sendMessage({
        id: 'leaveRoom'
      })
      for (let key in state.participants) {
        state.participants[key].dispose()
      }
      state.control.isSharing = state.control.isSharing ? false : true

      const message = {
        id : 'joinRoom',
        name : state.name,
        room : state.room,
        image: state.images.chroma
      }
      sendMessage(message)
    }

    const onChangeChroma = (imagePath) => {
      sendMessage({
        id: 'leaveRoom'
      })
      for (let key in state.participants) {
        state.participants[key].dispose()
      }
      state.images.chroma = imagePath

      const message = {
        id : 'joinRoom',
        name : state.name,
        room : state.room,
        image: state.images.chroma
      }
      sendMessage(message)
    }

    const body = document.querySelector('body')

    const onOpenChromaDialog = () => {
      body.style.overflow = 'hidden'
      state.chromaDialogOpen = true
    }
    const onCloseChromaDialog = () => {
      body.style.overflow = 'auto'
      state.chromaDialogOpen = false
    }

    return { state,
    // conferenceroom
    onNewParticipant, enterRoom, receiveVideoResponse, callResponse, onExistingParticipants, leaveRoom, receiveVideo, onParticipantLeft, sendMessage, readyWsConnection, checkState, controlMic, controlVideo, onChangeChroma, onOpenChromaDialog, onCloseChromaDialog, controlShare }
  }
}
</script>

<style>

</style>
