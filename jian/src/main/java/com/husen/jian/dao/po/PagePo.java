package com.husen.jian.dao.po;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by HuSen on 2018/9/21 14:58.
 */
@Data
@Entity
@Table(name = "t_page")
public class PagePo implements Serializable {
    /**pageId*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pageId;
    /**路径*/
    @Column(name = "path", length = 30)
    private String path;
    /**组件*/
    @Column(name = "component", length = 30)
    private String component;
    /**定向页面*/
    @Column(name = "redirect", length = 30)
    private String redirect;
    /**路由名称*/
    @Column(name = "name", length = 20)
    private String name;
    /**总是显示*/
    @Column(name = "alwaysShow")
    private Boolean alwaysShow;
    /**隐藏*/
    @Column(name = "hidden")
    private Boolean hidden;
    /**是否根目录*/
    @Column(name = "isRoot")
    private Boolean isRoot;
    /**父节点ID*/
    @Column(name = "fId")
    private Long fId;
    /**菜单名称*/
    @Column(name = "title", length = 20)
    private String title;
    /**图标*/
    @Column(name = "icon", length = 30)
    private String icon;
    /**该页面需要的角色 为空则所有人可访问*/
    @OneToOne(targetEntity = RolePo.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "roleId")
    private RolePo rolePo;
    /**是否缓存*/
    @Column(name = "noCache")
    private Boolean noCache;
}
