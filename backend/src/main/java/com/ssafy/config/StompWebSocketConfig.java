package com.ssafy.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Profile("stomp")
@EnableWebSocketMessageBroker
@Configuration
public class StompWebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 최초 소켓 연결을 하는 경우, endpoint가 되는 url이다. 추후 javascript에서 SockJS 생성자를 통해 연결
        registry.addEndpoint("/chat").setAllowedOrigins("*").withSockJS();
    }
    /**
     *     'chat'은 WebSocket 클라이언트가 Handshake를 위해 연결해야하는 end-point의 url이다
     *     프론트에서 websocket에 접속할 때 sockJS를 사용한다면 다음과 같이 연결하게 된다
     *     let socket = new SockJS('http://localhost:8080/chat')
     *     this.stompClient = Stomp.over(socket)
     */


    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app"); // 클라이언트에서 메세지 송신 시 붙여줄 prefix를 정의
        registry.enableSimpleBroker("/topic"); // 클라이언트로 메세지를 응답해줄 때 prefix를 정의
    }
}