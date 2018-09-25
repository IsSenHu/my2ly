package com.husen.jian.service.impl;

import com.husen.base.CommonResponse;
import com.husen.jian.dao.po.RolePo;
import com.husen.jian.dao.po.UserPo;
import com.husen.jian.dao.repository.UserRepository;
import com.husen.jian.dao.vo.UserVo;
import com.husen.jian.jwt.JwtUtil;
import com.husen.jian.service.BasicService;
import com.husen.jian.service.UserService;
import com.husen.jian.tran.UserPo2UserVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Created by HuSen on 2018/9/18 9:42.
 */
@Service
@Slf4j
public class UserServiceImpl extends BasicService implements UserService {
    private static final Map<Long, UserVo> data = new ConcurrentHashMap<>();
    private final UserPo2UserVo userPo2UserVo;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, JwtUtil jwtUtil, UserPo2UserVo userPo2UserVo) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.userPo2UserVo = userPo2UserVo;
    }

    @Override
    public Flux<UserVo> list() {
        return Flux.fromIterable(data.values());
    }

    @Override
    public Mono<UserVo> getById(Long id) {
        return Mono.justOrEmpty(data.get(id))
                .switchIfEmpty(Mono.error(new FileNotFoundException()));
    }

    /**
     * 根据token获取用户信息
     * @param userVo userVo
     * @return 验证成功响应用户信息 失败响应null
     */
    @Override
    public Mono<CommonResponse<UserVo>> getUserInfo(UserVo userVo) {
        return Mono.justOrEmpty(userVo)
                .map(vo -> {
                    if(StringUtils.isNotBlank(vo.getToken())) {
                        UserPo userPo = userRepository.findByToken(userVo.getToken());
                        if(!Objects.isNull(userPo)) {
                            return commonResponse(userPo2UserVo.apply(userPo), Constant.SUCCESS);
                        }
                    }
                    return commonResponse(userVo, Constant.AUTH_FAIL);
                })
                .switchIfEmpty(Mono.just(commonResponse(userVo, Constant.PARAM_EXCEPTION)));
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserPo userPo = userRepository.findByUsername(username);
        //加载用户角色
        List<RolePo> rolePoList = userPo.getRolePoList();
        if(CollectionUtils.isNotEmpty(rolePoList)) {
            userPo.setAuthorities(rolePoList.stream().map(rolePo -> new SimpleGrantedAuthority(rolePo.getRoleName())).collect(Collectors.toList()));
        }
        return userPo;
    }
}
