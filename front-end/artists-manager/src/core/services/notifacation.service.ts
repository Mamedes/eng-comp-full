import { Subject } from "rxjs";

export const notification$ = new Subject<any>();

export const initNotificationSocket = (token: string) => {
  const ws = new WebSocket(`ws://localhost:8081/ws?token=${token}`);

  ws.onmessage = (event) => {
    const data = JSON.parse(event.data);
    notification$.next(data);
  };

  return () => ws.close();
};
