/* eslint-disable vue/no-unused-components */
<template>
  <div style="margin-top: 3%;">
    <!-- <div class="flex">
      <div class="item interview-best"></div>
      <div class="item interview-best"></div>
      <div class="item interview-best"></div>
      <div class="item interview-best"></div>
    </div> -->
    <div class="d-flex justify-content-center">
      <el-carousel
        :interval="2000"
        type="card"
        height="300px"
        style="width: 1100px"
      >
        <el-carousel-item
          v-for="item in 6"
          :key="item"
          style="width: 550px"
        >
          <h3 class="medium">{{ item }}</h3>
        </el-carousel-item>
      </el-carousel>
    </div>
    <div
      id="container"
      class="interview-image"
    >
      <div
        id="wrapper"
        class="interview-buttons d-flex justify-content-center"
      >
        <div class="d-flex justify-content-between buttons">
          <router-link :to="{ name: 'SelfInterview' }">
            <button
              size="lg"
              class="btn btn-info button"
            >면접 연습</button>
          </router-link>

          <router-link :to="{ name: 'InterviewList' }">
            <button class="btn btn-info button">내 면접 목록</button>
          </router-link>
        </div>
        <div
          v-if="isMain == true"
          id="join"
          class="animate join d-flex justify-content-center"
        >
          <h1>방 가입</h1>
          <input
            type="text"
            name="name"
            v-model="name"
            id="name"
            required
            style="border: 1px solid"
          />
          <input
            style="border: 1px solid; margin-left: 3px"
            type="text"
            name="room"
            v-model="room"
            id="roomName"
            required
            @keypress.enter="register"
          />
          <button
            @click="register"
            class="btn btn-info"
            style="margin-left: 5px"
          >
            시작하기
          </button>
        </div>
        <div
          id="room"
          v-if="isInRoom == true"
        >
          <h2 id="room-header"></h2>
          <div id="participants"></div>
          <button
            id="button-leave"
            @click="leaveRoom"
          >나가즈아~</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import kurentoUtils from "kurento-utils";
import adapter from "webrtc-adapter"; // eslint-disable-line no-unused-vars
import { Participant } from "./participant.js";

export default {
  name: "Interview",
  data() {
    return {
      ws: {},
      participants: {},
      name: null,
      room: null,
      isMain: true,
      isInRoom: false,
    };
  },

  created() {
    this.ws = new WebSocket("wss://i5d105.p.ssafy.io:8443/groupcall");

    this.ws.onmessage = (message) => {
      var parsedMessage = JSON.parse(message.data);
      console.info("Received message: " + message.data);

      switch (parsedMessage.id) {
        case "existingParticipants":
          this.onExistingParticipants(parsedMessage);
          break;
        case "newParticipantArrived":
          this.onNewParticipant(parsedMessage);
          break;
        case "participantLeft":
          this.onParticipantLeft(parsedMessage);
          break;
        case "receiveVideoAnswer":
          this.receiveVideoResponse(parsedMessage);
          break;
        case "iceCandidate":
          this.participants[parsedMessage.name].rtcPeer.addIceCandidate(
            parsedMessage.candidate,
            function (error) {
              if (error) {
                console.error("Error adding candidate: " + error);
                return;
              }
            }
          );
          break;
        default:
          console.error("Unrecognized message", parsedMessage);
      }
    };
  },

  unmounted() {
    this.ws.close();
  },

  methods: {
    register() {
      // 조인 눌렸으면 이제 참가자 등록해준다.
      this.isMain = false; //방가입창 false
      this.isInRoom = true;
      var message = {
        // 메시지 하나 만들어서
        id: "joinRoom",
        name: this.name,
        room: this.room,
      };
      this.sendMessage(message);
    },

    onNewParticipant(request) {
      this.receiveVideo(request.name);
    },

    receiveVideoResponse(result) {
      this.participants[result.name].rtcPeer.processAnswer(
        result.sdpAnswer,
        function (error) {
          if (error) return console.error(error);
        }
      );
    },

    callResponse(message) {
      if (message.response != "accepted") {
        console.info("Call not accepted by peer. Closing call");
        stop();
      } else {
        kurentoUtils.webRtcPeer.processAnswer(
          message.sdpAnswer,
          function (error) {
            if (error) return console.error(error);
          }
        );
      }
    },
    onExistingParticipants(msg) {
      // 참가자가 있는 상태
      var constraints = {
        audio: true,
        video: {
          mandatory: {
            maxWidth: 320,
            maxFrameRate: 15,
            minFrameRate: 15,
          },
        },
      };

      console.log(this.name + " registered in room " + this.room);
      var participant = new Participant(this.name, this.sendMessage);
      this.participants[this.name] = participant;
      var video = participant.getVideoElement();

      var options = {
        localVideo: video,
        mediaConstraints: constraints,
        onicecandidate: participant.onIceCandidate.bind(participant),
      };
      participant.rtcPeer = new kurentoUtils.WebRtcPeer.WebRtcPeerSendonly(
        options,
        function (error) {
          if (error) {
            return console.error(error);
          }
          this.generateOffer(participant.offerToReceiveVideo.bind(participant));
        }
      );

      msg.data.forEach(this.receiveVideo);
    },

    leaveRoom() {
      this.sendMessage({
        id: "leaveRoom",
      });

      for (var key in this.participants) {
        this.participants[key].dispose();
      }

      document.getElementById("join").style.display = "block";
      document.getElementById("room").style.display = "none";

      this.ws.close();
    },

    receiveVideo(sender) {
      var participant = new Participant(sender, this.sendMessage);
      this.participants[sender] = participant;
      var video = participant.getVideoElement();

      var options = {
        remoteVideo: video,
        onicecandidate: participant.onIceCandidate.bind(participant),
      };

      participant.rtcPeer = new kurentoUtils.WebRtcPeer.WebRtcPeerRecvonly(
        options,
        function (error) {
          if (error) {
            return console.error(error);
          }
          this.generateOffer(participant.offerToReceiveVideo.bind(participant));
        }
      );
    },

    onParticipantLeft(request) {
      console.log("Participant " + request.name + " left");
      var participant = this.participants[request.name];
      participant.dispose();
      delete this.participants[request.name];
    },

    sendMessage(message) {
      var jsonMessage = JSON.stringify(message);
      console.log("Sending message: " + jsonMessage);
      this.ws.send(jsonMessage);
    },
  },
};
</script>
