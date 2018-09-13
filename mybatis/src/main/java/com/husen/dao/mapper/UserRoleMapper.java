package com.husen.dao.mapper;

import com.husen.dao.po.RolePo;
import com.husen.dao.po.UserRolePo;

import java.util.List;

/**
 * Created by HuSen on 2018/7/4 9:56.
 */
public interface UserRoleMapper {
    void save(UserRolePo userRolePo);
    List<UserRolePo> findAll();
    UserRolePo findByUserIdAndRoleId(UserRolePo userRolePo);

    List<RolePo> findByUserId(Long userId);

    void deleteByUserId(Long userId);

    void saveAll(List<UserRolePo> collect);

    void deleteByRoleId(Long roleId);
}
