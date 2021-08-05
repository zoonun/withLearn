const PARTICIPANT_MAIN_CLASS = 'participant main';
const PARTICIPANT_CLASS = 'participant';
import sendMessage from '../groupcall'

/**
 * Creates a video element for a new participant
 *
 * @param {String} name - the name of the new participant, to be used as tag
 *                        name of the video element.
 *                        The tag of the new element will be 'video<name>'
 * @return
 */
export default function Participant(pname) {
  this.name = pname;
  var container = document.createElement('div');
  container.className = isPresentMainParticipant() ? PARTICIPANT_CLASS : PARTICIPANT_MAIN_CLASS;
  container.id = pname;
  var span = document.createElement('span');
  var video = document.createElement('video');
  var rtcPeer;

  container.appendChild(video);
  container.appendChild(span);
  container.onclick = switchContainerClass;
  document.getElementById('participants').appendChild(container);

  span.appendChild(document.createTextNode(this.name));

  video.id = 'video-' + this.name;
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
      var elements = Array.prototype.slice.call(document.getElementsByClassName(PARTICIPANT_MAIN_CLASS));
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
    var msg =  { id : 'receiveVideoFrom',
        sender : this.name,
        sdpOffer : offerSdp
      };
    sendMessage(msg);
  }


  this.onIceCandidate = function (candidate, wp) {
    console.log('Local candidate' + JSON.stringify(candidate));

    var message = {
      id: 'onIceCandidate',
      candidate: candidate,
      name: this.name
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
