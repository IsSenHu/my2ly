package com.husen.dao.po;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * Created by HuSen on 2018/8/16 15:03.
 */
public class IconPo implements Serializable {
    private Integer iconId;
    @NotBlank(message = "图标名称不能为空")
    private String name;
    private String path;

    public Integer getIconId() {
        return iconId;
    }

    public void setIconId(Integer iconId) {
        this.iconId = iconId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "IconPo{" +
                "iconId=" + iconId +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
