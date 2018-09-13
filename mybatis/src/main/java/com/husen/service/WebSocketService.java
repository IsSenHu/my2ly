package com.husen.service;

import com.husen.base.Base;
import com.husen.config.security.WebSecurityConstant;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.server.standard.SpringConfigurator;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Iterator;

/**
 * Created by HuSen on 2018/8/13 17:02.
 */
@ServerEndpoint(value = "/userLogoutHandler/{username}", configurator = SpringConfigurator.class)
public class WebSocketService extends Base {
    private static final Logger log = LoggerFactory.getLogger(WebSocketService.class);

    /**当前session*/
    public Session currentSession;

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {
        log.info("建立WebSocket连接");
        this.currentSession = session;
        if(StringUtils.isNotBlank(username)) {
            log.info("建立WebSocket连接完成");
            WebSecurityConstant.USER_WEB_SOCKET_SESSION_MAP.put(username, this);
        }
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        log.info("关闭原因:[{}]", reason.toString());
        if (WebSecurityConstant.USER_WEB_SOCKET_SESSION_MAP.containsValue(this)) {
            Iterator<String> keys = WebSecurityConstant.USER_WEB_SOCKET_SESSION_MAP.keySet().iterator();
            String username;
            while(keys.hasNext()) {
                username = keys.next();
                if (WebSecurityConstant.USER_WEB_SOCKET_SESSION_MAP.get(username) == this) {
                    //关闭链接时，删除缓存对象
                    WebSecurityConstant.USER_WEB_SOCKET_SESSION_MAP.remove(username, this);
                }
            }
        }
        this.currentSession = null;
        try {
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
