package com.husen.dao.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by HuSen on 2018/8/10 16:48.
 */
public class MenusVo implements Serializable {
    private String name;
    private String url;
    private MenusVo child;
    private List<String> pageGlobalButtons;
    private List<String> pageRowButtons;

    public List<String> getPageGlobalButtons() {
        return pageGlobalButtons;
    }

    public void setPageGlobalButtons(List<String> pageGlobalButtons) {
        this.pageGlobalButtons = pageGlobalButtons;
    }

    public List<String> getPageRowButtons() {
        return pageRowButtons;
    }

    public void setPageRowButtons(List<String> pageRowButtons) {
        this.pageRowButtons = pageRowButtons;
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

    public MenusVo getChild() {
        return child;
    }

    public void setChild(MenusVo child) {
        this.child = child;
    }

    @Override
    public String toString() {
        return "MenusVo{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", child=" + child +
                '}';
    }
}
