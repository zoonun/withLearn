// // Last time updated on: June 08, 2018
//
// // Latest file can be found here: https://cdn.webrtc-experiment.com/Screen-Capturing.js
//
// // Muaz Khan     - www.MuazKhan.com
// // MIT License   - www.WebRTC-Experiment.com/licence
// // Documentation - https://github.com/muaz-khan/Chrome-Extensions/tree/master/Screen-Capturing.js
// // Demo          - https://www.webrtc-experiment.com/Screen-Capturing/
//
// // ___________________
// // Screen-Capturing.js
//
// // Source code: https://github.com/muaz-khan/Chrome-Extensions/tree/master/desktopCapture
// // Google AppStore installation path: https://chrome.google.com/webstore/detail/screen-capturing/ajhifddimkapgcifgcodmmfdlknahffk
//
// // This JavaScript file is aimed to explain steps needed to integrate above chrome extension
// // in your own webpages
//
// // Usage:
// // getScreenConstraints(function(screen_constraints) {
// //    navigator.mediaDevices.getUserMedia({ video: screen_constraints }).then(onSuccess).catch(onFailure );
// // });
//
// // First Step: Download the extension, modify "manifest.json" and publish to Google AppStore
// //             https://github.com/muaz-khan/Chrome-Extensions/tree/master/desktopCapture#how-to-publish-yourself
//
// // Second Step: Listen for postMessage handler
// // postMessage is used to exchange "sourceId" between chrome extension and you webpage.
// // though, there are tons other options as well, e.g. XHR-signaling, websockets, etc.
// window.addEventListener('message', function(event) {
// 	if (event.origin != window.location.origin) {
// 		return;
// 	}
//
// 	onMessageCallback(event.data);
// });
//
// // and the function that handles received messages
//
// function onMessageCallback(data) {
// 	// "cancel" button is clicked
// 	if (data == 'PermissionDeniedError') {
// 		chromeMediaSource = 'PermissionDeniedError';
// 		if (screenCallback) return screenCallback('PermissionDeniedError');
// 		else throw new Error('PermissionDeniedError');
// 	}
//
// 	// extension notified his presence
// 	if (data == 'rtcmulticonnection-extension-loaded') {
// 		chromeMediaSource = 'desktop';
// 	}
//
// 	// extension shared temp sourceId
// 	if (data.sourceId && screenCallback) {
// 		screenCallback(sourceId = data.sourceId, data.canRequestAudioTrack === true);
// 	}
// }
//
// // global variables
// var chromeMediaSource = 'screen';
// var sourceId;
// var screenCallback;
//
// // this method can be used to check if chrome extension is installed & enabled.
// function isChromeExtensionAvailable(callback) {
// 	if (!callback) return;
//
// 	if (chromeMediaSource == 'desktop') return callback(true);
//
// 	// ask extension if it is available
// 	window.postMessage('are-you-there', '*');
//
// 	setTimeout(function() {
// 		if (chromeMediaSource == 'screen') {
// 			callback(false);
// 		} else callback(true);
// 	}, 2000);
// }
//
// // this function can be used to get "source-id" from the extension
// function getSourceId(callback) {
// 	if (!callback) throw '"callback" parameter is mandatory.';
// 	if(sourceId) return callback(sourceId);
//
// 	screenCallback = callback;
// 	window.postMessage('get-sourceId', '*');
// }
//
// // this function can be used to get "source-id" from the extension
// function getCustomSourceId(arr, callback) {
// 	if (!arr || !arr.forEach) throw '"arr" parameter is mandatory and it must be an array.';
// 	if (!callback) throw '"callback" parameter is mandatory.';
//
// 	if(sourceId) return callback(sourceId);
//
// 	screenCallback = callback;
// 	window.postMessage({
// 		'get-custom-sourceId': arr
// 	}, '*');
// }
//
// // this function can be used to get "source-id" from the extension
// function getSourceIdWithAudio(callback) {
// 	if (!callback) throw '"callback" parameter is mandatory.';
// 	if(sourceId) return callback(sourceId);
//
// 	screenCallback = callback;
// 	window.postMessage('audio-plus-tab', '*');
// }
//
// var isFirefox = typeof window.InstallTrigger !== 'undefined';
// var isOpera = !!window.opera || navigator.userAgent.indexOf(' OPR/') >= 0;
// var isChrome = !!window.chrome && !isOpera;
//
// function getChromeExtensionStatus(extensionid, callback) {
// 	if (isFirefox) return callback('not-chrome');
//
// 	if (arguments.length != 2) {
// 		callback = extensionid;
// 		extensionid = 'ajhifddimkapgcifgcodmmfdlknahffk'; // default extension-id
// 	}
//
// 	var image = document.createElement('img');
// 	image.src = 'chrome-extension://' + extensionid + '/icon.png';
// 	image.onload = function() {
// 		chromeMediaSource = 'screen';
// 		window.postMessage('are-you-there', '*');
// 		setTimeout(function() {
// 			if (chromeMediaSource == 'screen') {
// 				callback('installed-disabled');
// 			} else callback('installed-enabled');
// 		}, 2000);
// 	};
// 	image.onerror = function() {
// 		callback('not-installed');
// 	};
// }
//
// function getScreenConstraintsWithAudio(callback) {
// 	getScreenConstraints(callback, true);
// }
//
// // this function explains how to use above methods/objects
// function getScreenConstraints(error, screen_constraints) {
// 	if (error) {
// 		return alert(error);
// 	}
//
// 	navigator.getUserMedia = navigator.webkitGetUserMedia || navigator.mozGetUserMedia;
// 	navigator.webkitGetUserMedia({
// 		video: screen_constraints
// 	}, function(stream) {
// 		var video = document.querySelector('video');
// 		video.src = URL.createObjectURL(stream);
// 		video.play();
// 	}, function(error) {
// 		alert(JSON.stringify(error, null, '\t'));
// 	});
// };

