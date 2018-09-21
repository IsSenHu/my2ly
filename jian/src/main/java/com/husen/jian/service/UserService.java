package com.husen.jian.service;

import com.husen.base.CommonResponse;
import com.husen.jian.dao.vo.UserVo;
import org.springframework.security.core.userdetails.UserDetailsService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by HuSen on 2018/9/18 9:42.
 */
public interface UserService extends UserDetailsService {
    Flux<UserVo> list();

    Mono<UserVo> getById(Long id);

    Mono<UserVo> createOrUpdate(UserVo user);

    Mono<UserVo> delete(Long id);

    Flux<UserVo> createOrUpdate(Flux<UserVo> user);

    Mono<CommonResponse<UserVo>> getUserInfo(UserVo userVo);
}
