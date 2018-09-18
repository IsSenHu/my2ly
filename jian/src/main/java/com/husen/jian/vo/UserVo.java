package com.husen.jian.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by HuSen on 2018/9/18 9:40.
 */
@Data
public class UserVo implements Serializable {
    private Long userId;
    private String username;
    private String password;
}
