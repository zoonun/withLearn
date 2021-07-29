<template>
  <div id="container">
    <div id="wrapper">
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
import kurentoUtils from 'kurento-utils'
export default {
  name: 'groupcall',

  setup() {
    const ws = new WebSocket('wss://' + 'localhost:8443' + '/groupcall')
    const participants = {}
    const name = 'joonon'
    const state = reactive({

    })

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
          participants[parsedMessage.name].rtcPeer.addIceCandidate(parsedMessage.candidate, function (error) {
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
      const name = 'Joo.non'
      const room = 'WebRTC TestRoom'

      document.getElementById('room-header').innerText = 'ROOM ' + room
      document.getElementById('join').style.display = 'none'
      document.getElementById('room').style.display = 'block'

      const message = {
        id: 'joinRoom',
        name: name,
        room: room
      }
      sendMessage(message)
    }

    const onNewParticipant = function (request) {
      receiveVideo(request.name)
    }

    const receiveVideoResponse = function (result) {
      participants[result.name].rtcPeer.processAnswer (result.sdpAnswer, function (error) {
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
      console.log(name + ' registered in room ' + 'sample')
      const participant = new Participant(name)
      participants[name] = participant
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

      for (let key in participants) {
        participants[key].dispose()
      }

      document.getElementById('join').style.display = 'block';
      document.getElementById('room').style.display = 'none';

      ws.close();
    }

    const receiveVideo = function (sender) {
      const participant = new Participant(sender);
      participants[sender] = participant;
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
      const participant = participants[request.name];
      participant.dispose();
      delete participants[request.name];
    }

    const sendMessage = function (message) {
      var jsonMessage = JSON.stringify(message);
      console.log('Sending message: ' + jsonMessage);
      ws.send(jsonMessage);
    }

    // participant.js
    const PARTICIPANT_MAIN_CLASS = 'participant main';
    const PARTICIPANT_CLASS = 'participant';

    const Participant = function (name) {
      this.name = name;
      const container = document.createElement('div');
      container.className = isPresentMainParticipant() ? PARTICIPANT_CLASS : PARTICIPANT_MAIN_CLASS;
      container.id = name;
      const span = document.createElement('span');
      const video = document.createElement('video');
      // let rtcPeer

      container.appendChild(video);
      container.appendChild(span);
      container.onclick = switchContainerClass;
      document.getElementById('participants').appendChild(container);

      span.appendChild(document.createTextNode(name));

      video.id = 'video-' + name;
      video.autoplay = true;
      video.controls = false;


      this.getElement = function() {
        return container;
      }

      this.getVideoElement = function() {
        return video;
      }

      function switchContainerClass() {
        if (container.className === PARTICIPANT_CLASS) {
          const elements = Array.prototype.slice.call(document.getElementsByClassName(PARTICIPANT_MAIN_CLASS));
          elements.forEach(function(item) {
              item.className = PARTICIPANT_CLASS;
            });
            container.className = PARTICIPANT_MAIN_CLASS;
          } else {
          container.className = PARTICIPANT_CLASS;
        }
      }

      function isPresentMainParticipant() {
        return ((document.getElementsByClassName(PARTICIPANT_MAIN_CLASS)).length != 0);
      }

      this.offerToReceiveVideo = function(error, offerSdp, wp){
        if (error) return console.error ('sdp offer error')
        console.log('Invoking SDP offer callback function');
        const msg =  { id : 'receiveVideoFrom',
            sender : name,
            sdpOffer : offerSdp
          };
        sendMessage(msg);
      }


      this.onIceCandidate = function (candidate, wp) {
          console.log('Local candidate' + JSON.stringify(candidate));

          const message = {
            id: 'onIceCandidate',
            candidate: candidate,
            name: name
          };
          sendMessage(message);
      }

      Object.defineProperty(this, 'rtcPeer', { writable: true});

      this.dispose = function() {
        console.log('Disposing participant ' + this.name);
        this.rtcPeer.dispose();
        container.parentNode.removeChild(container);
      };
    }

    return { ws, participants, name, state, unloadEvent,
    // conferenceroom.js
    register, onNewParticipant, receiveVideoResponse, callResponse, onExistingParticipants, leaveRoom, receiveVideo, onParticipantLeft, sendMessage,
    // participant.js
    Participant
    }
  }
}
</script>

<style>

</style>
