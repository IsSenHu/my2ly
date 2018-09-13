//package com.husen.config.websocket;
//
//import com.husen.base.Base;
//import com.husen.config.security.WebSecurityConstant;
//import com.husen.dao.po.UserPo;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.socket.*;
//import org.springframework.web.socket.config.annotation.EnableWebSocket;
//import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
//import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
//import org.springframework.web.socket.handler.TextWebSocketHandler;
//
//import java.util.Objects;
//
///**
// * Created by HuSen on 2018/7/4 16:16.
// */
//@Configuration
//@EnableWebSocket
//public class WebSocketConfig extends Base implements WebSocketConfigurer {
//    private static final Logger log = LoggerFactory.getLogger(WebSocketConfig.class);
//
//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
//        webSocketHandlerRegistry.addHandler(userLogoutHandler(), "/userLogoutHandler");
//    }
//
//    @Bean
//    public WebSocketHandler userLogoutHandler() {
//        return new TextWebSocketHandler() {
//
//            @Override
//            public void afterConnectionEstablished(WebSocketSession session) {
//                UserPo user = getUser();
//                if(!Objects.isNull(user)) {
//                    log.info("用户:[{}]建立WebSocket连接完成", user.getUsername());
//                    WebSecurityConstant.USER_WEB_SOCKET_SESSION_MAP.put(user.getUserId(), session);
//                }
//            }
//
//            @Override
//            protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//                log.info("正在建立连接");
//                session.sendMessage(message);
//                super.handleTextMessage(session, message);
//            }
//        };
//    }
//}
