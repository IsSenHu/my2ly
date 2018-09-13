package com.husen.dao.vo;

import java.io.Serializable;

/**
 * Created by HuSen on 2018/8/9 15:16.
 */
public class PermissionMenuVo implements Serializable {
    private Long permissionId;
    private Long menuId;

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    @Override
    public String toString() {
        return "PermissionMenuVo{" +
                "permissionId=" + permissionId +
                ", menuId=" + menuId +
                '}';
    }
}
