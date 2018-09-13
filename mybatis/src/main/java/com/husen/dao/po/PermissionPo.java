package com.husen.dao.po;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.util.Objects;

/**
 * 权限Po
 * Created by HuSen on 2018/7/3 15:04.
 */
public class PermissionPo implements Serializable {
    @JsonSerialize(using = ToStringSerializer.class)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PermissionPo po = (PermissionPo) o;
        return Objects.equals(permissionId, po.permissionId) &&
                Objects.equals(name, po.name) &&
                Objects.equals(menuId, po.menuId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(permissionId, name, menuId);
    }
}
