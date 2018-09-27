package com.husen.jian.dao.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;
import javax.persistence.*;

/**
 * Created by HuSen on 2018/9/21 10:01.
 */
@Data
@Entity
@Table(name = "t_user")
public class UserPo implements UserDetails {
    /**用户ID*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    /**user*/
    @Column(name = "user", length = 30)
    private String user;
    /**用户状态*/
    @Column(name = "status", length = 10)
    private String status;
    /**code*/
    @Column(name = "code", length = 1)
    private String code;
    /**token*/
    @Column(name = "token", length = 200)
    private String token;
    /**用户名*/
    @Column(name = "username", length = 30)
    private String username;
    /**密码*/
    @Column(name = "password", length = 200)
    private String password;
    /**用户头像*/
    @Column(name = "avatar", length = 200)
    private String avatar;
    /**用户介绍*/
    @Column(name = "introduction")
    private String introduction;
    /**
     * 用户拥有的角色 单边多对多
     * */
    @ManyToMany(targetEntity = RolePo.class, fetch = FetchType.EAGER)
    @JoinTable(name = "t_user_role",
            joinColumns = {@JoinColumn(name = "userId", referencedColumnName = "userId")},
            inverseJoinColumns = {@JoinColumn(name = "roleId", referencedColumnName = "roleId")})
    private List<RolePo> rolePoList;

    // TODO more

    /**
     * 权限
     * Transient 不持久化该字段 该字段作为普通属性使用
     */
    @Transient
    private List<? extends GrantedAuthority> authorities;

    public void setAuthorities(List<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    /**
     * 账户是否不过期
     * @return true是，false否
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 账户是否不锁定
     * @return true是，false否
     */
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 证书是否不过期
     * @return true是，false否
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 已启用
     * @return true是，false否
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    public static void main(String[] args) {
        System.out.println("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImNyZWF0ZWQiOjE1MzgxMTYzMjM5MDgsImV4cCI6MTUzODExNjMyM30.RuO86FzC6tNHXiUvgIQY5Y4QbblLr88U-PZzVuk0IKJ-kNx6yszYxw0zykfv98FFHkvaIODLlESoxK-8qXSQ1Q".length());
    }
}
