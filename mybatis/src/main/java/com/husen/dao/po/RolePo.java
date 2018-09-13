package com.husen.dao.po;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.util.Objects;

/**
 * 角色Po
 * Created by HuSen on 2018/7/3 14:58.
 */
public class RolePo implements Serializable {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long roleId;
    @NotBlank(message = "角色名不能为空")
    private String name;
    private String description;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
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
        RolePo rolePo = (RolePo) o;
        return Objects.equals(roleId, rolePo.roleId) &&
                Objects.equals(name, rolePo.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(roleId, name);
    }
}
