package com.husen.dao.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.io.Serializable;
import java.util.List;

/**
 * zTree节点Vo
 * Created by HuSen on 2018/8/6 17:26.
 */
public class TreeNode implements Serializable {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long nodeId;
    /**节点的勾选状态*/
    private Boolean checked;
    /**子节点数据*/
    private List<TreeNode> children;
    /**设置节点的 checkbox / radio 是否禁用*/
    private Boolean chkDisabled;
    /**click 事件*/
    private String click;
    /**强制节点的 checkBox / radio 的半勾选状态*/
    private Boolean halfCheck;
    /**判断 treeNode 节点是否被隐藏*/
    private Boolean isHidden;
    /**记录 treeNode 节点是否为父节点*/
    private Boolean isParent;
    /**节点名称*/
    private String name;
    /**设置节点是否隐藏*/
    private Boolean nocheck;
    /**记录 treeNode 节点的 展开 / 折叠 状态*/
    private Boolean open;
    /**设置点击节点后在何处打开 url。[treeNode.url 存在时有效]*/
    private String target;
    /**节点链接的目标 URL*/
    private String url;
    /**自定义属性 类型*/
    private String type;
    /**自定义属性 等级*/
    private Integer level;
    /**图标 如果只设置这个 则展开和关闭都使用同一个图标*/
    private String icon;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    public Boolean getChkDisabled() {
        return chkDisabled;
    }

    public void setChkDisabled(Boolean chkDisabled) {
        this.chkDisabled = chkDisabled;
    }

    public String getClick() {
        return click;
    }

    public void setClick(String click) {
        this.click = click;
    }

    public Boolean getHalfCheck() {
        return halfCheck;
    }

    public void setHalfCheck(Boolean halfCheck) {
        this.halfCheck = halfCheck;
    }

    public Boolean getHidden() {
        return isHidden;
    }

    public void setHidden(Boolean hidden) {
        isHidden = hidden;
    }

    public Boolean getParent() {
        return isParent;
    }

    public void setParent(Boolean parent) {
        isParent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getNocheck() {
        return nocheck;
    }

    public void setNocheck(Boolean nocheck) {
        this.nocheck = nocheck;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "nodeId=" + nodeId +
                ", checked=" + checked +
                ", children=" + children +
                ", chkDisabled=" + chkDisabled +
                ", click='" + click + '\'' +
                ", halfCheck=" + halfCheck +
                ", isHidden=" + isHidden +
                ", isParent=" + isParent +
                ", name='" + name + '\'' +
                ", nocheck=" + nocheck +
                ", open=" + open +
                ", target='" + target + '\'' +
                ", url='" + url + '\'' +
                ", type='" + type + '\'' +
                ", level=" + level +
                ", icon='" + icon + '\'' +
                '}';
    }
}
