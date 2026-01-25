import { Subject } from "rxjs";
import { Client, IFrame } from "@stomp/stompjs";
import SockJS from "sockjs-client";
import { API_ENDPOINTS } from "../constants/api.constants";

export const notification$ = new Subject<string>();

let stompClient: Client | null = null;

export const initNotificationSocket = (token: string): (() => void) => {
  const socket = new SockJS(`${API_ENDPOINTS.SOCKET.ENDPOINT}?token=${token}`);

  stompClient = new Client({
    webSocketFactory: () => socket,
    connectHeaders: {
      Authorization: `Bearer ${token}`,
    },
    debug: (msg: string) => {
      if (import.meta.env.DEV) console.log("[WS Debug]:", msg);
    },
    reconnectDelay: 5000,
    heartbeatIncoming: 4000,
    heartbeatOutgoing: 4000,
  });

  stompClient.onConnect = (frame: IFrame) => {
    stompClient?.subscribe("/topic/albuns", (message) => {
      if (message.body) {
        notification$.next(message.body);
      }
    });
  };

  stompClient.activate();

  return () => {
    if (stompClient?.active) {
      stompClient.deactivate();
    }
  };
};
