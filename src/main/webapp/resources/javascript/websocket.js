var wsUri = "ws://" + document.location.host + "/hhcib/hhcibWebSocketSliderFacade";
//var wsUri = "ws://hhcib-app.enbohm.cloudbees.net/hhcib/app/hhcibWebSocketSliderFacade";

var websocket = new WebSocket(wsUri);

websocket.onerror = function(evt) { onError(evt) };

function onError(evt) {
    writeToScreen('<span style="color: red;">ERROR:</span> ' + evt.data);
}

websocket.onopen = function(evt) { 
	onOpen(evt) 
};

function onOpen() {
	console.log("Connected to : " + wsUri);
}

function writeToScreen(message) {
	document.getElementById("current").innerHTML = message;
}

websocket.onmessage = function(evt) { onMessage(evt) };
                
function onMessage(evt) {
    //console.log("received: " + evt.data);
    writeToScreen(evt.data);
}