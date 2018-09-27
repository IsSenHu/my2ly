package com.husen.jian.tran;

import com.husen.jian.dao.po.UserPo;
import com.husen.jian.dao.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import java.util.Objects;
import java.util.function.Function;

/**
 * Created by HuSen on 2018/9/21 13:10.
 */
@Component
public class UserVo2UserPo implements Function<UserVo, UserPo> {
    @Override
    public UserPo apply(UserVo userVo) {
        UserPo userPo = new UserPo();
        if(!Objects.isNull(userVo)) {
            BeanUtils.copyProperties(userVo, userPo);
        }
        return userPo;
    }
}
