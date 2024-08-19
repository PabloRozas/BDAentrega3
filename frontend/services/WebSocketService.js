import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';

export default class WebSocketService {
  constructor(token) {
    const socketUrl = `http://localhost:8080/websocket?token=${token}`;
    this.socket = new SockJS(socketUrl);
    this.stompClient = Stomp.over(this.socket);
  }

  connect(callback) {
    this.stompClient.connect({}, frame => {
      this.stompClient.subscribe('/topic/notifications', message => {
        const parsedMessage = JSON.parse(message.body);
        callback(parsedMessage);
      });
    });
  }

  disconnect() {
    if (this.stompClient !== null) {
      this.stompClient.disconnect();
    }
  }

  sendMessage(message) {
    this.stompClient.send('/app/message', {}, JSON.stringify(message));
  }
}
