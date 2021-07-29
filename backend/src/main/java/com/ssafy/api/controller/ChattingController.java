package com.ssafy.api.controller;

import com.ssafy.db.entity.Chatting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;

/**
 * 인증 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Profile("stomp")
public class ChattingController {
   private final SimpMessagingTemplate template;

   @Autowired
   public ChattingController(SimpMessagingTemplate template) {
      this.template = template;
   }

   @MessageMapping("/chat/join")
   public void join(Chatting message) {
      message.setContents(message.getUser().getName() + "님이 입장하셨습니다.");
      template.convertAndSend("/topic/chat/room/" + message.getConference().getId(), message);
   }

   @MessageMapping("/chat/message")
   public void message(Chatting message) {
      template.convertAndSend("/topic/chat/room/" + message.getConference().getId(), message);
   }
}