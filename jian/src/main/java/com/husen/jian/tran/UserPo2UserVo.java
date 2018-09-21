package com.husen.jian.tran;

import com.husen.jian.dao.po.RolePo;
import com.husen.jian.dao.po.UserPo;
import com.husen.jian.dao.vo.UserVo;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by HuSen on 2018/9/21 17:47.
 */
@Component
public class UserPo2UserVo implements Function<UserPo, UserVo> {
    @Override
    public UserVo apply(UserPo userPo) {
        UserVo userVo = new UserVo();
        if(!Objects.isNull(userPo)) {
            userVo.setUserId(userPo.getUserId());
            userVo.setUser(userPo.getUser());
            userVo.setAvatar(userPo.getAvatar());
            userVo.setCode(userPo.getCode());
            userVo.setIntroduction(userPo.getIntroduction());
            userVo.setPassword(userPo.getPassword());
            userVo.setStatus(userPo.getStatus());
            userVo.setToken(userPo.getToken());
            userVo.setUsername(userPo.getUsername());
            List<RolePo> rolePoList = userPo.getRolePoList();
            if(CollectionUtils.isNotEmpty(rolePoList)) {
                userVo.setRoles(rolePoList.stream().map(RolePo::getRoleName).collect(Collectors.toList()));
            }else {
                userVo.setRoles(new ArrayList<>());
            }
        }
        return userVo;
    }
}
