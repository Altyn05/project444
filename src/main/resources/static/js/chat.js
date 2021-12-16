const urlApi = "http://localhost:8888/chatapi/";
let arrUsers = [];
let arrChats = [];
let arrMessages = [];
let lastActiveButton = -1;

function onError() {
    console.log("Stomp error!");
}

const connect = () => {
    console.log("try connect");
   // const Stomp = require("stompjs");
  //  var SockJS = require("sockjs-client");
    SockJS = new SockJS("http://localhost:8888/ws");
    stompClient = Stomp.over(SockJS);
    stompClient.connect({}, onConnected, onError);
};

function onMessageReceived(msg) {
    console.log("message on server!");
    console.log(msg);

    let chatId = document.getElementById("sChatId");
    loadMessages(chatId.innerText).then();



}

const onConnected = () => {
    console.log("connected");

    let owner = document.getElementById("sId");

    stompClient.subscribe(
        "/user/queue/messages",// +  owner.innerText,
      onMessageReceived
    );

};


const sendMessage = (msg) => {
    if (msg.trim() !== "") {
        let owner = document.getElementById("sId");
        let chatID= document.getElementById("sChatId");
        const message = {
            fromUserId: owner.innerText,
            chatId: chatID.innerText,
            message: msg
        };
        console.log("sending");
        stompClient.send("/app/ws", {}, JSON.stringify(message));
    }
};

function onSendMessage(){
    let x = document.getElementById("text2send");
    sendMessage(x.value);
    x.value = "";
}


function onAddChat(){
    $('.windowChatAdd #modalChatAdd').modal("show");
}

function onButtonFind(){
    loadUsers().then();
}
async function onAddUser2Chat(id) {
    const res = fetch(urlApi+"addUser",{
        method: 'post',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
        },
        body: 'id='+id
    });
    res.json();
   await loadChats();
}


const displayUsers = (list) => {
        usersList.innerHTML = list
        .map((user) => {
            return `
            <tr id="${user.id}">
                <td>${user.email}</td>
                <td>${user.firstName}</td>
                <td id="add2Chat"><button type="button" class="btn btn-info" onclick="onAddUser2Chat(${user.id})">Добавить</button></td>
            </tr>`;
        }) .join('');


}
const loadUsers = async () => {
    const res = await fetch(urlApi+"users",{
        method: 'post',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
        },
        body: 'eMail='+document.getElementById('findByEMail').value

    });
    arrUsers = await res.json();

   displayUsers(arrUsers);
};

function switchChat(chat_id){
    if (( lastActiveButton == -1 ) || ( lastActiveButton == null )) {

    } else {
      lastActiveButton.classList.remove("active");
    }
    let docID="buttonChats"+chat_id;
    lastActiveButton = document.getElementById(docID);
    lastActiveButton.classList.add("active");

    let x = document.getElementById("chatName");
    let nameElem = document.getElementById("bChatName"+chat_id);

    let chatId = document.getElementById("sChatId");
    chatId.innerText = chat_id;
    x.innerText = nameElem.innerText;
    loadMessages(chat_id).then();
}

const displayMessages = (list) => {
    messageList.innerHTML = list
        .map((message) => {

        return `
                  <li class="clearfix">
                      <div class="message-data">
                          <span class="message-data-time"></span>
                      </div>
                      <div class="message my-message">${message.textMessage}</div>
                   </li>
                                           
            `
            ;
    })
        .join('');

}
async function loadMessages(chat_id){
    const res = await fetch(urlApi+"getChatMessages/"+chat_id,{
        method: 'get',
        headers: {
            'Accept': 'application/json',
        }});
    arrMessages = await res.json();

     displayMessages(arrMessages);



}

const displayChats = (list) => {



    chatList.innerHTML = list
        .map((chat) => {

            return `
                            <li class="nav-item" role = "presentation">
                            <button id="buttonChats${chat.id}" class="list-group-item list-group-item-action position-relative" type="button" onclick="switchChat(${chat.id})" >${chat.chatName}
                                <span hidden id="bChatName${chat.id}" >${chat.chatName}</span>
                                <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger" id="badge-reviews">0</span>
                                <span hidden id="bOwnerId${chat.id}">${chat.owner.id}</span>
                            </button>
                            </li>
            `;
        }).join('');

}

const  loadChats = async () => {
    const res = await fetch(urlApi+"getChats",{
        method: 'get',
        headers: {
            'Accept': 'application/json',
        }});
    arrChats = await res.json();
    displayChats(arrChats);
}

const  loadIds = async () => {
    const res = await fetch(urlApi+"getId",{
        method: 'get',
        headers: {
            'Accept': 'application/json',
        }});
    let id = await res.json();

    let owner = document.getElementById("sId");
    owner.innerText = id.id;


}

loadIds().then();
loadChats().then();

connect();
