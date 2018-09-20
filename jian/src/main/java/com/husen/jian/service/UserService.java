package com.husen.jian.service;

import com.husen.jian.vo.UserVo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by HuSen on 2018/9/18 9:42.
 */
public interface UserService {
    Flux<UserVo> list();

    Mono<UserVo> getById(Long id);

    Mono<UserVo> createOrUpdate(UserVo user);

    Mono<UserVo> delete(Long id);

    Flux<UserVo> createOrUpdate(Flux<UserVo> user);
}
