package com.husen.jian.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by HuSen on 2018/9/20 15:01.
 */
@Data
public class MetaVo implements Serializable {
    private String title;
    private String icon;
    private List<String> roles;
    private Boolean noCache;
}
