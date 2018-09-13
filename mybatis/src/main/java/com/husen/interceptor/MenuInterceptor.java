package com.husen.interceptor;

import com.husen.dao.po.UserPo;
import com.husen.dao.vo.MenusVo;
import com.husen.service.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * Created by HuSen on 2018/8/10 17:33.
 */
@Component
public class MenuInterceptor extends HandlerInterceptorAdapter {
    private static final Logger log = LoggerFactory.getLogger(MenuInterceptor.class);

    private final MenuService menuService;
    @Autowired
    public MenuInterceptor(MenuService menuService) {
        this.menuService = menuService;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        MenusVo isPage = menuService.isPage(request.getRequestURI());
        if("Get".equalsIgnoreCase(request.getMethod()) && null != isPage) {
            if(!Objects.isNull(modelAndView)) {
                UserPo user = getUser();
                if(null != user) {
                    modelAndView.addObject("username", user.getUsername());
                }
                modelAndView.addObject("breadcrumbItems", isPage)
                        .addObject("pageButtons", isPage.getPageGlobalButtons())
                        .addObject("rowButtons", isPage.getPageRowButtons());
                super.postHandle(request, response, handler, modelAndView);
            }
        }
    }

    /**
     * 获取已认证的用户信息
     * @return 认证的用户信息
     */
    public static UserPo getUser(){
        SecurityContext securityContext;
        Authentication authentication;
        Object principal;
        if((securityContext = SecurityContextHolder.getContext()) == null
                || (authentication = securityContext.getAuthentication()) == null || (principal = authentication.getPrincipal()) == null) {
            return null;
        }else {
            log.info("当前登录的用户为:{}", principal);
            return (UserPo) principal;
        }
    }
}
