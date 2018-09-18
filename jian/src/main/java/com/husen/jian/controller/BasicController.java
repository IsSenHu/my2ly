package com.husen.jian.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * Created by HuSen on 2018/9/18 9:37.
 */
@RestController
public class BasicController {
    @GetMapping("/sayHelloWorld")
    private Mono<String> sayHelloWorld() {
        return Mono.just("你好，世界");
    }
}
