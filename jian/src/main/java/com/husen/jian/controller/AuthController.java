package com.husen.jian.controller;

import com.husen.base.CommonResponse;
import com.husen.jian.dao.vo.UserVo;
import com.husen.jian.service.AuthService;
import com.husen.jian.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * Created by HuSen on 2018/9/21 11:43.
 */
@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController extends BasicController {
    private final AuthService authService;
    private final UserService userService;
    @Autowired
    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    /**
     * 获取token
     * @param userVo 参数
     * @return token
     */
    @PostMapping("/getToken")
    public Mono<CommonResponse<String>> getToken(@RequestBody UserVo userVo) {
        return authService.getToken(userVo);
    }

    /**
     * 根据token获取用户信息
     * @param userVo userVo
     * @return 验证成功响应用户信息 失败响应null
     */
    @PostMapping("/getUserInfo")
    private Mono<CommonResponse<UserVo>> getUserInfo(@RequestBody UserVo userVo) {
        return userService.getUserInfo(userVo);
    }
}
