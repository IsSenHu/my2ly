package com.husen.jian.dao.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by HuSen on 2018/9/21 15:36.
 */
@Data
public class UserVo implements Serializable {
    /**用户ID*/
    private Long userId;
    /**user*/
    private String user;
    /**用户状态*/
    private String status;
    /**code*/
    private String code;
    /**token*/
    private String token;
    /**用户名*/
    private String username;
    /**密码*/
    private String password;
    /**用户头像*/
    private String avatar;
    /**用户介绍*/
    private String introduction;
    /**用户角色*/
    private List<String> roles;
}
