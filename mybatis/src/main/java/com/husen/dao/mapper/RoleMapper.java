package com.husen.dao.mapper;

import com.husen.dao.po.RolePo;
import com.husen.dao.vo.PageReqVo;
import java.util.List;

/**
 * Created by HuSen on 2018/7/4 9:32.
 */
public interface RoleMapper {
    List<RolePo> findAll();
    RolePo findById(Long roleId);
    void save(RolePo rolePo);
    RolePo findByName(String name);

    int count(RolePo pageReqVo);

    List<RolePo> page(PageReqVo<RolePo> pageReqVo);

    void deleteRoleById(Long roleId);
}
