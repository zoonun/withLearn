package com.ssafy.api.controller;

import com.ssafy.api.domain.Chat;
import com.ssafy.api.service.ChattingService;
import com.ssafy.api.service.ConferenceService;
import com.ssafy.db.entity.Chatting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

/**
 * 인증 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Profile("stomp")
@RestController
public class ChattingController {

    @Autowired
    ChattingService chattingService;

    private final SimpMessagingTemplate template;

    @Autowired
    public ChattingController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @MessageMapping("/chat/join/{conferenceId}")
    public void join(@DestinationVariable Long conferenceId, Chat message) {
        template.convertAndSend("/topic/chat/" + conferenceId, message.getName() + "님이 입장하셨습니다.");
    }

    @MessageMapping("/chat/message/{conferenceId}")
    public void message(@DestinationVariable Long conferenceId, Chat message) {
//        System.out.println(message);
//        chattingService.saveChatting(message);
        template.convertAndSend("/topic/chat/" + conferenceId, message);
    }
}