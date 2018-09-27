package com.husen.jian.service;

import com.husen.base.CommonResponse;
import com.husen.jian.dao.vo.UserVo;
import reactor.core.publisher.Mono;

/**
 * Created by HuSen on 2018/9/21 13:05.
 */
public interface AuthService {
    Mono<CommonResponse<String>> getToken(UserVo userVo);
}
