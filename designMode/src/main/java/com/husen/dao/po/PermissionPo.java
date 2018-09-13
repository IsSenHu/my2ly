package com.husen.dao.po;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * 权限Po
 * Created by HuSen on 2018/7/3 15:04.
 */
public class PermissionPo implements Serializable {
    private Long permissionId;
    @NotBlank(message = "权限名称不能为空")
    private String name;
    private String description;
    private Long menuId;

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
