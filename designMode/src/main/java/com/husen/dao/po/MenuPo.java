package com.husen.dao.po;

import java.io.Serializable;

/**
 * 菜单Po
 * Created by HuSen on 2018/7/3 15:06.
 */
public class MenuPo implements Serializable {
    private Long menuId;
    private String name;
    private String url;
    private Integer level;
    private String icon;
    private String fatherMenuId;
    private Long permissionId;
    private Integer buttonOrMenu;

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
