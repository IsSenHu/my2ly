package com.husen.config.security;

import com.husen.service.WebSocketService;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by HuSen on 2018/8/12 2:33.
 */
public class WebSecurityConstant {
    /**认证权限集合*/
    public static final Map<String, String> AUTHORIZE_MENUS = new ConcurrentHashMap<>();
    /**用户权限是否已修改MAP*/
    public static final Map<Long, Boolean> USER_PERMISSION_IF_MODIFIED_MAP = new ConcurrentHashMap<>();
    /**用户WebSocketSessionMap*/
    public static final Map<String, WebSocketService> USER_WEB_SOCKET_SESSION_MAP = new ConcurrentHashMap<>();
}
