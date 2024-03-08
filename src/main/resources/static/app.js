const userId = 'user-' + Math.random().toString(36).slice(2, 9);
const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/chat',
});

connect();

stompClient.onConnect = (frame) => {
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topic/public', (message) => {
        let response = JSON.parse(message.body);
        if (!response.content.includes("joined") ) {
            if (userId === response.userId) {
                displayMessage(userName, response.content, "right")
            } else {
                displayMessage(response.userName, response.content, "left")
            }
        } else {
            displayMessage("new", response.content, "");
        }
    });
};

stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};

function setConnected(connected) {
    let disconnectBtn = document.getElementById('disconnectBtn');
    let sendMessageForm = document.getElementById('sendMessageForm');
    let sendName = document.getElementById('sendName');
    let title = document.getElementById("title");
    if (connected) {
        disconnectBtn.classList.remove('hidden');
        sendMessageForm.classList.remove('hidden');
        sendName.classList.add('hidden');
        title.classList.remove('hidden');
    } else {
        connect();
        disconnectBtn.classList.add('hidden');
        sendMessageForm.classList.add('hidden');
        title.classList.add('hidden');
        sendName.classList.remove('hidden');
    }
    $("#message-container").html("");
}

function connect() {
    stompClient.activate();
}

function disconnect() {
    stompClient.deactivate();
    setConnected(false);
    console.log("Disconnected");
}

let userName;

function sendName() {
    let name = $("#name").val();
    if (name === "") {
        alert("Please, provide some name");
    }
    else {
        if (name !== "") {
            stompClient.publish({
                destination: "/app/addUser",
                body: JSON.stringify({'userName': name}),
                headers: { userId: userId }
            });
            userName = document.querySelector("#name").value;
            document.querySelector("#name").value = "";
        }
        setConnected(true);
    }
}

function sendMessage() {
    let message = $("#message").val();
    if (message !== "") {
        stompClient.publish({
            destination: "/app/sendMessage",
            body: JSON.stringify({'userName': userName, 'content': message}),
            headers: { userId: userId }
        });
        document.querySelector("#message").value = "";
    }
}

function displayMessage(userName, userMessage, messageJustify) {
    if (userName === "new") {
        $("#message-container").append("<tr><td><div class='user-added-info'>" + userMessage + "</div></td></tr>");
    } else {
        const newRow = $("<tr></tr>");
        const newCell = $("<td></td>");
        const divContainer = $("<div></div>").addClass(`${messageJustify}`);

        const divUserName = $("<div></div>")
            .addClass("user-name")
            .text(userName.split(" ").map((w) => w[0].toUpperCase()).join(""));

        const divUserMessage = $("<div></div>")
            .addClass("user-message")
            .text(userMessage);

        divContainer.append(divUserName);
        divContainer.append(divUserMessage);

        newCell.append(divContainer);

        newRow.append(newCell);

        $("#message-container").append(newRow);
    }
}

$(function () {
    $("form").on('submit', (e) => e.preventDefault());
    $("#disconnect").click(() => disconnect());
    $("#send-name-btn").click(() => sendName());
    $("#send-message-btn").click(() => sendMessage());
});
