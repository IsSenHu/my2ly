package com.husen.dao.mapper;

import com.husen.dao.po.PermissionPo;
import com.husen.dao.po.RolePermissionPo;

import java.util.List;

/**
 * Created by HuSen on 2018/7/5 13:31.
 */
public interface RolePermissionMapper {
    void save(RolePermissionPo rolePermissionPo);

    RolePermissionPo findByRoleIdAndPermissionId(RolePermissionPo rolePermissionPo);

    void deleteByRoleId(Long roleId);

    void saveAll(List<RolePermissionPo> collect);

    List<PermissionPo> findByRoleId(Long roleId);

    void deleteByPermissionId(Long permissionId);
}