/////////////////////////////////////////////////
// Last time updated on June 08, 2018

// Latest file can be found here: https://cdn.webrtc-experiment.com/getScreenId.js

// Muaz Khan         - www.MuazKhan.com
// MIT License       - www.WebRTC-Experiment.com/licence
// Documentation     - https://github.com/muaz-khan/getScreenId.

// ______________
// getScreenId.js

/*
getScreenId(function (error, sourceId, screen_constraints) {
    // error    == null || 'permission-denied' || 'not-installed' || 'installed-disabled' || 'not-chrome'
    // sourceId == null || 'string' || 'firefox'

    if(navigator.getDisplayMedia) {
        navigator.getDisplayMedia(screen_constraints).then(onSuccess, onFailure);
    }
    else {
        navigator.mediaDevices.getUserMedia(screen_constraints).then(onSuccess)catch(onFailure);
    }
}, 'pass second parameter only if you want system audio');
*/

(function() {
	window.getScreenId = function(callback, custom_parameter) {
		if(navigator.userAgent.indexOf('Edge') !== -1 && (!!navigator.msSaveOrOpenBlob || !!navigator.msSaveBlob)) {
			// microsoft edge => navigator.getDisplayMedia(screen_constraints).then(onSuccess, onFailure);
			callback({
				video: true
			});
			return;
		}

		// for Firefox:
		// sourceId == 'firefox'
		// screen_constraints = {...}
		if (!!navigator.mozGetUserMedia) {
			callback(null, 'firefox', {
				video: {
					mozMediaSource: 'window',
					mediaSource: 'window'
				}
			});
			return;
		}

		window.addEventListener('message', onIFrameCallback);

		function onIFrameCallback(event) {
			if (!event.data) return;

			if (event.data.chromeMediaSourceId) {
				if (event.data.chromeMediaSourceId === 'PermissionDeniedError') {
					callback('permission-denied');
				} else {
					callback(null, event.data.chromeMediaSourceId, getScreenConstraints(null, event.data.chromeMediaSourceId, event.data.canRequestAudioTrack));
				}

				// this event listener is no more needed
				window.removeEventListener('message', onIFrameCallback);
			}

			if (event.data.chromeExtensionStatus) {
				callback(event.data.chromeExtensionStatus, null, getScreenConstraints(event.data.chromeExtensionStatus));

				// this event listener is no more needed
				window.removeEventListener('message', onIFrameCallback);
			}
		}

		if(!custom_parameter) {
			setTimeout(postGetSourceIdMessage, 100);
		}
		else {
			setTimeout(function() {
				postGetSourceIdMessage(custom_parameter);
			}, 100);
		}
	};

	function getScreenConstraints(error, sourceId, canRequestAudioTrack) {
		var screen_constraints = {
			audio: false,
			video: {
				mandatory: {
					chromeMediaSource: error ? 'screen' : 'desktop',
					maxWidth: window.screen.width > 1920 ? window.screen.width : 1920,
					maxHeight: window.screen.height > 1080 ? window.screen.height : 1080
				},
				optional: []
			}
		};

		if(!!canRequestAudioTrack) {
			screen_constraints.audio = {
				mandatory: {
					chromeMediaSource: error ? 'screen' : 'desktop',
					// echoCancellation: true
				},
				optional: []
			};
		}

		if (sourceId) {
			screen_constraints.video.mandatory.chromeMediaSourceId = sourceId;

			if(screen_constraints.audio && screen_constraints.audio.mandatory) {
				screen_constraints.audio.mandatory.chromeMediaSourceId = sourceId;
			}
		}

		return screen_constraints;
	}

	function postGetSourceIdMessage(custom_parameter) {
		if (!iframe) {
			loadIFrame(function() {
				postGetSourceIdMessage(custom_parameter);
			});
			return;
		}

		if (!iframe.isLoaded) {
			setTimeout(function() {
				postGetSourceIdMessage(custom_parameter);
			}, 100);
			return;
		}

		if(!custom_parameter) {
			iframe.contentWindow.postMessage({
				captureSourceId: true
			}, '*');
		}
		else if(!!custom_parameter.forEach) {
			iframe.contentWindow.postMessage({
				captureCustomSourceId: custom_parameter
			}, '*');
		}
		else {
			iframe.contentWindow.postMessage({
				captureSourceIdWithAudio: true
			}, '*');
		}
	}

	var iframe;

	// this function is used in RTCMultiConnection v3
	window.getScreenConstraints = function(callback) {
		loadIFrame(function() {
			getScreenId(function(error, sourceId, screen_constraints) {
				if(!screen_constraints) {
					screen_constraints = {
						video: true
					};
				}

				callback(error, screen_constraints.video);
			});
		});
	};

	function loadIFrame(loadCallback) {
		if (iframe) {
			loadCallback();
			return;
		}

		iframe = document.createElement('iframe');
		iframe.onload = function() {
			iframe.isLoaded = true;

			loadCallback();
		};
		iframe.src = 'https://www.webrtc-experiment.com/getSourceId/'; // https://wwww.yourdomain.com/getScreenId.html
		iframe.style.display = 'none';
		(document.body || document.documentElement).appendChild(iframe);
	}

	window.getChromeExtensionStatus = function(callback) {
		// for Firefox:
		if (!!navigator.mozGetUserMedia) {
			callback('installed-enabled');
			return;
		}

		window.addEventListener('message', onIFrameCallback);

		function onIFrameCallback(event) {
			if (!event.data) return;

			if (event.data.chromeExtensionStatus) {
				callback(event.data.chromeExtensionStatus);

				// this event listener is no more needed
				window.removeEventListener('message', onIFrameCallback);
			}
		}

		setTimeout(postGetChromeExtensionStatusMessage, 100);
	};

	function postGetChromeExtensionStatusMessage() {
		if (!iframe) {
			loadIFrame(postGetChromeExtensionStatusMessage);
			return;
		}

		if (!iframe.isLoaded) {
			setTimeout(postGetChromeExtensionStatusMessage, 100);
			return;
		}

		iframe.contentWindow.postMessage({
			getChromeExtensionStatus: true
		}, '*');
	}
})();