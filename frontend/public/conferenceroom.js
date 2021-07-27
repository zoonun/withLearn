const ws = new WebSocket('wss://' + 'localhost:8443' + '/groupcall')
const participants = {}
let name

window.onbeforeunload = () => {
  ws.close()
}

ws.onmessage = function(message) {
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

function register() {
  const name = 'Joo.non'
  const room = 'WebRTC'

  document.getElementById('room-header').innerText = 'ROOM' + room
  document.getElementById('join').style.display = 'none'
  document.getElementById('room').style.display = 'block'

  const message = {
    id: 'joinRoom',
    name: name,
    room: room
  }
  sendMessage(message)
}

function onNewParticipant(request) {
  receiveVideo(request.name)
}

function receiveVideoResponse(result) {
  participants[result.name].rtcPeer.processAnswer (result.sdpAnswer, function (error) {
    if (error) return console.error(error)
  })
}

function callResponse(message) {
  if (message.response != 'accepted') {
    console.info('Call not accepted by peer. Closing call');
    stop()
  } else {
    webRtcPeer.processAnswer(message.sdpAnswer, function (error) {
      if (error) return console.error (error);
    })
  }
}

function onExistingParticipants(message) {
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
  console.log(name + ' registered in room ' + room)
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

function leaveRoom() {
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

function receiveVideo(sender) {
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

function onParticipantLeft(request) {
  console.log('Participant ' + request.name + ' left');
  const participant = participants[request.name];
  participant.dispose();
  delete participants[request.name];
}

function sendMessage(message) {
  var jsonMessage = JSON.stringify(message);
  console.log('Sending message: ' + jsonMessage);
  ws.send(jsonMessage);
}
