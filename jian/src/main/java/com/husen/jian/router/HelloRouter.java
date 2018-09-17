package com.husen.jian.router;

import com.husen.jian.handler.HelloHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.*;

/**
 * Created by HuSen on 2018/9/17 18:34.
 */
@Configuration
public class HelloRouter {
    @Bean
    public RouterFunction<ServerResponse> responseRouterFunction(HelloHandler helloHandler) {
        return RouterFunctions
                .route(RequestPredicates.GET("/hello").and(RequestPredicates.accept(MediaType.TEXT_PLAIN)),
                        helloHandler::hello);
    }
}
