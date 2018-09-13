package com.husen.dao.po;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by HuSen on 2018/7/4 10:29.
 */
public class RolePermissionPo implements Serializable {
    private Long id;
    @NotNull(message = "角色ID不能为空")
    private Long roleId;
    @NotNull(message = "权限ID不能为空")
    private Long permissionId;

    public RolePermissionPo() {
    }

    public RolePermissionPo(Long id, Long roleId, Long permissionId) {
        this.id = id;
        this.roleId = roleId;
        this.permissionId = permissionId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }
}
