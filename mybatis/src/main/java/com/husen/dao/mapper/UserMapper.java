package com.husen.dao.mapper;

import com.husen.dao.po.UserPo;
import com.husen.dao.vo.PageReqVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by HuSen on 2018/6/28 11:03.
 */
@Repository
public interface UserMapper {
    List<UserPo> findAll();
    void save(UserPo userPo);
    UserPo findByUsername(String username);
    UserPo findById(Long userId);
    int count(UserPo userPo);
    List<UserPo> page(PageReqVo<UserPo> poPageReqVo);
    void update(UserPo userPo);
    void delete(Long userId);
    UserPo findUserById(Long userId);
}
