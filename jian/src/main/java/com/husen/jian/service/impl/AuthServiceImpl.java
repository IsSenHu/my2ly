package com.husen.jian.service.impl;

import com.husen.base.CommonResponse;
import com.husen.jian.dao.po.UserPo;
import com.husen.jian.dao.repository.UserRepository;
import com.husen.jian.dao.vo.UserVo;
import com.husen.jian.jwt.JwtUtil;
import com.husen.jian.service.AuthService;
import com.husen.jian.service.BasicService;
import com.husen.jian.tran.UserVo2UserPo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.util.Objects;

/**
 * Created by HuSen on 2018/9/21 13:05.
 */
@Slf4j
@Service
public class AuthServiceImpl extends BasicService implements AuthService {
    private final JwtUtil jwtUtil;
    private final UserVo2UserPo userVo2UserPo;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public AuthServiceImpl(JwtUtil jwtUtil, UserVo2UserPo userVo2UserPo, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.jwtUtil = jwtUtil;
        this.userVo2UserPo = userVo2UserPo;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 获取token
     * @param userVo 参数
     * @return token
     */
    @Override
    public Mono<CommonResponse<String>> getToken(UserVo userVo) {
        return Mono.justOrEmpty(userVo)
                .map(vo -> {
                    UserPo userPo = userRepository.findByUsername(vo.getUsername());
                    if(!Objects.isNull(userPo)) {
                        if(passwordEncoder.matches(userVo.getPassword(), userPo.getPassword())) {
                            String token = userPo.getToken();
                            if(jwtUtil.isTokenExpired(userPo.getToken())) {
                                token = jwtUtil.generateToken(userVo2UserPo.apply(userVo));
                                userPo.setToken(token);
                                userRepository.save(userPo);
                            }
                            return commonResponse(token, Constant.SUCCESS);
                        }
                    }
                    return commonResponse("", Constant.FAIL);
                })
                .switchIfEmpty(Mono.just(commonResponse("", Constant.PARAM_EXCEPTION)));
    }
}
