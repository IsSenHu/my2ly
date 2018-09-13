package com.husen.dao.po;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * 角色Po
 * Created by HuSen on 2018/7/3 14:58.
 */
public class RolePo implements Serializable {
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
}
