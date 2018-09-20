package com.husen.jian.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

/**
 * Created by HuSen on 2018/9/18 10:39.
 */
@Component
public class EchoHandler implements WebSocketHandler {
    @Override
    public Mono<Void> handle(final WebSocketSession webSocketSession) {
        return webSocketSession.send(
                webSocketSession.receive().map(msg -> webSocketSession.textMessage("ECHO->" + msg.getPayloadAsText()))
        );
    }
}
