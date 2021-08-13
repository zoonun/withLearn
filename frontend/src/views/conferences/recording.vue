<template>
  <div>
    <!-- video option 정하는 부분 -->
    <div class="row">
			<div class="col-md-12">
				<input type="radio" v-model="mode" name="mode" value="video-and-audio" checked="checked"> Video and audio
        <input type="radio" v-model="mode" name="mode" value="video-only"> Video only
        <input type="radio" v-model="mode" name="mode" value="audio-only"> Audio only
			</div>
		</div>

    <div class="row">
      <!-- 로컬 스트림 부분 -->
      <div class="col-md-5">
				<h3>Local stream</h3>
        <video id="videoInput" poster="@/assets/images/poster.png"></video>
			</div>
      <!-- 버튼 부분 -->
      <div class="col-md-2">
        <!-- start button -->
        <a id="start" href="#" class="btn btn-success" @click="start">
        <span class="glyphicon glyphicon-play"></span> Start</a><br> <br>

        <a id="stop" href="#" class="btn btn-danger" @click="stop" >
        <span class="glyphicon glyphicon-stop"></span> Stop</a><br> <br>

        <a id="play" href="#" class="btn btn-warning" @click="play">
        <span class="glyphicon glyphicon-play-circle"></span> Play</a>
      </div>
      <!-- 녹화본 뿌려주는 부분 -->
      <div class="col-md-5">
        <h3>녹화본</h3>
        <video id="videoOutput" poster="@/assets/images/poster.png"></video>
      </div>
    </div>
  </div>
</template>

<script>
import kurentoUtils from "kurento-utils";
import adapter from "webrtc-adapter"; // eslint-disable-line no-unused-vars

var videoInput;
var videoOutput;
var ws = new WebSocket('wss://i5d105.p.ssafy.io:8443/recording');
var webRtcPeer;
var state;


window.onbeforeunload = function() {
	ws.close();
}

const NO_CALL = 0;
const IN_CALL = 1;
const POST_CALL = 2;
const DISABLED = 3;
const IN_PLAY = 4;

export default {
  name: "SelfInterview",

  data() {
    return {
      // state: NO_CALL,
      isStart: false,
      isStop: false,
      isPlay: false,
      console:null,
      mode: 'video-and-audio', //video-and-audio, radio, video-only있음
    };
  },
  mounted(){
    videoInput = document.getElementById('videoInput');
    videoInput.autoplay = true;
    videoOutput = document.getElementById('videoOutput');
    videoOutput.autoplay = true;
  },
  created() {

    // websocket 메시지 등록
    ws.onmessage =  (message)=> {
      var parsedMessage = JSON.parse(message.data);
      console.info("Received message: " + message.data);

      switch (parsedMessage.id) {
        case "startResponse":
          this.startResponse(parsedMessage);
          break;
        case "playResponse":
          this.playResponse(parsedMessage);
          break;
        case "playEnd":
          this.playEnd();
          break;
        case "error":
          this.setState(NO_CALL);
          this.onError("Error message from server: " + parsedMessage.message);
          break;
        case "iceCandidate":
          webRtcPeer.addIceCandidate(parsedMessage.candidate,  (error)=> {
            if (error) return console.error("Error adding candidate: " + error);
          });
          break;
        case "stopped":
          break;
        case "paused":
          break;
        case "recording":
          break;
        default:
          this.setState(NO_CALL);
        this.onError("Unrecognized message", parsedMessage);
      }
    };
    this.setState(NO_CALL);
  },
  methods: {
    setState(nextState) {
      switch (nextState) {
        case NO_CALL:
          this.isStart = false;
          this.isStop = true;
          this.isPlay = true;
          break;
        case DISABLED:
          this.isStart = true;
          this.isStop = true;
          this.isPlay = true;
          break;
        case IN_CALL:
          this.isStart = true;
          this.isStop = false;
          this.isPlay = true;
          break;
        case POST_CALL:
          this.isStart = false;
          this.isStop = true;
          this.isPlay = false;
          break;
        case IN_PLAY:
          this.isStart = true;
          this.isStop = false;
          this.isPlay = true;
          break;
        default:
          this.onError("Unknown state " + nextState);
          return;
      }
      state = nextState;
    },
    start() {
      console.log("Starting video call ...");

      // Disable start button
      this.setState(DISABLED);
      console.log("Creating WebRtcPeer and generating local sdp offer ...");
      var options = {
        localVideo: videoInput,
        remoteVideo: videoOutput,
        mediaConstraints: this.getConstraints(),
        onicecandidate: this.onIceCandidate,
      };

      webRtcPeer = new kurentoUtils.WebRtcPeer.WebRtcPeerSendrecv(
        options, (error) => {
          if(error){
            return console.error(error);
          }
          webRtcPeer.generateOffer(this.onOffer);
        }
      );
      return false;
    },

    onOffer(error, offerSdp) {
      if (error) return console.error("Error generating the offer");
      console.info("Invoking SDP offer callback function " + location.host);
      console.log("현재 모드: "+this.mode)
      var message = {
        id: "start",
        sdpOffer: offerSdp,
        mode: this.mode,
      };
      this.sendMessage(message);
    },

    onError(error) {
      console.error(error);
    },

    onIceCandidate(candidate) {
      console.log("Local candidate" + JSON.stringify(candidate));

      var message = {
        id: "onIceCandidate",
        candidate: candidate,
      };
      this.sendMessage(message);
    },

    startResponse(message) {
      this.setState(IN_CALL);
      console.log("SDP answer received from server. Processing ...");

      webRtcPeer.processAnswer(message.sdpAnswer, (error)=> {
        if (error) return console.error(error);
      });
    },

    stop() {
      var stopMessageId = ( state == IN_CALL )? 'stop' : 'stopPlay';
      console.log("Stopping video while in " + state + "...");
      this.setState(POST_CALL);
      if (webRtcPeer) {
        webRtcPeer.dispose();
        webRtcPeer = null;

        var message = {
          id: stopMessageId,
        };
        this.sendMessage(message);
      }
      return false;
    },

    play() {
      console.log("Starting to play recorded video...");

      // Disable start button
      this.setState(DISABLED);

      console.log("Creating WebRtcPeer and generating local sdp offer ...");

      var options = {
        remoteVideo: videoOutput,
        mediaConstraints: this.getConstraints(),
        onicecandidate: this.onIceCandidate,
      };

      webRtcPeer = new kurentoUtils.WebRtcPeer.WebRtcPeerRecvonly(
        options, (error) => {
          if (error) return console.error(error);
          webRtcPeer.generateOffer(this.onPlayOffer);
        }
      );
      return false;
    },

    onPlayOffer(error, offerSdp) {
      if (error) return console.error("Error generating the offer");
      console.info("Invoking SDP offer callback function " + location.host);
      var message = {
        id: "play",
        sdpOffer: offerSdp,
      };
      this.sendMessage(message);
    },

    getConstraints() {
      var mode = this.mode;
      var constraints = {
        audio: true,
        video: true,
      };

      if (mode == 'video-only') {
        constraints.audio = false;
      } else if (mode == 'audio-only') {
        constraints.video = false;
      }

      return constraints;
    },

    playResponse(message) {
      this.setState(IN_PLAY);
      webRtcPeer.processAnswer(message.sdpAnswer,(error)=>{
        if (error) return console.error(error);
      });
    },

    playEnd() {
      this.setState(POST_CALL);
    },

    sendMessage(message) {
      var jsonMessage = JSON.stringify(message);
      console.log("Sending message: " + jsonMessage);
      ws.send(jsonMessage);
    },
  },
};
</script>

<style>
</style>
