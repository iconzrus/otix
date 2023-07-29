var stompClient = null;
var username = null;

function connect() {
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, onConnected, onError);
}

function onConnected() {
    // Subscribe to the Public Topic
    stompClient.subscribe('/topic/public', onMessageReceived);

    // Tell your username to the server
    stompClient.send("/app/chat.addUser",
        {},
        JSON.stringify({sender: username, type: 'JOIN'})
    )
}

function onError(error) {
    console.log('Could not connect to WebSocket server. Please refresh this page to try again!');
}

function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);

    var messageElement = document.createElement('li');

    if(message.type === 'JOIN') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' joined!';
    }
    else if (message.type === 'LEAVE') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' left!';
    } else {
        messageElement.classList.add('chat-message');

        var usernameElement = document.createElement('strong');
        usernameElement.classList.add('nickname');
        usernameElement.appendChild(document.createTextNode(message.sender));
        messageElement.appendChild(usernameElement);
    }

    var textElement = document.createElement('span');
    textElement.appendChild(document.createTextNode(message.content));
    messageElement.appendChild(textElement);

    document.getElementById('messageList').appendChild(messageElement);
    document.getElementById('messageList').scrollTop = document.getElementById('messageList').scrollHeight;
}

document.getElementById('form').addEventListener('submit', sendMessage, true)

function sendMessage(event) {
    var messageContent = document.getElementById('msg').value.trim();
    if(messageContent && stompClient) {
        var chatMessage = {
            sender: username,
            content: document.getElementById('msg').value,
            type: 'CHAT'
        };
        stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
        document.getElementById('msg').value = '';
    }
    event.preventDefault();
}