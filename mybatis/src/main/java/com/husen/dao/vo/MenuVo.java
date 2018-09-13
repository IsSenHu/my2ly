package com.husen.dao.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.husen.dao.po.IconPo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by HuSen on 2018/8/3 17:25.
 */
public class MenuVo implements Serializable {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long menuId;
    /**菜单或按钮名称*/
    private String name;
    /**菜单地址*/
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
    private String permissionName;
    /**是菜单还是按钮*/
    private Integer buttonOrMenu;
    /**按钮或菜单是否启用*/
    private Integer isEnable;
    /**图标集合*/
    private List<IconPo> icons;

    public List<IconPo> getIcons() {
        return icons;
    }

    public void setIcons(List<IconPo> icons) {
        this.icons = icons;
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

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public Integer getButtonOrMenu() {
        return buttonOrMenu;
    }

    public void setButtonOrMenu(Integer buttonOrMenu) {
        this.buttonOrMenu = buttonOrMenu;
    }

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }

    @Override
    public String toString() {
        return "MenuVo{" +
                "menuId=" + menuId +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", level=" + level +
                ", icon='" + icon + '\'' +
                ", fatherMenuId='" + fatherMenuId + '\'' +
                ", permissionId=" + permissionId +
                ", permissionName='" + permissionName + '\'' +
                ", buttonOrMenu=" + buttonOrMenu +
                ", isEnable=" + isEnable +
                '}';
    }
}
