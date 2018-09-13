package com.husen.dao.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * 用户Po
 * Created by HuSen on 2018/6/28 11:00.
 */
public class UserPo implements Serializable, UserDetails {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;
    @NotBlank(message = "用户名不能为空")
    @Length(max = 20, message = "用户名长度不能大于20")
    private String username;
    @Length(min = 6, max = 16, message = "密码长度在6-16之间")
    @NotBlank(message = "密码不能为空")
    private String password;
    @Length(min = 20, message = "真实姓名长度不能大于20")
    @NotBlank(message = "真实姓名不能为空")
    private String realName;
    @Length(max = 50, message = "邮箱长度不能大于50")
    @Email(message = "邮箱格式不正确")
    private String email;
    @NotNull(message = "生日不能为空")
    private LocalDate birthday;
    @NotNull(message = "性别不能为空")
    private Integer gender;
    private Integer age;
    @Pattern(regexp = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\\\d{8}$", message = "手机号格式不正确")
    private String phone;
    @Pattern(regexp = "[1-9][0-9]{4,14}", message = "QQ号格式不正确")
    private String qq;
    @NotBlank(message = "微信号不能为空")
    @Length(max = 50, message = "微信号长度不能大于50")
    private String weixin;
    //todo 其他
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPo po = (UserPo) o;
        return Objects.equals(userId, po.userId) &&
                Objects.equals(username, po.username) &&
                Objects.equals(password, po.password) &&
                Objects.equals(realName, po.realName) &&
                Objects.equals(email, po.email) &&
                Objects.equals(phone, po.phone) &&
                Objects.equals(qq, po.qq) &&
                Objects.equals(weixin, po.weixin) &&
                Objects.equals(authorities, po.authorities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, username, realName, email, phone);
    }
}
