package com.husen.dao.po;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * Created by HuSen on 2018/7/6 16:31.
 */
public class UserVo implements Serializable {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;
    @NotBlank(message = "用户名不能为空")
    @Length(max = 20, message = "用户名长度不能大于20")
    private String username;
    @Length(min = 6, max = 16, message = "密码长度在6-16之间")
    @NotBlank(message = "密码不能为空")
    private String password;
    @Length(max = 20, message = "真实姓名长度不能大于20")
    @NotBlank(message = "真实姓名不能为空")
    private String realName;
    @Length(max = 50, message = "邮箱长度不能大于50")
    @Email(message = "邮箱格式不正确")
    private String email;
    @NotBlank(message = "生日不能为空")
    private String birthday;
    @NotNull(message = "性别不能为空")
    private Integer gender;
    private Integer age;
    @Pattern(regexp = "^((13[0-9])|(14[5|7|9])|(15([0-3]|[5-9]))|(17[0|1|3|5|6|7|8])|(18[0-9])|(19[8|9]))\\d{8}$", message = "手机号格式不正确")
    private String phone;
    @Pattern(regexp = "[1-9][0-9]{4,14}", message = "QQ号格式不正确")
    private String qq;
    @NotBlank(message = "微信号不能为空")
    @Length(max = 50, message = "微信号长度不能大于50")
    private String weixin;

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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
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
}
