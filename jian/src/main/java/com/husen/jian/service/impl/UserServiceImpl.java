package com.husen.jian.service.impl;

import com.husen.jian.service.UserService;
import com.husen.jian.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.FileNotFoundException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by HuSen on 2018/9/18 9:42.
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private static final Map<Long, UserVo> data = new ConcurrentHashMap<>();

    @Override
    public Flux<UserVo> list() {
        return Flux.fromIterable(data.values());
    }

    @Override
    public Mono<UserVo> getById(Long id) {
        return Mono.justOrEmpty(data.get(id))
                .switchIfEmpty(Mono.error(new FileNotFoundException()));
    }

    @Override
    public Mono<UserVo> createOrUpdate(UserVo user) {
        data.put(user.getUserId(), user);
        return Mono.justOrEmpty(user);
    }

    @Override
    public Mono<UserVo> delete(Long id) {
        return Mono.justOrEmpty(data.remove(id));
    }

    @Override
    public Flux<UserVo> createOrUpdate(Flux<UserVo> user) {
        return user.doOnNext(userVo -> data.put(userVo.getUserId(), userVo));
    }
}
