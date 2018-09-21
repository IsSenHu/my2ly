package com.husen.jian.dao.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by HuSen on 2018/9/20 14:55.
 */
@Data
public class RouterVo implements Serializable {
    private String path;
    private String component;
    private String redirect;
    private String name;
    private Boolean alwaysShow;
    private Boolean hidden;
    private List<RouterVo> children;
    private MetaVo meta;
}
