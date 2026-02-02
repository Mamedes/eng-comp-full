package com.seletivo.infra.api.service.notification;

import com.seletivo.application.album.notification.AlbumNotificationService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class WebSocketAlbumNotificationService implements AlbumNotificationService {
    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketAlbumNotificationService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void notifyAlbumCreated(String message) {
        this.messagingTemplate.convertAndSend("/topic/albuns", message);
    }
}