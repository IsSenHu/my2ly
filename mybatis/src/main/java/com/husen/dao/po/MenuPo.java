package com.husen.dao.po;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * 菜单Po
 * Created by HuSen on 2018/7/3 15:06.
 */
public class MenuPo implements Serializable {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long menuId;
    /**菜单或按钮名称*/
    @NotBlank(message = "菜单名称不能为空")
    private String name;
    /**菜单地址*/
    @NotBlank(message = "菜单地址不能为空")
    private String url;
    /**菜单等级*/
    private Integer level;
    /**菜单图标*/
    private String icon;
    /**父菜单ID*/
    private String fatherMenuId;
    /**该菜单被哪个权限拥有*/
    @JsonSerialize(using = ToStringSerializer.class)
    private Long permissionId;
    /**是菜单还是按钮*/
    private Integer buttonOrMenu;
    /**按钮或菜单是否启用*/
    private Integer isEnable;

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }

    public Integer getButtonOrMenu() {
        return buttonOrMenu;
    }

    public void setButtonOrMenu(Integer buttonOrMenu) {
        this.buttonOrMenu = buttonOrMenu;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getFatherMenuId() {
        return fatherMenuId;
    }

    public void setFatherMenuId(String fatherMenuId) {
        this.fatherMenuId = fatherMenuId;
    }
}
