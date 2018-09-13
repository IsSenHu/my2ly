package com.husen.dao.mapper;

import com.husen.dao.po.PermissionPo;
import com.husen.dao.vo.PageReqVo;
import com.husen.dao.vo.PermissionMenuVo;

import java.util.List;

/**
 * Created by HuSen on 2018/7/4 10:30.
 */
public interface PermissionMapper {
    void save(PermissionPo permissionPo);
    PermissionPo findByName(String name);
    PermissionPo findById(Long permissionId);
    int count(PermissionPo permissionPo);
    List<PermissionPo> page(PageReqVo<PermissionPo> pageReqVo);
    void deletePermissionById(Long permissionId);
    List<PermissionPo> findByRoleId(Long roleId);
    List<PermissionPo> findAll();
    void clearMenu(Long permissionId);
    void addMenu(PermissionMenuVo vo);
}
