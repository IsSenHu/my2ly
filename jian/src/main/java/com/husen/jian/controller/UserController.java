package com.husen.jian.controller;

import com.husen.jian.service.UserService;
import com.husen.jian.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * Created by HuSen on 2018/9/18 9:43.
 */
@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    private Flux<UserVo> list() {
        return userService.list();
    }

    @GetMapping("/{id}")
    private Mono<UserVo> getById(@PathVariable("id") final Long id) {
        return userService.getById(id);
    }

    @PostMapping("")
    private Flux<UserVo> create(@RequestBody final Flux<UserVo> user) {
        return userService.createOrUpdate(user);
    }

    @PutMapping("/{id}")
    private Mono<UserVo> update(@PathVariable("id") final Long id, @RequestBody final UserVo user) {
        Objects.requireNonNull(user);
        user.setUserId(id);
        return userService.createOrUpdate(user);
    }

    @DeleteMapping("/{id}")
    private Mono<UserVo> delete(@PathVariable("id") final Long id) {
        return userService.delete(id);
    }
}
