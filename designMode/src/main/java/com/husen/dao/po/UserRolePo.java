package com.husen.dao.po;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by HuSen on 2018/7/3 15:02.
 */
public class UserRolePo implements Serializable {
    private Long id;
    @NotNull(message = "用户ID不能为空")
    private Long userId;
    @NotNull(message = "角色ID不能为空")
    private Long roleId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
