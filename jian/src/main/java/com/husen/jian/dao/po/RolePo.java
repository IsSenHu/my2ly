package com.husen.jian.dao.po;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by HuSen on 2018/9/21 14:53.
 */
@Data
@Entity
@Table(name = "t_role")
public class RolePo implements Serializable {
    /**roleId*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    /**角色名称*/
    @Column(name = "roleName", length = 20)
    private String roleName;

    /**该角色可访问的页面*/
    @OneToOne(targetEntity = PagePo.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "pageId")
    private PagePo pagePo;
}
